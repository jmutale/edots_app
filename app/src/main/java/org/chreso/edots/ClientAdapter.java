package org.chreso.edots;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClientAdapter extends ArrayAdapter<Client> {

    private List<Client> clientListFull;

    public ClientAdapter(Context context, ArrayList<Client> clients){
        super(context,0,clients);
        clientListFull = new ArrayList<>(clients);

    }

    public Filter getFilter() {
        return clientFilter;
    }

    private Filter clientFilter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            List<Client> suggestions = new ArrayList<>();
            if (charSequence == null||charSequence.length()==0){
                suggestions.addAll(clientListFull);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(Client item : clientListFull){
                    if(item.getFirst_name().toLowerCase().contains(filterPattern)){
                        suggestions.add(item);
                    }else if(item.getLast_name().toLowerCase().contains(filterPattern)){
                        suggestions.add(item);
                    }else if(item.getMobile_phone_number().toLowerCase().contains(filterPattern)){
                        suggestions.add(item);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            addAll((List)filterResults.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return super.convertResultToString(resultValue);
        }
    };

    public View getView(int position, View convertView, ViewGroup parent){
        //Get the data item for this position
        Client client = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_result_layout, parent, false);
        }
        // Lookup view for data population
        TextView fName =  convertView.findViewById(R.id.fName);
        TextView lName =  convertView.findViewById(R.id.lName);
        TextView dob =  convertView.findViewById(R.id.sDob);
        TextView sex =  convertView.findViewById(R.id.sex);
        TextView m_phone =  convertView.findViewById(R.id.mPhone);
        // Populate the data into the template view using the data object
        if(client!=null) {
            fName.setText(client.getFirst_name());
            lName.setText(client.getLast_name());
            dob.setText(client.getDate_of_birth());
            sex.setText(client.getSex());
            m_phone.setText(client.getMobile_phone_number());
        }



        convertView.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.LTGRAY);
        // Return the completed view to render on screen
        return convertView;

    }

    }

