package rafakob.multiedip.filebrowser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

import rafakob.multiedip.R;


public class FilebrowserAdapter extends BaseAdapter {

    private File[] dirItems;


    @Override
    public int getCount() {

        return dirItems.length;
    }

    @Override
    public Object getItem(int i) {

        return dirItems[i];
    }

    @Override
    public long getItemId(int i) {

        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater fileListInflater = LayoutInflater.from(parent.getContext());
        View itemView = fileListInflater.inflate(R.layout.filebrowser_item, parent, false);

        File item = dirItems[position];

        TextView itemName = (TextView) itemView.findViewById(R.id.filepicker_item_label);
        itemName.setText(item.getName());
        itemName.setSelected(true);

        ImageView itemIcon = (ImageView) itemView.findViewById(R.id.filepicker_item_icon);
        setRightIcon(item, itemIcon);

        return itemView;
    }

    public void getFilteredContentOfDirectory(String path) {

        FileFilter getDirsAndTxt = new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory() && !file.getName().startsWith("."))
                    return true;
                else return file.getName().toLowerCase().endsWith(".txt");
            }
        };

        dirItems = new File(path).listFiles(getDirsAndTxt);
        Arrays.sort(dirItems, new FilenameComparator());
    }


    public String getSelectedFileName(int position) {
        return dirItems[position].getName();
    }

    private void setRightIcon(File item, ImageView itemIcon) {

        if (item.isDirectory()) {
            itemIcon.setImageResource(R.drawable.ic_folder);
        } else if (item.getName().toLowerCase().endsWith(".txt")) {
            itemIcon.setImageResource(R.drawable.ic_txt);
        }
    }

}

