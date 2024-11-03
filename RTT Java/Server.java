import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Logger;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private static final int PORT = 8080;
    private static final int BUFFER_SIZE = 1024;
    private static final double ERROR_RATE = 0.1; // 10% chance of simulated error
    private static final int THREAD_POOL_SIZE = 10;
    private final Random random;
    private DatagramSocket socket;
    private boolean running;
    private int expectedSequence;
    private final ExecutorService executor;

    public Server() {
        this.random = new Random();
        this.executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        this.expectedSequence = 0;
    }

    public void start() {
        try {
            socket = new DatagramSocket(PORT);
            running = true;
            LOGGER.info("Server started on port " + PORT);
            
            while (running) {
                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                processMessage(message, packet);
            }
        } catch (Exception e) {
            LOGGER.severe("Server error: " + e.getMessage());
        }
    }

    private void processMessage(String message, DatagramPacket packet) {
        executor.submit(() -> {
            try {
                // Simulate random network errors
                if (random.nextDouble() < ERROR_RATE) {
                    LOGGER.warning("Simulating packet loss (no ACK sent)");
                    return;
                }

                String[] parts = message.split(":");
                int receivedSeq = Integer.parseInt(parts[0]);
                
                // Send acknowledgment
                String ack = "ACK:" + receivedSeq;
                byte[] ackData = ack.getBytes();
                DatagramPacket response = new DatagramPacket(
                    ackData, ackData.length, packet.getAddress(), packet.getPort()
                );
                
                if (receivedSeq == expectedSequence) {
                    socket.send(response);
                    LOGGER.info("Processed message: " + parts[1] + " (seq=" + receivedSeq + ")");
                    expectedSequence = (expectedSequence + 1) % 2;
                } else {
                    LOGGER.warning("Received duplicate or out-of-sequence message");
                    socket.send(response); // Still ACK to handle lost ACKs
                }
            } catch (Exception e) {
                LOGGER.warning("Error processing message: " + e.getMessage());
            }
        });
    }

    public void stop() {
        running = false;
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    public static void main(String[] args) {
        new Server().start();
    }
} 