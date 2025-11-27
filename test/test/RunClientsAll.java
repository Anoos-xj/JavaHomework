package test;

public class RunClientsAll {
    public static void main(String[] args) {
        try {
            Process p1 = new ProcessBuilder("java", "-cp", ".;lib\\mysql-connector-j-8.3.0.jar", "test.FileClient1").start();
            Process p2 = new ProcessBuilder("java", "-cp", ".;lib\\mysql-connector-j-8.3.0.jar", "test.FileClient2").start();
            Process p3 = new ProcessBuilder("java", "-cp", ".;lib\\mysql-connector-j-8.3.0.jar", "test.FileClient3").start();
            Process p4 = new ProcessBuilder("java", "-cp", ".;lib\\mysql-connector-j-8.3.0.jar", "test.FileClient4").start();
            p1.waitFor();
            p2.waitFor();
            p3.waitFor();
            p4.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}