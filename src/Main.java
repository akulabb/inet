public class Main {
    public static void main(String[] args) {
        Router router1 = new Router();
        Device device1 = new Device(), device2 = new Device(), device3 = new Device(), device4 = new Device();
        router1.registerDevice(device1);
        router1.registerDevice(device2);
        router1.registerDevice(device3);
        router1.registerDevice(device4);
        router1.printDevices();
        router1.printAvailableDevices();
        byte[] packet = device1.createPacket("Hello world!", device3.getAddress());
        device3.receivePacket(packet);
        //device1.transferPacket("127~33~10~5");
        //device1.printAvailableDevices();
    }
}