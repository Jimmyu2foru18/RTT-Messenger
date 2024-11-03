import java.net.*;
import java.time.Instant;
import java.util.logging.Logger;

public class Client {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;
    private static final int MAX_RETRIES = 3;
    private static final int SOCKET_TIMEOUT = 1000;
    private final RTTStatistics stats;
    private final DatagramSocket socket;

    public Client() {
        try {
            this.socket = new DatagramSocket();
            this.socket.setSoTimeout(SOCKET_TIMEOUT);
            this.socket.setReceiveBufferSize(65535);
            this.socket.setSendBufferSize(65535);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize client", e);
        }
        this.stats = new RTTStatistics();
    }

    @Override
    public void finalize() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    public String sendMessage(String content) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(SOCKET_TIMEOUT);
            Message msg = new Message(content);
            
            while (msg.getRetryCount() < MAX_RETRIES) {
                try {
                    // Send message with sequence number
                    String data = msg.getSequenceNumber() + ":" + msg.getContent();
                    DatagramPacket packet = new DatagramPacket(
                        data.getBytes(), data.getBytes().length,
                        InetAddress.getByName(SERVER_HOST), SERVER_PORT
                    );
                    socket.send(packet);
                    LOGGER.info("Sent message (seq=" + msg.getSequenceNumber() + ")");

                    // Wait for acknowledgment
                    byte[] buffer = new byte[1024];
                    DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                    socket.receive(response);
                    
                    String ack = new String(response.getData(), 0, response.getLength());
                    if (validateAck(ack, msg.getSequenceNumber())) {
                        msg.setReceiveTime(Instant.now());
                        stats.addMeasurement(msg.getRTT());
                        LOGGER.info("Message delivered successfully, RTT: " + msg.getRTT() + "ms");
                        LOGGER.info(stats.getStatistics());
                        return "Message sent successfully. RTT: " + msg.getRTT() + "ms";
                    }
                } catch (SocketTimeoutException e) {
                    msg.incrementRetry();
                    LOGGER.warning("Timeout, retrying... (" + msg.getRetryCount() + "/" + MAX_RETRIES + ")");
                }
            }
            LOGGER.severe("Failed to send message after " + MAX_RETRIES + " attempts");
            return "Failed to send message: Message not delivered after " + MAX_RETRIES + " attempts";
        } catch (Exception e) {
            LOGGER.severe("Error: " + e.getMessage());
            return "Failed to send message: " + e.getMessage();
        }
    }

    private boolean validateAck(String ack, int expectedSeq) {
        try {
            String[] parts = ack.split(":");
            return parts[0].equals("ACK") && Integer.parseInt(parts[1]) == expectedSeq;
        } catch (Exception e) {
            return false;
        }
    }

    public String getStats() {
        return stats.getStatistics();
    }

    public static void main(String[] args) {
        Client client = new Client();
        for (int i = 0; i < 5; i++) {
            client.sendMessage("Test " + (i + 1));
            try { Thread.sleep(100); } catch (InterruptedException e) { break; }
        }
    }
} 