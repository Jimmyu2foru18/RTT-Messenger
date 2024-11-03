import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Instant;

public class RTTTest {
    @Test
    public void testBasicRTTScenario() {
        // Test complete message flow
        Message message = new Message("Test message");
        message.setReceiveTime(Instant.now().plusMillis(100));
        
        // Verify RTT calculation
        long rtt = message.getRTT();
        assertTrue(rtt >= 100 && rtt <= 150, 
            "RTT should be approximately 100ms");
    }

    @Test
    public void testStatistics() {
        RTTStatistics stats = new RTTStatistics();
        stats.addMeasurement(100);
        stats.addMeasurement(200);
        
        String result = stats.getStatistics();
        assertTrue(result.contains("Min: 100") && result.contains("Max: 200"),
            "Statistics should show correct min and max values");
    }
} 