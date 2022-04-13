package org.chreso.edots;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ClientAdapter extends ArrayAdapter<Client> {
    public ClientAdapter(Context context, ArrayList<Client> clients){
        super(context,0,clients);
    }


    public View getView(int position, View convertView, ViewGroup parent){
        //Get the data item for this position
        Client client = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_result_layout, parent, false);
        }
        // Lookup view for data population
        TextView fName = (TextView) convertView.findViewById(R.id.fName);
        TextView lName = (TextView) convertView.findViewById(R.id.lName);
        TextView dob = (TextView) convertView.findViewById(R.id.sDob);
        TextView sex = (TextView) convertView.findViewById(R.id.sex);
        TextView m_phone = (TextView) convertView.findViewById(R.id.mPhone);
        // Populate the data into the template view using the data object
        fName.setText(client.getFirst_name());
        lName.setText(client.getLast_name());
        dob.setText(client.getDate_of_birth());
        sex.setText(client.getSex());
        m_phone.setText(client.getMobile_phone_number());

        convertView.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.LTGRAY);
        // Return the completed view to render on screen
        return convertView;

    }
}
