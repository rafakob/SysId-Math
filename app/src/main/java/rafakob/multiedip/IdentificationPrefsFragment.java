package rafakob.multiedip;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
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

        // Setup listeners for custom radio group
        super.findPreference("id_flag_parametric")
                .setOnPreferenceChangeListener( new Preference.OnPreferenceChangeListener(){
                    @Override
                    public boolean onPreferenceChange(Preference pref, Object newVal) {
                        if((Boolean) newVal==true) {

                            // User just turned off checkbox - take necessary action here
                            ((CheckBoxPreference) getPreferenceManager().findPreference("id_flag_nonparametric")).setChecked(false);
                            return true;
                        }
                        return false; // Finally, let the checkbox value go through to update itself
                    }
                });


        super.findPreference("id_flag_nonparametric")
                .setOnPreferenceChangeListener( new Preference.OnPreferenceChangeListener(){
                    @Override
                    public boolean onPreferenceChange(Preference pref, Object newVal) {
                        if((Boolean) newVal==true) {

                            // User just turned off checkbox - take necessary action here
                            ((CheckBoxPreference) getPreferenceManager().findPreference("id_flag_parametric")).setChecked(false);
                            return true;
                        }
                        return false; // Finally, let the checkbox value go through to update itself
                    }
                });

    }

}
