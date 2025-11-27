package test;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;
import java.util.regex.*;

public class HttpServerApp {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/tickets", HttpServerApp::handleTickets);
        server.setExecutor(null);
        System.out.println("HTTP Server started at http://localhost:8080");
        server.start();
    }

    private static void cors(HttpExchange ex) {
        Headers h = ex.getResponseHeaders();
        h.add("Access-Control-Allow-Origin", "*");
        h.add("Access-Control-Allow-Headers", "Content-Type");
        h.add("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        h.add("Content-Type", "application/json; charset=utf-8");
    }

    private static void handleTickets(HttpExchange ex) throws IOException {
        cors(ex);
        String method = ex.getRequestMethod();
        try {
            if ("OPTIONS".equalsIgnoreCase(method)) {
                ex.sendResponseHeaders(204, -1);
                return;
            }
            if ("GET".equalsIgnoreCase(method)) {
                String json = listTicketsJson();
                byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
                ex.sendResponseHeaders(200, bytes.length);
                try (OutputStream os = ex.getResponseBody()) { os.write(bytes); }
                return;
            }
            if ("POST".equalsIgnoreCase(method)) {
                String body = readBody(ex);
                Map<String, String> m = parseJsonBody(body);
                Ticket t = new Ticket(m.getOrDefault("name", ""),
                        m.getOrDefault("idCard", ""),
                        m.getOrDefault("startCity", ""),
                        m.getOrDefault("endCity", ""));
                ServerFileHelper.saveRecord("server_all_records.txt", List.of(t));
                try {
                    DatabaseHelper.insertTicket(t);
                } catch (SQLException se) {
                    se.printStackTrace();
                }
                String ok = "{\"ok\":true}";
                byte[] bytes = ok.getBytes(StandardCharsets.UTF_8);
                ex.sendResponseHeaders(200, bytes.length);
                try (OutputStream os = ex.getResponseBody()) { os.write(bytes); }
                return;
            }
            ex.sendResponseHeaders(405, -1);
        } catch (Exception e) {
            e.printStackTrace();
            byte[] bytes = ("{\"message\":\"" + escape(e.getMessage()) + "\"}").getBytes(StandardCharsets.UTF_8);
            ex.sendResponseHeaders(500, bytes.length);
            try (OutputStream os = ex.getResponseBody()) { os.write(bytes); }
        }
    }

    private static String readBody(HttpExchange ex) throws IOException {
        try (InputStream is = ex.getRequestBody()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private static Map<String, String> parseJsonBody(String body) {
        Map<String, String> map = new HashMap<>();
        for (String k : List.of("name", "idCard", "startCity", "endCity")) {
            map.put(k, extractValue(body, k));
        }
        return map;
    }

    private static String extractValue(String body, String key) {
        Pattern p = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\"([^\"]*)\"");
        Matcher m = p.matcher(body);
        return m.find() ? m.group(1) : "";
    }

    private static String listTicketsJson() throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean first = true;
        try (Connection conn = DatabaseHelper.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT id,name,id_card,start_city,end_city,created_at FROM tickets ORDER BY id")) {
            while (rs.next()) {
                if (!first) sb.append(",");
                first = false;
                sb.append("{");
                sb.append("\"id\":").append(rs.getLong(1)).append(",");
                sb.append("\"name\":\"").append(escape(rs.getString(2))).append("\",");
                sb.append("\"idCard\":\"").append(escape(rs.getString(3))).append("\",");
                sb.append("\"startCity\":\"").append(escape(rs.getString(4))).append("\",");
                sb.append("\"endCity\":\"").append(escape(rs.getString(5))).append("\",");
                Timestamp ts = rs.getTimestamp(6);
                String iso = ts == null ? "" : ts.toInstant().toString();
                sb.append("\"createdAt\":\"").append(escape(iso)).append("\"");
                sb.append("}");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
