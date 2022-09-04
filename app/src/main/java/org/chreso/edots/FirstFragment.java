package org.chreso.edots;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DBHandler dbHandler;
    private Button btnSubmitClientPartADotCard;
    private LinearLayout layoutTypeOfTb,layoutTreatmentOutcome, layoutTypeOfRegimen, layoutDiseaseSite;
    private DatePicker dteDateOfDecision;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
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
        dbHandler = new DBHandler(getActivity());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_client_dotcard_part_aactivity, container, false);
        layoutTypeOfTb = view.findViewById(R.id.layoutTypeOfTb);
        layoutTreatmentOutcome = view.findViewById(R.id.layoutTreatmentOutcome);
        dteDateOfDecision = view.findViewById(R.id.dteTxOutcomeDateOfDecision);
        layoutTypeOfRegimen = view.findViewById(R.id.layoutTypeOfRegimen);
        layoutDiseaseSite = view.findViewById(R.id.layoutDiseaseSite);
        btnSubmitClientPartADotCard = view.findViewById(R.id.btnSubmitClientPartADOTCard);
        btnSubmitClientPartADotCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClientPartADOTCardData();
            }
        });

        return view;
    }

    private void saveClientPartADOTCardData() {
        ClientDOTCardActivity activity = (ClientDOTCardActivity)getActivity();
        String client_uuid = activity.getClientUuid();
        String dot_card_uuid = Utils.getNewUuid();
        String typeOfTb = Utils.getSelectedCheckboxValuesFromCheckboxGroup(layoutTypeOfTb);
        String treatmentOutcome = Utils.getSelectedCheckboxValuesFromCheckboxGroup(layoutTreatmentOutcome);
        String dateOfDecision = Utils.getDateFromDatePicker(dteDateOfDecision);
        String typeOfRegimen = Utils.getSelectedCheckboxValuesFromCheckboxGroup(layoutTypeOfRegimen);
        String diseaseSite = Utils.getSelectedCheckboxValuesFromCheckboxGroup(layoutDiseaseSite);
        dbHandler.saveEDOTPartADataToDatabase(dot_card_uuid,client_uuid,typeOfTb,treatmentOutcome,dateOfDecision,typeOfRegimen,diseaseSite);
    }
}