import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HexFormat;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Device {

    private Router router;
    private String name;
    private byte[] address;
    private final MeshSpace meshSpace;
    private final byte[][] lastPackets = new byte[5][];

    public Device(MeshSpace space) {
        meshSpace = space;
    }

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

    private void writePacket(byte[] packet) {
        byte[] packetToWrite;
        byte[] packetToSave = new byte[packet.length];
        int lastIndex = lastPackets.length-1;

        for (int i = lastIndex; i>=0; i--) {
            packetToWrite = packetToSave;
            packetToSave = lastPackets[i];
            lastPackets[i] = packetToWrite;
        }

        System.arraycopy(packet, 0, lastPackets[lastIndex], 0, packet.length);
        lastPackets[lastIndex][address.length] = 0;
    }

    private boolean packetWasHere(byte[] packet) {
        boolean packetWasHere = false;
        byte[] cleanPacket = packet.clone();
        cleanPacket[address.length] = 0;
        for (byte[] packetToCompare: lastPackets) {
            if (Arrays.equals(cleanPacket, packetToCompare)) {
                packetWasHere = true;
                System.out.println(getName() + " has received a duplicate packet");
            }
        }
        return packetWasHere;
    }

    private byte[] createPacket(@NotNull String message, byte @NotNull [] targetAddress, byte timeToLive) {
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);

        ByteBuffer bufPacket = ByteBuffer.allocate(
                address.length
                        +2 //TTL + message length
                        +messageBytes.length
                        +targetAddress.length
        );

        bufPacket.put(targetAddress);
        bufPacket.put(timeToLive);
        bufPacket.put((byte) messageBytes.length);
        bufPacket.put(messageBytes);
        bufPacket.put(address);

        return bufPacket.array();
    }

    public void receivePacket(byte[] packet) {
        if (!packetWasHere(packet)) {
            writePacket(packet);
            byte[] newPacket = packet.clone();
            System.out.println(getName() + " received packet with ttl " + newPacket[address.length]);
            for (byte i = 0; i <= address.length - 1; i++) {
                if (newPacket[i] != address[i]) {
                    transferPacket(newPacket);
                    return;
                }
            }
            parsePacket(packet);
        }
    }

    public void sendPacket(String message, byte[] destinationAddress) {
        byte[] packet = createPacket(message, destinationAddress, (byte) 1);
        meshSpace.sendPacket(packet, this);
    }

    private void transferPacket(byte[] packet) {
        byte ttl = packet[address.length];
        if (ttl > 0) {
            packet[address.length] = (byte) (ttl - 1);
            meshSpace.sendPacket(packet, this);
        }
    }

    private void parsePacket(byte[] packet) {
        System.out.println("Parsing packet...");
        ByteBuffer bufPacket = ByteBuffer.wrap(packet);
        bufPacket.position(address.length+1);

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
