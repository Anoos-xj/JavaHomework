package test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static List<Ticket> readTicketsFromFile(String fileName) {
        List<Ticket> tickets = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] info = line.split(",");
                tickets.add(new Ticket(info));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tickets;
    }
    public static void main(String[] args) {
        List<Ticket> tickets = readTicketsFromFile("tickets.txt");
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
        //追加写入模式
        writeTicketsToFile(tickets, "tickets.txt");
        List<Ticket> newTickets = readTicketsFromFile("tickets.txt");
        for (Ticket ticket : newTickets) {
            System.out.println(ticket);
        }
    }
    //追加写入模式
    public static void writeTicketsToFile(List<Ticket> tickets, String fileName) {
        try {
            File file = new File(fileName);
            boolean needLeadingNewLine = false;
            if (file.exists() && file.length() > 0) {
                try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
                    long len = raf.length();
                    raf.seek(len - 1);
                    int last = raf.read();
                    if (last != '\n') {
                        needLeadingNewLine = true;
                    }
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                if (needLeadingNewLine) {
                    writer.newLine();
                }
                for (Ticket ticket : tickets) {
                    writer.write(ticket.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
