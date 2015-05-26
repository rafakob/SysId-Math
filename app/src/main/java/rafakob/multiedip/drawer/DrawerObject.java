package rafakob.multiedip.drawer;

public interface DrawerObject {

    int getId();
    int getType();
    String getLabel();
    boolean isEnabled();
    boolean updateActionBarTitle();
}