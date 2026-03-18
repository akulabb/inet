import java.util.ArrayList;

public class MeshSpace {
    private ArrayList<Device> devices = new ArrayList<>();

    public void addDevice(Device device) {
        devices.add(device);
    }

    public void printDevices() {
        devices.forEach(device -> System.out.println("device: " + device.getName()));
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public void sendPacket(byte[] packet, Device sender) {
        System.out.println("Received packet from " + sender.getName());
            for (Device device : devices) {
                if (device != sender)
                    device.receivePacket(packet);
        }
    }

}
