package org.chreso.edots;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SyncOperations {
    private DBHandler dbHandler;
    private Context myContext;
    public static final int DEFAULT_BUFFER_SIZE = 8192;

    public SyncOperations(Context context) {

        this.dbHandler = new DBHandler(context);
        this.myContext = context;
    }

    public void startDataSync(){
            syncMedDrugs();
            syncFacilityData();
            syncClientData();
            syncDrugDispensations();
            syncClientStatusData();
    }

    private void syncClientStatusData() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(PreferenceManager
                        .getDefaultSharedPreferences(myContext).getString("server",null))
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        ArrayList<ClientStatus> listOfClientStatuses = dbHandler.getListOfClientStatusFromDatabase();
        for(ClientStatus cs: listOfClientStatuses){
            ClientStatusEvent cse = setValuesForClientStatusEvent(cs);
            Call<ClientStatusEvent> call = api.postClientStatus(cse, "Token "+getAuthToken());
            call.enqueue(new Callback<ClientStatusEvent>() {
                @Override
                public void onResponse(Call<ClientStatusEvent> call, Response<ClientStatusEvent> response) {

                }

                @Override
                public void onFailure(Call<ClientStatusEvent> call, Throwable t) {

                }
            });
        }
    }

    private ClientStatusEvent setValuesForClientStatusEvent(ClientStatus cs) {
        ClientStatusEvent cse = new ClientStatusEvent();
        cse.setClient_status_uuid(cs.getClient_status_uuid());
        cse.setReporting_facility(cs.getReporting_facility());
        cse.setClient_uuid(cs.getClient_uuid());
        cse.setStatus_date(cs.getStatus_date());
        cse.setClient_died(cs.getClient_died());
        cse.setClient_died_date(cs.getClient_died_date());
        cse.setCause_of_death(cs.getCause_of_death());
        cse.setClient_transferred_out(cs.getClient_transferred_out());
        cse.setClient_transferred_out_date(cs.getClient_transferred_out_date());
        cse.setFacility_transferred_to(cs.getFacility_transferred_to());
        return cse;

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
            Call<ClientDispensationEvent> call = api.postDispensationData(cde, "Token "+getAuthToken());

            call.enqueue(new Callback<ClientDispensationEvent>() {
                @Override
                public void onResponse(Call<ClientDispensationEvent> call, Response<ClientDispensationEvent> response) {
                    Toast.makeText(myContext, "Syncing dispensations: "+cd.getMedDrugName(cd.getMed_drug_uuid(),myContext), Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Call<ClientDispensationEvent> call, Throwable t) {
                    Toast.makeText(myContext, t.getMessage(), Toast.LENGTH_LONG).show();
                }


            });
            //We upload the client dispensation video here.
            File fileObject = null;
            InputStream inStream=null;
            try {

                try {
                    Uri uri = Uri.parse(cd.getVideo_path());
                    inStream = myContext.getContentResolver().openInputStream(uri);
                    fileObject = new File(Environment.getExternalStorageDirectory().getPath()+"/"+cd.getMedDrugName(cd.getMed_drug_uuid(), myContext)+"_"+cd.getClient_uuid()+".mp4");
                    copyInputStreamToFile(inStream,fileObject);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //fileObject = new File(Utils.getFileName(Uri.parse(filePath), myContext));

            }catch (Exception e)
            {
                Toast.makeText(myContext, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), fileObject);

            MultipartBody.Part file = MultipartBody.Part.createFormData("file", fileObject.getName(), requestBody);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), fileObject.getName());


            Call <ClientVideoUploadServerResponse> call2 = api.uploadVideo(file, name,"Token "+getAuthToken());

            call2.enqueue(new Callback<ClientVideoUploadServerResponse>() {

                @Override
                public void onResponse(Call<ClientVideoUploadServerResponse> call, Response<ClientVideoUploadServerResponse> response) {
                    Toast.makeText(myContext, "Syncing dispensation videos: ", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Call<ClientVideoUploadServerResponse> call, Throwable t) {
                    Toast.makeText(myContext, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    private ClientDispensationEvent setValuesForClientDispensationEvent(ClientDispensation cd) {
        ClientDispensationEvent cde = new ClientDispensationEvent();
        cde.setDispensation_uuid(cd.getDispensation_uuid());
        cde.setClient_uuid(cd.getClient_uuid());
        cde.setDispensation_date(cd.getDispensation_date());
        cde.setMed_drug_uuid(cd.getMed_drug_uuid());
        cde.setRefill_date(cd.getRefill_date());
        cde.setDose(cd.getDose());
        cde.setItems_per_dose(cd.getItems_per_dose());
        //cde.setFile(cd.getVideo_path());
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

    private static void copyInputStreamToFile(InputStream inputStream, File file)
            throws IOException {

        // append = false
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

    }
}
