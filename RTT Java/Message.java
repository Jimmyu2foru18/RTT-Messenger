import java.time.Instant;

public class Message {
    private final String content;
    private final Instant sendTime;
    private Instant receiveTime;
    private final int sequenceNumber;
    private int retryCount;
    private static int currentSequence = 0;

    public Message(String content) {
        this.content = content;
        this.sendTime = Instant.now();
        this.sequenceNumber = currentSequence++ % 2; // Alternating bit protocol
        this.retryCount = 0;
    }

    public String getContent() { return content; }
    public int getSequenceNumber() { return sequenceNumber; }
    public int getRetryCount() { return retryCount; }
    
    public void setReceiveTime(Instant receiveTime) {
        this.receiveTime = receiveTime;
    }

    public long getRTT() {
        if (receiveTime == null) return -1;
        return receiveTime.toEpochMilli() - sendTime.toEpochMilli();
    }

    public void incrementRetry() { 
        this.retryCount++; 
    }

    @Override
    public String toString() {
        return String.format("Message{seq=%d, content='%s', rtt=%d ms, retries=%d}",
                sequenceNumber, content, getRTT(), retryCount);
    }
} 