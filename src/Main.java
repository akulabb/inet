public class Main {
    public static void main(String[] args) {
        MeshSpace space = new MeshSpace();
        Router router1 = new Router(space);
        Device device1 = new Device(space), device2 = new Device(space), device3 = new Device(space), device4 = new Device(space);
        router1.registerDevice(device1);
        router1.registerDevice(device2);
        router1.registerDevice(device3);
        router1.registerDevice(device4);
        //router1.printDevices();
        space.printDevices();
        //router1.printAvailableDevices();
        device1.sendPacket("Hello world!", device3.getAddress());
        //device1.transferPacket("127~33~10~5");
        //device1.printAvailableDevices();
    }
}