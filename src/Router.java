import java.util.HashMap;

public class Router extends Device {
    private final HashMap<Byte, Device> devices = new HashMap<>();

    public Router() {
        setRouter(this);
        registerDevice(this);
    }

    public void registerDevice (Device device) {
        byte[] deviceAddress = new byte[1];
        for (byte id=1; id<127; id++) {
            //System.out.println(id);
            if (devices.get(id) == null || devices.get(id) == device) {
                deviceAddress[0] = id;
                devices.put(id, device);
                device.setAddress(deviceAddress);
                device.setName("KUB" + id);
                device.setRouter(this);
                return;
            }
        }
    }

    public HashMap<Byte, String> getDevices () {
        HashMap<Byte, String> devicesToReturn = new HashMap<>();
        devices.forEach((id, device) -> devicesToReturn.put(id, device.getName()));
        return devicesToReturn;
    }

    public void printDevices () {

        devices.forEach((id, device) -> System.out.println("Device " + device.getName() + " on place " + id + " has address " + device.getStringAddress()));
    }
}