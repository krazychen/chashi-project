package com.io.yy.mandun.udp;

import com.io.yy.util.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.dsl.Udp;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Configuration
public class MandunServer {

    @Value("${udp.port}")
    private Integer udpPort;

    private String serverIp = null;

    private String serverPort = null;

    @Autowired
    private MandunClient mandunClient;

    /**
     * UDP消息接收服务写法二
     * https://docs.spring.io/spring-integration/reference/html/ip.html#inbound-udp-adapters-java-dsl-configuration
     *
     * @param
     * @return org.springframework.integration.dsl.IntegrationFlow
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/20 16:08
     */
    @Bean
    public IntegrationFlow integrationFlow() {
        log.info("UDP服务启动成功，端口号为: {}", udpPort);
        return IntegrationFlows.from(Udp.inboundAdapter(udpPort)).channel("udpChannel").get();
    }

    /**
     * 转换器
     *
     * @param payload
     * @param headers
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/20 15:30
     */
    @Transformer(inputChannel = "udpChannel", outputChannel = "udpFilter")
    public String transformer(@Payload byte[] payload, @Headers Map<String, Object> headers) {

        headers.entrySet().forEach(e -> log.info(e.toString()));
//        log.info("transformer：--"+payload.toString());
        String message = DatatypeConverter.printHexBinary(payload);//把接收的数据转化为字符串
        log.info("transformer：--"+message);
        // 转换为大写
        // message = message.toUpperCase();
        // 向客户端响应，还不知道怎么写
        return message;
    }

    /**
     * 过滤器
     *
     * @param message
     * @param headers
     * @return boolean
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/20 15:30
     */
    @Filter(inputChannel = "udpFilter", outputChannel = "udpRouter")
    public boolean filter(String message, @Headers Map<String, Object> headers) {
        // 获取来源Id
        String id = headers.get("id").toString();
        // 获取来源IP，可以进行IP过滤
        String ip = headers.get("ip_address").toString();
        // 获取来源Port
        String port = headers.get("ip_port").toString();
        // 信息数据过滤
        /*if (message.indexOf("-") < 0) {
            // 没有-的数据会被过滤
            return false;
        }*/
        log.info("udp -filter"+id+":"+ip+":"+port);
        return true;
    }

    /**
     * 路由分发处理器
     *
     * @param message
     * @param headers
     * @return java.lang.String
     * @throws
     */
    @Router(inputChannel = "udpRouter")
    public String router(String message, @Headers Map<String, Object> headers) {
        // 获取来源Id
        String id = headers.get("id").toString();
        // 获取来源IP，可以进行IP过滤
        serverIp = headers.get("ip_address").toString();
        // 获取来源Port
        serverPort = headers.get("ip_port").toString();

//        log.info("udp -router"+id+":"+ip+":"+port+":"+message);
        // 筛选，走那个处理器
        String PVER = message.substring(0,2);
//        log.info(PVER);
        //注册信息
        if(StringUtils.isNotEmpty(PVER)&&"F1".equals(PVER)){
            return "registerHandle";
        }

        return "errorHandle";
    }

    /**
     * 最终处理器1
     *
     * @param message
     * @return void
     * @throws
     */
    @ServiceActivator(inputChannel = "errorHandle")
    public void udpMessageHandle(String message) throws Exception {
        // 可以进行异步处理
        log.info("非正常消息:" + message);
    }

    /**
     * 最终处理器2
     *
     * @param message
     * @return void
     * @throws
     */
    @ServiceActivator(inputChannel = "registerHandle")
    public void registerHandle(String message) throws Exception {
        String PVER = message.substring(0,2);
        String CMD = message.substring(2,4);
        String PARA = message.substring(4,6);
        String CMDNO = message.substring(12,16);

        String ECHO = "00";
        String Len = "0000";

        String nowTime = Long.toHexString(System.currentTimeMillis()/1000L).toUpperCase();

        String UID = "FFFFFF80";

        //拼装应答
        String respMessage = PVER+CMD+PARA+ECHO+Len+CMDNO+nowTime+UID;

//        log.info(PVER+":"+CMD+":"+PARA+":"+CMDNO);
        log.info("registerHandle:" + respMessage);
        mandunClient.redisterMessage(respMessage,serverIp,serverPort);
    }
}