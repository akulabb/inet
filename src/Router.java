public class Router {
    private Device[] devices = new Device[127];

    public byte getId (Device device) {
        for (byte i=0; i<devices.length; i++){
            //System.out.println(i);
            if (devices[i] == null || devices[i] == device) {
                devices[i] = device;
                device.setId(++i);
                return i;
            }
        }
        return -1;
    }

    public void printDevices () {
        for (byte i=0; i<devices.length; i++) {
            Device device = devices[i];
            if (device == null)
                return;
            System.out.println(device.getId());
        }
    }
}