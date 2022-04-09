package org.chreso.edots;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class EdotActivity extends AppCompatActivity {
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
                new SyncOperations().startDataSync();
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
}
