package test;

import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("=== 多线程 Server 启动 (端口8888) ===");
            while (true) {
                // 1. 阻塞等待，直到有客户端连接
                Socket socket = serverSocket.accept();
                System.out.println(">>> 有新的客户端连接! IP: " + socket.getInetAddress());
                
                // 2. 创建一个任务对象
                ClientHandler task = new ClientHandler(socket);
                
                // 3. 启动一个新线程去执行这个任务
                new Thread(task).start();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
