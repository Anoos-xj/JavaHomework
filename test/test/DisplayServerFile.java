package test;

import java.io.BufferedReader;
import java.io.FileReader;

public class DisplayServerFile {
    public static void main(String[] args) {
        String file = "server_all_records.txt";
        System.out.printf("%-12s | %-20s | %-12s | %-12s%n", "姓名", "身份证号码", "出发城市", "到达城市");
        System.out.println("--------------------------------------------------------------------");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",", -1);
                if (p.length < 4) continue;
                System.out.printf("%-12s | %-20s | %-12s | %-12s%n", p[0], p[1], p[2], p[3]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
