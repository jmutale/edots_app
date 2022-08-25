package org.chreso.edots;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class ClientDOTCardPartBActivity extends AppCompatActivity {

    private Button btnSubmitClientDOTCardActivity;
    private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dotcard_part_bactivity);
        dbHandler = new DBHandler(this);
        btnSubmitClientDOTCardActivity = findViewById(R.id.btnSubmitDOTCardPartB);
        GridLayout grid = findViewById(R.id.initial_phase);
        createInitiationCheckBoxGroup(grid);
        GridLayout grid2 = findViewById(R.id.continuation_phase1);
        createContinuationPhaseCheckBoxGroup(grid2);
        GridLayout grid3 = findViewById(R.id.continuation_phase2);
        createContinuationPhaseCheckBoxGroup(grid3);
        GridLayout grid4 = findViewById(R.id.continuation_phase3);
        createContinuationPhaseCheckBoxGroup(grid4);
        GridLayout grid5 = findViewById(R.id.continuation_phase4);
        createContinuationPhaseCheckBoxGroup(grid5);
        btnSubmitClientDOTCardActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.saveEDOTPartBDataToDatabase();
            }
        });

    }

    private void createContinuationPhaseCheckBoxGroup(GridLayout grid2) {
        int checkBoxId = 1000;
        for(int i=1;i<=30;i++){

            EditText editText = new EditText(this);
            editText.setId(++checkBoxId);
            editText.setHint(String.valueOf(i));
            editText.setScaleX((float) 3);
            editText.setScaleY((float) 2);
            //editText.setPadding(5,5,5,5);
            GridLayout.LayoutParams params =
                    new GridLayout.LayoutParams();
            params.rowSpec = GridLayout.spec(0,9,GridLayout.VERTICAL);
            params.setMargins(15,5,15,5);
            editText.setLayoutParams(params);
            grid2.addView(editText);

        }
    }

    private void createInitiationCheckBoxGroup(GridLayout grid) {
        int checkBoxId = 1000;
        for(int i=1;i<=61;i++){
            EditText editText = new EditText(this);
            editText.setId(++checkBoxId);
            editText.setText(String.valueOf(i));
            editText.setScaleX((float) 5);
            editText.setScaleY((float) 1.5);
            editText.setPadding(10,10,10,10);
            GridLayout.LayoutParams params =
                    new GridLayout.LayoutParams();
            params.rowSpec = GridLayout.spec(0,8,GridLayout.VERTICAL);
            //params.columnSpec = GridLayout.spec(0,7,GridLayout.FILL);
            editText.setLayoutParams(params);
            grid.addView(editText);

        }
    }
}