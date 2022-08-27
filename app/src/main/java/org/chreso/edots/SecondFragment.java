package org.chreso.edots;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.HashMap;

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
        grid1 = view.findViewById(R.id.initial_phase);
        createCheckBoxGroupInGridLayout(grid1,60);
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
        String initiationData = getSelectedCheckBoxesFromGridLayout(grid1);
        dbHandler.saveEDOTPartBDataToDatabase();
    }

    private String getSelectedCheckBoxesFromGridLayout(GridLayout grid) {
        HashMap<String, String> map = new HashMap<>();
        for(int i=0;i<grid.getChildCount();i++){
            View view = grid.getChildAt(i);
            if(view instanceof CheckBox){
                CheckBox checkBox = ((CheckBox) view);
                if(checkBox.isChecked()){
                    String key = checkBox.getText().toString();
                    map.put(key , getClientInitials());
                }
            }
        }
        return "";
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
            checkBox.setScaleX((float) 1.3);
            checkBox.setScaleY((float) 1.5);
            checkBox.setTextColor(Color.LTGRAY);
            checkBox.setButtonTintList(ColorStateList.valueOf(Color.DKGRAY));
            //editText.setPadding(5,5,5,5);
            GridLayout.LayoutParams params =
                    new GridLayout.LayoutParams();
            params.rowSpec = GridLayout.spec(0,9,GridLayout.VERTICAL);
            params.setMargins(10,5,10,1);
            checkBox.setLayoutParams(params);
            grid.addView(checkBox);

        }


    }
}