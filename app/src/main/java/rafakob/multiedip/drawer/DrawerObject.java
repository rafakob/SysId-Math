package rafakob.multiedip.drawer;

public interface DrawerObject {

    public int getId();
    public int getType();
    public String getLabel();
    public boolean isEnabled();
    public boolean updateActionBarTitle();
}