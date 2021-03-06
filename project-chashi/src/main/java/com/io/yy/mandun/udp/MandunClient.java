package com.io.yy.mandun.udp;

import com.io.yy.util.codec.EncodeUtils;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

@Slf4j
@Service
public class MandunClient {

    @Value("${udp.port}")
    private Integer udpPort;

    // 服务器端口
    public static final int SERVER_PORT = 6666;
    // 本地发送端口
    public static final int LOCAL_PORT = 7777;


    public void redisterMessage(String message,String serverIp,String serverPort) {
        log.info("发送 redisterMessage: {}", message+":ip:"+serverIp+":port:"+serverPort);
        //计算crc
        CRC32 crc32 = new CRC32();
        crc32.update(Hex.decode(message.getBytes()));
        String crc32str = Long.toHexString(crc32.getValue());
        log.info(crc32str);
        message = message + crc32str.toUpperCase();
        log.info(message);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(serverIp, Integer.valueOf(serverPort));
        byte[] udpMessage = getHexBytes(message);;
        log.info(DatatypeConverter.printHexBinary(udpMessage));
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

    public static byte[] getHexBytes(String str) {
        str = str.replaceAll(" ", "");
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    public static void main(String[] args){
        String message="";

        Date nowDate = new Date();
        String time = String.valueOf(nowDate.getTime());
        log.info(nowDate.getTime()+"");
        long time2 = 1546308000;
        Long.toHexString(1546308000);
        log.info( "2:"+Long.toHexString(1546308000));

        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd HH");
        log.info("3:"+System.currentTimeMillis()/1000L);
        log.info( Long.toHexString(System.currentTimeMillis()/1000L));

        String tt= "F1B00000000000015E93CA88FFFFFF80";
        byte[] t = getHexBytes(tt);
        log.info(DatatypeConverter.printHexBinary(t));



    }


}
