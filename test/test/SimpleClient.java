package test;

import java.io.*;
import java.net.*;

public class SimpleClient {
    public static void main(String[] args) {
        System.out.println("客户端启动");
        try (Socket socket = new Socket("localhost", 8888)) {
            System.out.println("连接服务器成功");
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Ticket ticket = new Ticket("张三", "123", "北京", "上海");
            System.out.println("正在发送票信息:" + ticket.toString());
            oos.writeObject(ticket);
            oos.flush();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String serverMsg = dis.readUTF();
            System.out.println("服务器返回消息:" + serverMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
