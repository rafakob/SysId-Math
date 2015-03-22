package rafakob.multiedip;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;


public class ProjectPrefsActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTheme(R.style.PreferenceScreen);
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PreprocessingPrefsFragment())
                .commit();
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
}