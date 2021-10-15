package com.io.yy.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class TestUpd {

    private static final  String TAG = "UDPServer: ";
    public static void main(String[] args) {
        String sendStr = "客服端你好， 我是服务端";
        byte[] buf = new byte[1024];
        //服务端在3333端口监听接收到的数据
        try{
            DatagramSocket datagramSocket = new DatagramSocket( 6666 );
            //接收从客户端发送过来的数据
            DatagramPacket packet_receive = new DatagramPacket( buf,buf.length );
            System.out.println("server is on,waiting for client to send data...");
            boolean f =true;
            while (f){
                //服务器端接收来自客户端的数据
                datagramSocket.receive( packet_receive );
                System.out.println("server receive data from client");
                String receiveStr = new String(packet_receive.getData(),0,packet_receive.getLength())+
                        " from " + packet_receive.getAddress().getHostAddress()+":"+packet_receive.getPort();
                System.out.println(receiveStr);
                //数据发送到客户端的3333端口

                // DatagramPacket packet_send = new DatagramPacket( sendStr.getBytes(),sendStr.length(),packet_receive.getAddress(),9999 );
                //sendStr.length()为求字符串的长度，它的长度与该字符串转化为字节数组的长度不一致，可能会造成数据末尾丢失,所以改为如下

                DatagramPacket packet_send = new DatagramPacket( sendStr.getBytes(),sendStr.getBytes().length,packet_receive.getAddress(),packet_receive.getPort() );
                datagramSocket.send( packet_send );
                //由于packet_receive在接收了数据之后，其内部消息长度值会变为实际接收消息的字节数
                //所以这里要将packet_receive的内部消息长度重新设置为1024
                packet_receive.setLength( 1024 );
            }

            datagramSocket.close();
        }catch (SocketException e){
            System.out.println(TAG+e.getMessage());
            e.printStackTrace();
        }catch (IOException e){
            System.out.println(TAG+e.getMessage());
            e.printStackTrace();
        }
    }
}
