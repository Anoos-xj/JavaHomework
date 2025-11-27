package test;

import java.io.*;
import java.util.List;

public class ServerFileHelper {
    public static synchronized void saveRecord(String filePath, List<Ticket> tickets) {
        try {
            File file = new File(filePath);
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
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
                if (needLeadingNewLine) {
                    bw.newLine();
                }
                for (Ticket ticket : tickets) {
                    bw.write(ticket.toString());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
