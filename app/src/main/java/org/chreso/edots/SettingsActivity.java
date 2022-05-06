package org.chreso.edots;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

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
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            final ListPreference listPreference = (ListPreference) findPreference("facility");

            setListPreferenceData(listPreference);
        }

        protected static void setListPreferenceData(ListPreference lp) {
            CharSequence[] entries = { "Petauke District Hospital", "Petauke Urban" };
            CharSequence[] entryValues = {"11111" , "22222"};
            lp.setEntries(entries);
            lp.setDefaultValue("11111");
            lp.setEntryValues(entryValues);
        }
    }
}