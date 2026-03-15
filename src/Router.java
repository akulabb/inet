import java.util.HashMap;

public class Router {
    private final HashMap<Byte, Device> devices = new HashMap<>();

    public void registerDevice (Device device) {
        for (byte id=1; id<127; id++) {
            //System.out.println(id);
            if (devices.get(id) == null || devices.get(id) == device) {
                devices.put(id, device);
                device.setId(id);
                device.setName("KUB" + id);
                return;
            }
        }
    }

    public void printDevices () {
        devices.forEach((id, device) -> System.out.println("Device " + device.getName() + " on place " + id + " has id " + device.getId()));
    }
}