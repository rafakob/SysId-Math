package rafakob.multiedip.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import rafakob.multiedip.R;


public class DrawerAdapter extends ArrayAdapter<DrawerObject> {

    private LayoutInflater mInflater;
    private static final int DRAWER_TYPE_HEADER = 0;
    private static final int DRAWER_TYPE_ITEM = 1;


    public DrawerAdapter(Context context, int textViewResourceId, DrawerObject[] elements) {
        super(context, textViewResourceId, elements);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DrawerObject drawerObject = this.getItem(position); // get object from its position in ArrayAdapter
        View drawerObjectView; // it will be either Item or Header

        if (drawerObject.getType() == DRAWER_TYPE_ITEM) {
            drawerObjectView = getItemView(convertView, parent, drawerObject);
        } else {
            drawerObjectView = getHeaderView(convertView, parent, drawerObject);
        }
        return drawerObjectView;
    }

    /**
     * Returns view for drawer object 'Item'.
     */
    public View getItemView(View convertView, ViewGroup parentView, DrawerObject DrawerObject) {

        DrawerItem drawerItem = (DrawerItem) DrawerObject;
        DrawerItemHolder drawerItemHolder = null;

        // "Holder Pattern" - inflate layout only once to reduce loading time.
        // (well... not exactly once, inflate when convertView doesn't have this kind of layout
        // in 'view memory') -> http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.drawer_list_item, parentView, false);
            drawerItemHolder = new DrawerItemHolder();

            drawerItemHolder.labelView = (TextView) convertView
                    .findViewById(R.id.drawer_item_label);
            drawerItemHolder.iconView = (ImageView) convertView
                    .findViewById(R.id.drawer_item_icon);

            convertView.setTag(drawerItemHolder);
        }

        if (drawerItemHolder == null) {
            drawerItemHolder = (DrawerItemHolder) convertView.getTag();
        }

        drawerItemHolder.labelView.setText(drawerItem.getLabel());
        drawerItemHolder.iconView.setImageResource(drawerItem.getIcon());

        return convertView;
    }

    /**
     * Returns view for menu object 'Header'.
     */
    public View getHeaderView(View convertView, ViewGroup parentView, DrawerObject DrawerObject) {

        DrawerHeader menuHeader = (DrawerHeader) DrawerObject;
        DrawerHeaderHolder drawerHeaderHolder = null;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.drawer_list_header, parentView, false);
            drawerHeaderHolder = new DrawerHeaderHolder();

            drawerHeaderHolder.labelView = (TextView) convertView
                    .findViewById(R.id.menu_header_label);

            convertView.setTag(drawerHeaderHolder);
        }

        if (drawerHeaderHolder == null) {
            drawerHeaderHolder = (DrawerHeaderHolder) convertView.getTag();
        }

        drawerHeaderHolder.labelView.setText(menuHeader.getLabel());

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return this.getItem(position).getType();
    }

    @Override
    public boolean isEnabled(int position) {
        return getItem(position).isEnabled();
    }

    /**
     * Holders are lightweight inner classes that hold direct references
     * to all inner views from a list row.
     */
    private static class DrawerItemHolder {
        private TextView labelView;
        private ImageView iconView;
    }

    private class DrawerHeaderHolder {
        private TextView labelView;
    }
}
