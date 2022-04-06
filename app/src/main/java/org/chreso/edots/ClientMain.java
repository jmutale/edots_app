package org.chreso.edots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClientMain extends AppCompatActivity {

    private Button btnDispenseDrugToClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        btnDispenseDrugToClient = findViewById(R.id.btnDispenseDrugToClient);
        btnDispenseDrugToClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDispensationActivity();
            }
        });
    }

    private void openDispensationActivity() {
        Intent intent = new Intent(this, DispensationActivity.class);
        startActivity(intent);
    }


}