package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DisplayDatabase {
    public static void main(String[] args) {
        System.out.printf("%-12s | %-20s | %-12s | %-12s%n", "姓名", "身份证号码", "出发城市", "到达城市");
        System.out.println("--------------------------------------------------------------------");
        try (Connection conn = DatabaseHelper.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT name,id_card,start_city,end_city FROM tickets ORDER BY id")) {
            while (rs.next()) {
                System.out.printf("%-12s | %-20s | %-12s | %-12s%n",
                        rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
