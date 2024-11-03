import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;

public class PerformanceTest {
    @Test
    public void testMessageThroughput() throws InterruptedException {
        Server server = new Server();
        Client client = new Client();
        AtomicInteger successCount = new AtomicInteger(0);

        // Start server
        new Thread(server::start).start();
        Thread.sleep(1000); // Wait for server startup

        // Send test messages
        for (int i = 0; i < 10; i++) {
            String result = client.sendMessage("Test " + i);
            if (result.contains("successfully")) {
                successCount.incrementAndGet();
            }
            Thread.sleep(100); // Brief pause between messages
        }

        // Report results
        System.out.printf("Success rate: %d%%\n", successCount.get() * 10);
    }
} 