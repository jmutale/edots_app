package org.chreso.edots;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class EdotActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    AlertDialog.Builder builder;
    AlertDialog progressDialog;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sync_item:
                if(PreferenceManager
                        .getDefaultSharedPreferences(this).getString("server",null)!=null) {
                   // progressDialog = getDialogProgressBar().create();

                    new SyncOperations(getApplicationContext()).startDataSync();

                }else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .setTitle("Server Error")
                            .setMessage("Please assign a server URL value before you can sync data.")
                        .setCancelable(true)

                            ;
                    builder.create();
                    builder.show();
                }
                return true;
            case R.id.settings_item:
                openSettingsForm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSettingsForm() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public void setPrefs(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public AlertDialog.Builder getDialogProgressBar() {

        if (builder == null) {
            builder = new AlertDialog.Builder(this);

            builder.setTitle("Syncing...");
            builder.setMessage("Data sync in progress");

            final ProgressBar progressBar = new ProgressBar(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            progressBar.setLayoutParams(lp);
            builder.setView(progressBar);
        }
        return builder;
    }
}
