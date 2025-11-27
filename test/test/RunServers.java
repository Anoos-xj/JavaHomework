package test;

public class RunServers {
    public static void main(String[] args) {
        Thread http = new Thread(() -> {
            try { HttpServerApp.main(new String[0]); } catch (Exception e) { e.printStackTrace(); }
        }, "http-server");
        Thread socket = new Thread(() -> {
            try { MultiThreadServer.main(new String[0]); } catch (Exception e) { e.printStackTrace(); }
        }, "socket-server");
        http.start();
        socket.start();
        System.out.println("Servers started: HTTP 8080, Socket 8888");
        try { http.join(); socket.join(); } catch (InterruptedException ignored) {}
    }
}