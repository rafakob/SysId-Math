package rafakob.multiedip.drawer;


public class DrawerHeader implements DrawerObject {

    private static final int OBJECT_TYPE = 0;
    private int id;
    private String label;

    private DrawerHeader() {
    }

    public static DrawerHeader create(int id, String label) {
        DrawerHeader header = new DrawerHeader();
        header.setLabel(label);
        return header;
    }

    @Override
    public int getType() {
        return OBJECT_TYPE;
    }
    public int getId() {
        return id;
    }
    public String getLabel() {
        return label;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean updateActionBarTitle() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}