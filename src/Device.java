public class Device {

    private byte id;

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        if (id >= 0)
            this.id = id;
        else
            return;
    }


}
