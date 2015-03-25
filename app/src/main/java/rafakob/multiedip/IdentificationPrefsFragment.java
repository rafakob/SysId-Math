package rafakob.multiedip;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-03-22
 */
public class IdentificationPrefsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.prefs_identification);
    }

}
