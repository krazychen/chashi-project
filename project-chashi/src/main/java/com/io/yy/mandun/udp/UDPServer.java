package com.io.yy.mandun.udp;

import com.io.yy.util.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.zip.CRC32;

@Slf4j
@Component
public class UDPServer implements ApplicationRunner {

    public static final int MAX_UDP_DATA_SIZE = 4096;
    public static final int UDP_PORT = 6666;
    public static DatagramPacket packet = null;
    public static DatagramSocket socket = null;

    @Override
    public void run(ApplicationArguments var1) throws Exception{
        try {
            log.info("========启动一个线程，监听UDP数据报.PORT:" + UDP_PORT + "=========");
            // 启动一个线程，监听UDP数据报
            new Thread(new UDPProcess(UDP_PORT)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class UDPProcess implements Runnable {

        public UDPProcess(final int port) throws SocketException {
            //创建服务器端DatagramSocket，指定端口
            socket = new DatagramSocket(port);
        }

        @Override
        public void run() {
            log.info("=======创建数据报，用于接收客户端发送的数据======");
            while (true) {
                byte[] buffer = new byte[MAX_UDP_DATA_SIZE];
                packet = new DatagramPacket(buffer, buffer.length);
                try {
                    log.info("=======此方法在接收到数据报之前会一直阻塞======");
                    socket.receive(packet);
                    new Thread(new Process(packet)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    class Process implements Runnable {

        private String message =null;
        private String ipAddress = null;
        private int port = 0;

        public Process(DatagramPacket packet) throws UnsupportedEncodingException {

            log.info("=======接收到的UDP信息======");
            byte[] buffer = packet.getData();// 接收到的UDP信息，然后解码

            message = DatatypeConverter.printHexBinary(buffer);//把接收的数据转化为字符串
            ipAddress = packet.getAddress().getHostAddress();
            port = packet.getPort();
            log.info("ip:"+  packet.getAddress().getHostAddress()+":"+packet.getPort());
            log.info("transformer：--"+message);

        }

        @Override
        public void run() {
            log.info("====过程运行=====");

            byte[] udpMessage = null;
            try {
                String PVER = message.substring(0,2);
                String CMD = message.substring(2,4);
                String PARA = message.substring(4,6);
                String CMDNO = message.substring(12,16);

                log.info(PVER+":"+CMD);

                //注册信息
                if(StringUtils.isNotEmpty(PVER)&&"F1".equals(PVER)&&"B0".equals(CMD)){
                    String ECHO = "00";
                    String Len = "0000";
                    String nowTime = Long.toHexString(System.currentTimeMillis()/1000L).toUpperCase();
                    String UID = "FFFFFF80";

                    //拼装应答
                    String respMessage = PVER+CMD+PARA+ECHO+Len+CMDNO+nowTime+UID;

                    CRC32 crc32 = new CRC32();
                    crc32.update(Hex.decode(respMessage.getBytes()));
                    String crc32str = Long.toHexString(crc32.getValue());
                    respMessage = respMessage + crc32str.toUpperCase();
                    log.info(respMessage);
                    udpMessage = getHexBytes(message);;
                }

                if(StringUtils.isNotEmpty(PVER)&&"F1".equals(PVER)&&"AC".equals(CMD)){

                }


                log.info("====向客户端响应数据=====");
                //1.定义客户端的地址、端口号、数据
                InetAddress address = packet.getAddress();
//                InetSocketAddress inetSocketAddress = new InetSocketAddress(ipAddress, port);

                int port = packet.getPort();
                //2.创建数据报，包含响应的数据信息
                if(udpMessage!=null){
                    DatagramPacket packet2 = new DatagramPacket(udpMessage, udpMessage.length, address,port);
                    //3.响应客户端
                    socket.send(packet2);
                }else{
                    log.info("响应内容为空！");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
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

}

