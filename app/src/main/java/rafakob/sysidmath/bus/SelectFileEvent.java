package rafakob.sysidmath.bus;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-03-22
 */
public class SelectFileEvent {
    public final String path;

    public SelectFileEvent(String path) {
        this.path = path;
    }
}
