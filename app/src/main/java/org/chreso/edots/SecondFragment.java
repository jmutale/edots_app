package org.chreso.edots;

import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.internal.ws.WebSocketExtensions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnSubmitClientDOTCardPartBActivity;
    private GridLayout grid1,grid2,grid3,grid4,grid5;
    private DBHandler dbHandler;
    private DatePicker dteInitiationStartDate;
    private EditText editObserverName, editDOTPlan, editStartWeight;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_client_dotcard_part_bactivity, container, false);
        dbHandler = new DBHandler(getActivity());
        btnSubmitClientDOTCardPartBActivity = view.findViewById(R.id.btnSubmitDOTCardPartB);
        dteInitiationStartDate = view.findViewById(R.id.editInitiationStartDate);
        editObserverName = view.findViewById(R.id.editObserverName);
        editStartWeight = view.findViewById(R.id.editStartWeight);
        editDOTPlan = view.findViewById(R.id.editDOTPlan);
        grid1 = view.findViewById(R.id.initial_phase);
        createCheckBoxGroupInGridLayout(grid1,30);
        grid2 = view.findViewById(R.id.continuation_phase1);
        createCheckBoxGroupInGridLayout(grid2,30);
        grid3 = view.findViewById(R.id.continuation_phase2);
        createCheckBoxGroupInGridLayout(grid3,30);
        grid4 = view.findViewById(R.id.continuation_phase3);
        createCheckBoxGroupInGridLayout(grid4,30);
        grid5 = view.findViewById(R.id.continuation_phase4);
        createCheckBoxGroupInGridLayout(grid5, 30);
        btnSubmitClientDOTCardPartBActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDOTCardPartBToDatabase();
            }
        });

        return view;
    }

    private void saveDOTCardPartBToDatabase() {
        String dot_card_uuid = Utils.getNewUuid();
        ClientDOTCardActivity activity = (ClientDOTCardActivity)getActivity();
        String client_uuid = activity.getClientUuid();
        String initial_phase_start_date = Utils.getDateFromDatePicker(dteInitiationStartDate);
        String observer = editObserverName.getText().toString();
        String dotPlan = editDOTPlan.getText().toString();
        String startWeight = editStartWeight.getText().toString();
        String dotPlanInitiationData =getSelectedCheckBoxesFromGridLayout(grid1);
        String continuationPhaseStartDate = "";
        String dotPlanContinuationDataMonth1 = getSelectedCheckBoxesFromGridLayout(grid2);
        String dotPlanContinuationDataMonth2 = getSelectedCheckBoxesFromGridLayout(grid3);
        String dotPlanContinuationDataMonth3 = getSelectedCheckBoxesFromGridLayout(grid4);
        String dotPlanContinuationDataMonth4 = getSelectedCheckBoxesFromGridLayout(grid5);
        dbHandler.saveEDOTPartBDataToDatabase(dot_card_uuid,
                client_uuid,
                initial_phase_start_date,
                observer,
                dotPlan,
                startWeight,
                dotPlanInitiationData,
                continuationPhaseStartDate,
                dotPlanContinuationDataMonth1,
                dotPlanContinuationDataMonth2,
                dotPlanContinuationDataMonth3,
                dotPlanContinuationDataMonth4);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Success")
                .setMessage("EDOT Card Part B successfully saved. Please remember to save Part A.")
                .setCancelable(true)
                ;
        builder.create();
        builder.show();
    }

    private String getSelectedCheckBoxesFromGridLayout(GridLayout grid) {
        HashMap<String, String> map = new HashMap<>();
        for(int i=1;i<=grid.getChildCount();i++){
            View view = grid.getChildAt(i);
            if(view instanceof CheckBox){
                CheckBox checkBox = ((CheckBox) view);
                if(checkBox.isChecked()){

                    map.put(String.valueOf(i) , getClientInitials());
                }
            }
        }
        return map.toString();
    }

    private String getClientInitials() {
        return "TT";
    }



    private void createCheckBoxGroupInGridLayout(GridLayout grid, int numberOfCheckBoxes) {
        int checkBoxId = 1000;
        for(int i=1;i<=numberOfCheckBoxes;i++){
            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setId(++checkBoxId);
            checkBox.setHint(String.valueOf(i));
            checkBox.setScaleX((float) 1.2);
            checkBox.setScaleY((float) 1.4);
            checkBox.setTextColor(Color.LTGRAY);
            checkBox.setButtonTintList(ColorStateList.valueOf(Color.DKGRAY));
            //editText.setPadding(5,5,5,5);
            GridLayout.LayoutParams params =
                    new GridLayout.LayoutParams();
            params.rowSpec = GridLayout.spec(0,6,GridLayout.VERTICAL);
            params.setMargins(10,5,10,1);
            checkBox.setLayoutParams(params);
            grid.addView(checkBox);

        }




    }

    private void createSpinner(GridLayout grid, int numberSpinners) {
        for(int i=1;i<=numberSpinners;i++) {
            ArrayList<String> spinnerArray = new ArrayList<String>();
            spinnerArray.add(String.valueOf(i));
            spinnerArray.add("MM");
            spinnerArray.add("O");
            Spinner spinner = new Spinner(getActivity());
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerArray){
                @Override
                public boolean isEnabled(int position) {
                    if(position == 0)
                    {
                        // Disable the first item from Spinner
                        // First item will be use for hint
                        return false;
                    }
                    else
                    {
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if(position == 0){
                        // Set the hint text color gray
                        tv.setTextColor(Color.GRAY);
                    }
                    else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };
            spinner.setAdapter(spinnerArrayAdapter);
            GridLayout.LayoutParams params =
                    new GridLayout.LayoutParams();
            params.rowSpec = GridLayout.spec(0, 9, GridLayout.VERTICAL);
            //params.columnSpec = GridLayout.spec(0,6,GridLayout.HORIZONTAL);
            params.setMargins(1, 3, 1, 1);
            spinner.setLayoutParams(params);


            grid.addView(spinner);//you add the whole RadioGroup to the layout
        }

    }
}