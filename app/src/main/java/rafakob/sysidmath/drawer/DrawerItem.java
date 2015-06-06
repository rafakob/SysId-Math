package rafakob.sysidmath.drawer;

import android.content.Context;


public class DrawerItem implements DrawerObject {

    private static final int OBJECT_TYPE = 1;
    private int id;
    private String label;
    private int icon;
    private boolean updateActionBarTitle;

    private DrawerItem() {
    }

    public static DrawerItem create(int id, String label, String icon, boolean updateActionBarTitle, Context context) {
        DrawerItem item = new DrawerItem();
        item.setId(id);
        item.setLabel(label);
        item.setIcon(context.getResources().getIdentifier(icon, "drawable", context.getPackageName()));
        item.setUpdateActionBarTitle(updateActionBarTitle);
        return item;
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

    public int getIcon() {
        return icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setUpdateActionBarTitle(boolean updateActionBarTitle) {
        this.updateActionBarTitle = updateActionBarTitle;
    }

    @Override
    public boolean updateActionBarTitle() {
        return this.updateActionBarTitle;
    }

    @Override

    public boolean isEnabled() {
        return true;
    }

}