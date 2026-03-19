import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MeshSpace {
    private final ExecutorService executor = Executors.newFixedThreadPool(5);
    private final ArrayList<Device> devices = new ArrayList<>();

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
        System.out.println("Space received packet from " + sender.getName());
            for (int i = 0; i < devices.toArray().length; i++) {
                Device device = devices.get(i);
                if (device != sender) {
                    executor.execute(() -> device.receivePacket(packet));
                }
        }
    }

}
