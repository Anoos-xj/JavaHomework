package test;

import java.io.*;
import java.net.*;

public class SimpleServer {
    public static void main(String[] args) {
        System.out.println("服务器启动");
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            Socket socket = serverSocket.accept();
            System.out.println("客户端连接,客户端IP:" + socket.getInetAddress());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Ticket ticket = (Ticket) ois.readObject();
            System.out.println("客户端发送的票信息:" + ticket.toString());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("服务器已收到票信息");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
