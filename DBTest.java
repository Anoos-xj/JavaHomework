import java.sql.Connection;
import java.sql.DriverManager;

public class DBTest {
    public static void main(String[] args) {
        // 1. 这里的密码一定要改成你刚才安装设置的那个！！！
        String url = "jdbc:mysql://localhost:3306/?serverTimezone=UTC";
        String user = "root";
        String password = "1234"; // <--- 改这里

        System.out.println("正在连接数据库...");
        
        try {
            // 加载驱动（新版可以省略，但写上不亏）
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 尝试连接
            Connection conn = DriverManager.getConnection(url, user, password);
            
            System.out.println("恭喜！数据库连接成功！");
            System.out.println("Java环境: OK");
            System.out.println("MySQL驱动: OK");
            
            conn.close();
        } catch (ClassNotFoundException e) {
            System.err.println("错误：找不到MySQL驱动包！请检查 referenced libraries 设置。");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("错误：连接失败！可能是密码错了，或者MySQL服务没启动。");
            e.printStackTrace();
        }
    }
}