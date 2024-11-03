import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class MessageController {
    private static final Logger LOGGER = Logger.getLogger(MessageController.class.getName());
    private final Client client;

    public MessageController() throws IOException {
        this.client = new Client();
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        // Single endpoint for messages
        server.createContext("/message", exchange -> {
            try {
                if ("POST".equals(exchange.getRequestMethod())) {
                    String content = new String(exchange.getRequestBody().readAllBytes());
                    String response = client.sendMessage(content);
                    sendResponse(exchange, 200, response);
                } else if ("GET".equals(exchange.getRequestMethod())) {
                    sendResponse(exchange, 200, client.getStats());
                } else {
                    sendResponse(exchange, 405, "Method not allowed");
                }
            } catch (IOException e) {
                sendResponse(exchange, 500, "Error: " + e.getMessage());
            }
        });
        
        server.start();
        LOGGER.info("Server started on port 8000");
    }

    private void sendResponse(HttpExchange exchange, int code, String response) throws IOException {
        exchange.sendResponseHeaders(code, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    public static void main(String[] args) {
        try {
            new MessageController();
        } catch (IOException e) {
            LOGGER.severe("Failed to start server: " + e.getMessage());
        }
    }
} 