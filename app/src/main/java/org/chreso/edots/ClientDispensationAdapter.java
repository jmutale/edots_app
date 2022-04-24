package org.chreso.edots;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ClientDispensationAdapter extends ArrayAdapter<ClientDispensation> {
    Context context;
    public ClientDispensationAdapter(Context context, ArrayList<ClientDispensation> clientDispensations){
        super(context,0,clientDispensations);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //Get the data item for this position
        ClientDispensation clientDispensation = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.client_dispensation_layout, parent, false);
        }

        // Lookup view for data population
        TextView drugName = (TextView) convertView.findViewById(R.id.drugName);
        TextView dispensationDate = (TextView) convertView.findViewById(R.id.dispensationDate);
        TextView unitsDispensed = (TextView) convertView.findViewById(R.id.unitsDispensed);
        TextView refillDate = (TextView) convertView.findViewById(R.id.refillDate);

        drugName.setText("Drug name: "+clientDispensation.getMedDrugName(clientDispensation.getMed_drug_uuid(), context));
        dispensationDate.setText("Dispensation date: "+clientDispensation.getDispensation_date());
        unitsDispensed.setText("Units dispensed: "+clientDispensation.getDose());
        refillDate.setText("Refill date: "+clientDispensation.getRefill_date());

        convertView.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.LTGRAY);

        return  convertView;
    }
}
