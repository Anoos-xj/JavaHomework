package test;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class FileClientBase {
    public static void sendTicketsFromFile(String fileName) {
        List<Ticket> tickets = FileManager.readTicketsFromFile(fileName);
        for (Ticket t : tickets) {
            try (Socket socket = new Socket("localhost", 8888)) {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(t);
                oos.flush();
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                String msg = dis.readUTF();
                System.out.println(msg + " -> " + t.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
