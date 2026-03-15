public class Main {
    public static void main(String[] args) {
        Router router1 = new Router();
        Device device1 = new Device();
        Device device2 = new Device();
        Device device3 = new Device();
        Device device4 = new Device();
        router1.registerDevice(device1);
        router1.registerDevice(device2);
        router1.registerDevice(device3);
        router1.registerDevice(device4);
        router1.registerDevice(device1);
        router1.registerDevice(device1);
        router1.registerDevice(device2);
        router1.registerDevice(device1);
        router1.printDevices();
    }
}