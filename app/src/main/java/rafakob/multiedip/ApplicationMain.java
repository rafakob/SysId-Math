package rafakob.multiedip;

import android.app.Application;

import com.pixplicity.easyprefs.library.Prefs;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-03-29
 */
public class ApplicationMain  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Prefs.initPrefs(this);

    }
}
