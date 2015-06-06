package rafakob.sysidmath.filebrowser;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

final class FilenameComparator implements Comparator, Serializable {

    public int compare(Object o1, Object o2) {
        try {
            File f1 = (File) o1;
            File f2 = (File) o2;

            if (f1.isDirectory() && !f2.isDirectory()) {
                return -1;
            } else if (!f1.isDirectory() && f2.isDirectory()) {
                return 1;
            } else {
                return f1.getName().toLowerCase().compareTo(f2.getName().toLowerCase());
            }
        } catch (ClassCastException ex) {}

        return 0;
    }
}