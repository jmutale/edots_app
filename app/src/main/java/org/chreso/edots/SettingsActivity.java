package org.chreso.edots;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Map;

public class SettingsActivity extends AppCompatActivity {
    private static DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        dbHandler = new DBHandler(getApplicationContext());
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            final ListPreference listPreference = (ListPreference) findPreference("facility");

            setListPreferenceData(listPreference);
        }

        private static void setListPreferenceData(ListPreference lp) {
            Map<String, String> facilities = dbHandler.getListOfHealthFacilitiesFromDatabase();
            CharSequence[] entries = facilities.values().toArray(new CharSequence[facilities.size()]);
            CharSequence[] entryValues = facilities.keySet().toArray(new CharSequence[facilities.size()]);
            lp.setEntries(entries);
            lp.setDefaultValue("11111");
            lp.setEntryValues(entryValues);
        }
    }
}