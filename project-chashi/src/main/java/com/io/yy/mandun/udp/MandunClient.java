package com.io.yy.mandun.udp;

import com.io.yy.util.codec.EncodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

@Slf4j
@Service
public class MandunClient {

    @Value("${udp.port}")
    private Integer udpPort;

    public void redisterMessage(String message){
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

    public void sendMessage(String message) {
        log.info("发送 redisterMessage: {}", message);
        //计算crc
        CRC32 crc32 = new CRC32();
        crc32.update(Hex.decode(message.getBytes()));
        String crc32str = Long.toHexString(crc32.getValue());
        log.info(crc32str);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", udpPort);
        byte[] udpMessage = message.getBytes();
        DatagramPacket datagramPacket = null;
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            datagramPacket = new DatagramPacket(udpMessage, udpMessage.length, inetSocketAddress);
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        log.info("redisterMessage发送成功");
    }

    public static byte[] calCrc32(byte[] data) {
        Checksum checksum = new CRC32();
        // update the current checksum with the specified array of bytes
        checksum.update(data, 0, data.length);

        // get the current checksum value
        long checksumValue = checksum.getValue();

        log.info(":"+checksumValue);

        String hex = Long.toHexString(checksumValue).toUpperCase();
        while (hex.length() < 8) {
            hex = "0" + hex;
        }
        byte[] crc32 = Hex.decode(hex);

        return crc32;
    }

    public static void main(String[] args){
        String message="";

        Date nowDate = new Date();
        String time = String.valueOf(nowDate.getTime());
        log.info(nowDate.getTime()+"");
        long time2 = 1546308000;
        Long.toHexString(1546308000);
        log.info( Long.toHexString(1546308000));

        String tt= "F1B00000000000015E93CA88FFFFFF80";
        CRC32 crc32 = new CRC32();
        crc32.update(Hex.decode(tt.getBytes()));
        log.info(Long.toHexString(crc32.getValue()));
        log.info("CRC32：" + DatatypeConverter.printHexBinary(calCrc32(tt.getBytes())));


    }


}
