public class RTTStatistics {
    private long minRTT;
    private long maxRTT;
    private long totalRTT;
    private int count;

    public RTTStatistics() {
        this.minRTT = Long.MAX_VALUE;
        this.maxRTT = 0;
        this.totalRTT = 0;
        this.count = 0;
    }

    public void addMeasurement(long rtt) {
        if (rtt <= 0) return;
        
        minRTT = Math.min(minRTT, rtt);
        maxRTT = Math.max(maxRTT, rtt);
        totalRTT += rtt;
        count++;
    }

    public String getStatistics() {
        if (count == 0) return "No measurements yet";
        double avg = totalRTT / (double) count;
        return String.format("RTT - Min: %d ms, Max: %d ms, Avg: %.1f ms", 
            minRTT, maxRTT, avg);
    }
} 