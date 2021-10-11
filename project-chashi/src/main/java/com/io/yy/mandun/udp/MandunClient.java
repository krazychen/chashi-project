package com.io.yy.mandun.udp;

import com.io.yy.util.codec.EncodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Service
public class MandunClient {

    @Value("${udp.port}")
    private Integer udpPort;

    public void sendMessage(String message) {
        log.info("发送UDP: {}", message);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", udpPort);
        byte[] udpMessage = message.getBytes();
        DatagramPacket datagramPacket = null;
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            datagramPacket = new DatagramPacket(udpMessage, udpMessage.length, inetSocketAddress);
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        log.info("发送成功");
    }

    public static void main(String[] args){
        String message="";

        Date nowDate = new Date();
        String time = String.valueOf(nowDate.getTime());
        log.info(nowDate.getTime()+"");
        long time2 = 1546308000;
        Long.toHexString(1546308000);
        log.info( Long.toHexString(1546308000));


    }


}
