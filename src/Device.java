import org.jetbrains.annotations.NotNull;

import java.util.HexFormat;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Device {

    private Router router;
    private String name;
    private byte[] address;


    public byte[] getAddress() {
        return address;
    }

    public void setAddress(byte[] address) {
        this.address = address;
    }

    public String getStringAddress() {
        return HexFormat.ofDelimiter("").formatHex(address);
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printAvailableDevices() {
        System.out.println(router.getDevices());
    }

    public byte[] createPacket(@NotNull String message, byte @NotNull [] targetAddress) {
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);

        ByteBuffer bufPacket = ByteBuffer.allocate(address.length+1+messageBytes.length+targetAddress.length);

        bufPacket.put(targetAddress);
        bufPacket.put((byte) messageBytes.length);
        bufPacket.put(messageBytes);
        bufPacket.put(address);

        return bufPacket.array();
    }

    public void receivePacket(byte[] packet) {
        System.out.println("Received packet");
        for (byte i = 0; i <= address.length-1; i++) {
            if (packet[i] != address[i]) {
                transferPacket(packet);
                return;
            }
            parsePacket(packet);
        }
    }

    private void transferPacket(byte[] packet) {
        return;                                         //todo make transferring mechanism
    }

    private void parsePacket(byte[] packet) {
        System.out.println("Parsing packet...");
        ByteBuffer bufPacket = ByteBuffer.wrap(packet);
        bufPacket.position(address.length);

        int length = bufPacket.get() & 0xFF;
        byte[] messageBytes = new byte[length];

        bufPacket.get(messageBytes);
        int senderAddressLength = bufPacket.remaining();
        byte[] senderAddressByte = new byte[senderAddressLength];
        bufPacket.get(senderAddressByte);

        String message = new String(messageBytes, StandardCharsets.UTF_8);
        String senderAddress = HexFormat.ofDelimiter("").formatHex(senderAddressByte);

        System.out.println("Message: " + message + " from: " + senderAddress);
        //return message;
    }

}
