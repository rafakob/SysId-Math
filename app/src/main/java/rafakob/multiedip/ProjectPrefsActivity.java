package rafakob.multiedip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import de.greenrobot.event.EventBus;
import rafakob.multiedip.bus.SelectFileEvent;
import rafakob.multiedip.bus.SettingsChangedEvent;


public class ProjectPrefsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTheme(R.style.PreferenceScreen);
        // Display the fragment as the main content.

        Intent intent = getIntent();
        String mode = intent.getStringExtra("PROJECT_SETTINGS_MODE");

        if (mode.equals("preprocessing")) {
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new PreprocessingPrefsFragment())
                    .commit();
            setTitle(getString(R.string.activity_title_preprocessing));
        } else {
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new IdentificationPrefsFragment())
                    .commit();
            setTitle(getString(R.string.activity_title_identification));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus bus = EventBus.getDefault();
        bus.post(new SettingsChangedEvent());


    }
}