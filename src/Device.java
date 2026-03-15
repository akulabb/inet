public class Device {

    private String name;
    private byte id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        if (id >= 0)
            this.id = id;
    }


}
