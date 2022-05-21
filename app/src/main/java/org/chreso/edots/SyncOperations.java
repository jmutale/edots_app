package org.chreso.edots;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SyncOperations {
    private DBHandler dbHandler;
    private Context myContext;

    public SyncOperations(Context context) {

        this.dbHandler = new DBHandler(context);
        this.myContext = context;
    }

    public void getClientDataFromServer(){

    }

    public void startDataSync(){
            syncMedDrugs();
            syncFacilityData();
            syncClientData();
            syncDrugDispensations();

    }

    private void syncFacilityData() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(PreferenceManager
                        .getDefaultSharedPreferences(myContext).getString("server",null))
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<List<Location>> call = api.getLocations("Token "+getAuthToken());

        call.enqueue(new Callback<List<Location>>() {

            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                for(Location l : response.body()) {
                    dbHandler.addNewLocation(l.getUuid(),l.getName(), l.getCode(),l.getSupported(),l.getType(),l.getPoint(),l.getParent());
                    Toast.makeText(myContext, "Syncing location: "+l.getName(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Toast.makeText(myContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void syncDrugDispensations() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(PreferenceManager
                        .getDefaultSharedPreferences(myContext).getString("server",null))
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        ArrayList<ClientDispensation> listOfClientDispensationsFromDatabase = dbHandler.getListOfClientDispensationsFromDatabase();
        for(ClientDispensation cd : listOfClientDispensationsFromDatabase){

            ClientDispensationEvent cde = setValuesForClientDispensationEvent(cd);
            Call<ClientDispensation> call = api.postDispensationData(cde, "Token "+getAuthToken());

            call.enqueue(new Callback<ClientDispensation>() {
                @Override
                public void onResponse(Call<ClientDispensation> call, Response<ClientDispensation> response) {
                    Toast.makeText(myContext, "Syncing dispensations: "+cd.getMedDrugName(cd.getMed_drug_uuid(),myContext), Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Call<ClientDispensation> call, Throwable t) {
                    //Toast.makeText(myContext, t.getMessage(), Toast.LENGTH_LONG).show();
                }


            });

        }

    }

    private ClientDispensationEvent setValuesForClientDispensationEvent(ClientDispensation cd) {
        ClientDispensationEvent cde = new ClientDispensationEvent();
        cde.setClient_uuid(cd.getClient_uuid());
        cde.setDispensation_date(cd.getDispensation_date());
        cde.setMed_drug_uuid(cd.getMed_drug_uuid());
        cde.setRefill_date(cd.getRefill_date());
        cde.setDose(cd.getDose());
        cde.setItems_per_dose(cd.getItems_per_dose());
        cde.setVideo_path(cd.getVideo_path());
        return cde;
    }

    private void syncClientData() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(PreferenceManager
                        .getDefaultSharedPreferences(myContext).getString("server",null))
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<List<Client>> call = api.getClients("Token "+getAuthToken());

        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                for (Client client: response.body()) {

                    dbHandler.addNewClient(client.getUuid(),client.getNrc_number(),client.getChreso_id(), client.getArt_number(), client.getFirst_name(), client.getLast_name(), client.getDate_of_birth(), client.getSex(), client.getMobile_phone_number());
                    Toast.makeText(myContext, "Syncing client: "+client.getNrc_number(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                Toast.makeText(myContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void syncMedDrugs() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(PreferenceManager
                        .getDefaultSharedPreferences(myContext).getString("server",null))
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        String token = getAuthToken();

        Call<List<MedDrug>> call = api.getMedDrugs("Token "+getAuthToken());

        call.enqueue(new Callback<List<MedDrug>>() {
            @Override
            public void onResponse(Call<List<MedDrug>> call, Response<List<MedDrug>> response) {
                for (MedDrug drug: response.body()) {

                    dbHandler.addNewMedDrug(drug.getUuid(), drug.getGeneric_name(), drug.getBrand_name(), drug.getFormulation(), drug.getGeneric_ingredients(), drug.getGeneric_strength());
                    Toast.makeText(myContext, "Syncing drug: "+drug.getGeneric_name(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MedDrug>> call, Throwable t) {
                Toast.makeText(myContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }


        });
    }

    private String getAuthToken() {
        String toReturn = "";
        if(PreferenceManager
                .getDefaultSharedPreferences(myContext).getString("token",null)!=null){
            toReturn = PreferenceManager
                    .getDefaultSharedPreferences(myContext).getString("token",null);
        }
        return toReturn;
    }
}
