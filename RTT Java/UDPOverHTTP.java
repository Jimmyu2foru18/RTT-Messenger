import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Base64;

public class UDPOverHTTP {
    private final DatagramSocket udpSocket;

    public UDPOverHTTP(DatagramSocket socket) {
        this.udpSocket = socket;
    }

    public void sendMessage(String message) throws IOException {
        byte[] encodedData = Base64.getEncoder().encode(message.getBytes());
        DatagramPacket packet = new DatagramPacket(
            encodedData, encodedData.length,
            udpSocket.getLocalAddress(), udpSocket.getLocalPort()
        );
        udpSocket.send(packet);
    }

    public String receiveMessage() throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        udpSocket.receive(packet);
        byte[] data = Base64.getDecoder().decode(
            new String(packet.getData(), 0, packet.getLength())
        );
        return new String(data);
    }
} 