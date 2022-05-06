package org.chreso.edots;

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

    public void sendClientDataToServer() {
        DateTimeFormatter formatter_3 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String str_date_3 = "24-09-2019";
        LocalDate local_date_3 = LocalDate.parse(str_date_3, formatter_3);

        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<Client> call = apiInterface.getClientData("Johnnie",
                "Daka",
                local_date_3,
                "male",
                "+260978895623");
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                Log.e("Send Client Data","onResponse: "+response.code());
                Log.e("Send Client Data","onResponse: first_name : "+response.body().getFirst_name());
                Log.e("Send Client Data","onResponse: last_name : "+response.body().getLast_name());
                //Log.e(TAG,"onResponse: date_of_birth : "+response.body().getDate_of_birth());
                Log.e("Send Client Data","onResponse: mobile_phone_number : "+response.body().getMobile_phone_number());
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Log.e("Send Client Data","onFailure: "+t.getMessage());

            }
        });

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

            Call<ClientDispensation> call = api.postDispensationData(cd);

            call.enqueue(new Callback<ClientDispensation>() {
                @Override
                public void onResponse(Call<ClientDispensation> call, Response<ClientDispensation> response) {
                    Toast.makeText(myContext, "Syncing dispensations: "+cd.getMedDrugName(cd.getMed_drug_uuid(),myContext), Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Call<ClientDispensation> call, Throwable t) {
                    Toast.makeText(myContext, t.getMessage(), Toast.LENGTH_LONG).show();
                }


            });

        }

    }

    private void syncClientData() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(PreferenceManager
                        .getDefaultSharedPreferences(myContext).getString("server",null))
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<List<Client>> call = api.getClients();

        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                for (Client client: response.body()) {

                    dbHandler.addNewClient(client.getUuid(),client.getNrc_number(), client.getArt_number(), client.getFirst_name(), client.getLast_name(), client.getDate_of_birth(), client.getSex(), client.getMobile_phone_number());
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

        Call<List<MedDrug>> call = api.getMedDrugs();

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
}
