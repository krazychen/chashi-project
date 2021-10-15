package com.io.yy.mandun.udp;

import com.io.yy.util.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.zip.CRC32;

@Slf4j
@Component
public class ManDunUdp implements ApplicationRunner {

    public static final int UDP_PORT = 6666;
    private static final  String TAG = "UDPServer: ";

    @Override
    public void run(ApplicationArguments var1) throws Exception{
        try {
            byte[] buf = new byte[1024];
            //服务端在3333端口监听接收到的数据
            try{
                DatagramSocket datagramSocket = new DatagramSocket( UDP_PORT );
                //接收从客户端发送过来的数据
                DatagramPacket packet_receive = new DatagramPacket( buf,buf.length );
                log.info("========启动一个线程，监听UDP数据报.PORT:" + UDP_PORT + "=========");
                boolean f =true;
                while (f){
                    //服务器端接收来自客户端的数据
                    datagramSocket.receive( packet_receive );
                    log.info("=======接收到的UDP信息======");
                    byte[] buffer = packet_receive.getData();// 接收到的UDP信息，然后解码
                    String message = DatatypeConverter.printHexBinary(buffer);//把接收的数据转化为字符串
                    log.info(message);
                    log.info("ip:"+  packet_receive.getAddress().getHostAddress()+":"+packet_receive.getPort());

                    log.info("====解析过程运行=====");
                    String PVER = message.substring(0,2);
                    String CMD = message.substring(2,4);

                    log.info(PVER+":"+CMD);

                    String sendStr = null;
                    //注册信息
                    if(StringUtils.isNotEmpty(PVER)&&"F1".equals(PVER)){
                        sendStr = this.redisterMessage(message);
                    }
//                    if(StringUtils.isNotEmpty(PVER)&&"F1".equals(PVER)&&"B0".equals(CMD)){
//
//                        sendStr = this.redisterMessage(message);
//                    }
//                    if(StringUtils.isNotEmpty(PVER)&&"F1".equals(PVER)&&"B2".equals(CMD)){
//                        sendStr = this.networkMessage(message);
//                    }
//                    if(StringUtils.isNotEmpty(PVER)&&"F1".equals(PVER)&&"B4".equals(CMD)){
//                        sendStr = this.switchMessage(message);
//                    }
//
//                    if(StringUtils.isNotEmpty(PVER)&&"F1".equals(PVER)&&"AC".equals(CMD)){
//                        sendStr = this.ACMessage(message);
//                    }
//
//                    if(StringUtils.isNotEmpty(PVER)&&"F1".equals(PVER)&&"AA".equals(CMD)){
//                        sendStr = this.AAMessage(message);
//                    }

                    log.info(sendStr);
                    if(StringUtils.isNotEmpty(sendStr)){
                        byte[] udpMessage = getHexBytes(sendStr);;
                        log.info(DatatypeConverter.printHexBinary(udpMessage));
                        // DatagramPacket packet_send = new DatagramPacket( sendStr.getBytes(),sendStr.length(),packet_receive.getAddress(),9999 );
                        //sendStr.length()为求字符串的长度，它的长度与该字符串转化为字节数组的长度不一致，可能会造成数据末尾丢失,所以改为如下

                        DatagramPacket packet_send = new DatagramPacket( udpMessage,udpMessage.length,packet_receive.getAddress(),packet_receive.getPort() );
                        datagramSocket.send( packet_send );
                        //由于packet_receive在接收了数据之后，其内部消息长度值会变为实际接收消息的字节数
                        //所以这里要将packet_receive的内部消息长度重新设置为1024
                        packet_receive.setLength( 1024 );
                    }
                }
                datagramSocket.close();
            }catch (SocketException e){
                System.out.println(TAG+e.getMessage());
                e.printStackTrace();
            }catch (IOException e){
                System.out.println(TAG+e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String redisterMessage(String message){
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

        //计算crc
        CRC32 crc32 = new CRC32();
        crc32.update(Hex.decode(respMessage.getBytes()));
        String crc32str = Long.toHexString(crc32.getValue());
        log.info(crc32str);
        respMessage = respMessage + crc32str.toUpperCase();
        log.info(respMessage);
        return respMessage;
    }

    private String networkMessage(String message){
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

        //计算crc
        CRC32 crc32 = new CRC32();
        crc32.update(Hex.decode(respMessage.getBytes()));
        String crc32str = Long.toHexString(crc32.getValue());
        log.info(crc32str);
        respMessage = respMessage + crc32str.toUpperCase();
        log.info(respMessage);
        return respMessage;
    }

    private String switchMessage(String message){
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

        //计算crc
        CRC32 crc32 = new CRC32();
        crc32.update(Hex.decode(respMessage.getBytes()));
        String crc32str = Long.toHexString(crc32.getValue());
        log.info(crc32str);
        respMessage = respMessage + crc32str.toUpperCase();
        log.info(respMessage);
        return respMessage;
    }

    private String ACMessage(String message){
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

        //计算crc
        CRC32 crc32 = new CRC32();
        crc32.update(Hex.decode(respMessage.getBytes()));
        String crc32str = Long.toHexString(crc32.getValue());
        log.info(crc32str);
        respMessage = respMessage + crc32str.toUpperCase();
        log.info(respMessage);
        return respMessage;
    }

    private String AAMessage(String message){
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

        //计算crc
        CRC32 crc32 = new CRC32();
        crc32.update(Hex.decode(respMessage.getBytes()));
        String crc32str = Long.toHexString(crc32.getValue());
        log.info(crc32str);
        respMessage = respMessage + crc32str.toUpperCase();
        log.info(respMessage);
        return respMessage;
    }

    private byte[] getHexBytes(String str) {
        str = str.replaceAll(" ", "");
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }
}
