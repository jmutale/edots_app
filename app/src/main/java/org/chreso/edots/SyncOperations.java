package org.chreso.edots;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
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
    private ProgressBar progressBar;

    public SyncOperations(Context context) {

        this.dbHandler = new DBHandler(context);
        this.myContext = context;
    }

    public void startDataSync(){

        try {

            syncMedDrugs();
            syncFacilityData();
            syncClientData();
            syncDrugDispensations();
            syncClientStatusData();
            syncClientFeedbackData();
            syncClientEDOTSurvey();
            syncClientTBLabData();
            syncClientDOTCardPartAData();
            syncClientDOTCardPartBData();
        }catch(Exception e){
            Toast.makeText(myContext,"Sync Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void syncClientDOTCardPartAData() {
        ArrayList<ClientDOTCardPartA> listOfClientDOTCardPartARecords = dbHandler.getListOfClientDOTCardPartARecords();
        for(ClientDOTCardPartA ces: listOfClientDOTCardPartARecords){
            ClientDOTCardPartAEvent cese = setValuesForClientDOTCardPartAEvent(ces);
            Call<ClientDOTCardPartAEvent> call = getApiInterface().postClientDOTCardPartA(cese, "Token "+getAuthToken());
            call.enqueue(new Callback<ClientDOTCardPartAEvent>() {
                @Override
                public void onResponse(Call<ClientDOTCardPartAEvent> call, Response<ClientDOTCardPartAEvent> response) {

                }

                @Override
                public void onFailure(Call<ClientDOTCardPartAEvent> call, Throwable t) {

                }
            });

        }
    }

    private ClientDOTCardPartAEvent setValuesForClientDOTCardPartAEvent(ClientDOTCardPartA ces) {
        ClientDOTCardPartAEvent cdpase = new ClientDOTCardPartAEvent();
        cdpase.setDot_card_uuid(ces.getDot_card_uuid());
        cdpase.setClient_uuid(ces.getClient_uuid());
        cdpase.setType_of_tuberculosis(ces.getType_of_tuberculosis());
        cdpase.setTreatment_outcome(ces.getTreatment_outcome());
        cdpase.setDate_of_decision(ces.getDate_of_decision());
        cdpase.setType_of_regimen(ces.getType_of_regimen());
        cdpase.setDisease_site(ces.getDisease_site());
        return cdpase;
    }

    public void syncClientDOTCardPartBData() {
        ArrayList<ClientDOTCardPartB> listOfClientDOTCardPartBRecords = dbHandler.getListOfClientDOTCardPartBRecords();
        for(ClientDOTCardPartB ces: listOfClientDOTCardPartBRecords) {
            ClientDOTCardPartBEvent cese = setValuesForClientDOTCardPartBEvent(ces);
            Call<ClientDOTCardPartBEvent> call = getApiInterface().postClientDOTCardPartB(cese, "Token "+getAuthToken());
            call.enqueue(new Callback<ClientDOTCardPartBEvent>() {
                @Override
                public void onResponse(Call<ClientDOTCardPartBEvent> call, Response<ClientDOTCardPartBEvent> response) {

                }

                @Override
                public void onFailure(Call<ClientDOTCardPartBEvent> call, Throwable t) {

                }
            });
        }

    }

    void syncClientTBLabData() {
        ArrayList<ClientTBLab> listOfClientTBLabsFromDatabase = dbHandler.getListOfClientTBLabsFromDatabase();
        for(ClientTBLab ctl : listOfClientTBLabsFromDatabase) {
            ClientTBLabEvent ctle = setClientTBLabEvent(ctl);
            Call<ClientTBLabEvent> call = getApiInterface().postClientTBLab(ctle, "Token "+getAuthToken());
            call.enqueue(new Callback<ClientTBLabEvent>() {

                @Override
                public void onResponse(Call<ClientTBLabEvent> call, Response<ClientTBLabEvent> response) {
                    //Toast.makeText(myContext, "Syncing client tb labs. "+response.errorBody(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ClientTBLabEvent> call, Throwable t) {

                }
            });
        }

    }

    private ClientTBLabEvent setClientTBLabEvent(ClientTBLab ctl) {
        ClientTBLabEvent ctle = new ClientTBLabEvent();
        ctle.setClient_tb_lab_uuid(ctl.getClient_tb_lab_uuid());
        ctle.setClient_tb_lab_date(ctl.getClient_tb_lab_date());
        ctle.setClient_uuid(ctl.getClient_uuid());
        ctle.setLevel_of_treatment_for_lab_examination(ctl.getLevel_of_treatment_for_lab_examination());
        ctle.setLab_test_type(ctl.getLab_test_type());
        ctle.setLab_result(ctl.getLab_result());
        return ctle;
    }

    void syncClientEDOTSurvey() {

        ArrayList<ClientEDOTSurvey> listOfClientEDOTSurveyRecords = dbHandler.getListOfClientSurveyRecords();
        for(ClientEDOTSurvey ces: listOfClientEDOTSurveyRecords){
            ClientEDOTSurveyEvent cese = setValuesForClientEDOTSurveyEvent(ces);
            Call<ClientEDOTSurveyEvent> call = getApiInterface().postClientEDOTSurvey(cese, "Token "+getAuthToken());
            call.enqueue(new Callback<ClientEDOTSurveyEvent>() {

                @Override
                public void onResponse(Call<ClientEDOTSurveyEvent> call, Response<ClientEDOTSurveyEvent> response) {
                    //Toast.makeText(myContext,"Syncing client survey. ",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ClientEDOTSurveyEvent> call, Throwable t) {

                }
            });
        }
    }

    private ClientEDOTSurveyEvent setValuesForClientEDOTSurveyEvent(ClientEDOTSurvey ces) {
        ClientEDOTSurveyEvent cese = new ClientEDOTSurveyEvent();
        cese.setEdot_survey_uuid(ces.getEdot_survey_uuid());
        cese.setEdot_survey_date(ces.getEdot_survey_date());
        cese.setClient_uuid(ces.getClient_uuid());
        cese.setIs_patient_satisfied_with_edot(ces.getIs_patient_satisfied_with_edot().toLowerCase());
        cese.setReasons_satisfied_or_not(ces.getReasons_satisfied_or_not());
        cese.setWould_client_like_to_continue_with_edot(ces.getWould_client_like_to_continue_with_edot().toLowerCase());
        cese.setReasons_client_will_continue_with_edot_or_not(ces.getReasons_client_will_continue_with_edot_or_not());
        return cese;
    }

    void syncClientFeedbackData() {
        ArrayList<ClientFeedback> listOfClientFeedbackEntries = null;
        try {
            listOfClientFeedbackEntries = dbHandler.getListOfClientFeedbackEntriesFromDatabase();
        }catch(Exception e){
            Toast.makeText(myContext,"Syncing Client Feedback. "+e.getMessage(),Toast.LENGTH_LONG).show();

        }
        for(ClientFeedback cf : listOfClientFeedbackEntries){
            ClientFeedbackEvent cfe = setValuesForClientFeedbackEvent(cf);
            Call<ClientFeedbackEvent> call = getApiInterface().postClientFeedback(cfe, "Token "+getAuthToken());
            call.enqueue(new Callback<ClientFeedbackEvent>() {

                @Override
                public void onResponse(Call<ClientFeedbackEvent> call, Response<ClientFeedbackEvent> response) {
                    //Toast.makeText(myContext,"Syncing client feedback. ",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ClientFeedbackEvent> call, Throwable t) {

                }
            });
        }
    }

    private ClientFeedbackEvent setValuesForClientFeedbackEvent(ClientFeedback cf) {
        ClientFeedbackEvent cfe = new ClientFeedbackEvent();
        cfe.setClient_feedback_uuid(cf.getClient_feedback_uuid());
        cfe.setClient_feedback_date(cf.getClient_feedback_date());
        cfe.setClient_uuid(cf.getClient_uuid());
        cfe.setClient_adverse_reaction(cf.getClient_adverse_reaction());
        cfe.setClient_concerns(cf.getClient_concerns());
        cfe.setAdvice_given_to_client(cf.getAdvice_given_to_client());
        return cfe;
    }

    void syncClientStatusData() {

        ArrayList<ClientStatus> listOfClientStatuses = null;
        try {
            listOfClientStatuses = dbHandler.getListOfClientStatusFromDatabase();
        }catch (Exception e){
            Toast.makeText(myContext, "SYNC CLIENT STATUS DATA: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        for(ClientStatus cs: listOfClientStatuses){
            ClientStatusEvent cse = setValuesForClientStatusEvent(cs);
            Call<ClientStatusEvent> call = getApiInterface().postClientStatus(cse, "Token "+getAuthToken());
            call.enqueue(new Callback<ClientStatusEvent>() {
                @Override
                public void onResponse(Call<ClientStatusEvent> call, Response<ClientStatusEvent> response) {
                    //Toast.makeText(myContext, "Syncing client status: ", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ClientStatusEvent> call, Throwable t) {
                    //Toast.makeText(myContext, t.getMessage(), Toast.LENGTH_LONG).show();
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
        cse.setClient_died(cs.getClient_died().equals("")?"":cs.getClient_died().toLowerCase());
        cse.setClient_died_date(cs.getClient_died_date());
        cse.setCause_of_death(cs.getCause_of_death());
        cse.setCause_of_death_other(cs.getCause_of_death_other());
        cse.setClient_refuses_to_continue_treatment(cs.getClient_refuses_to_continue_treatment().equals("")?"":cs.getClient_refuses_to_continue_treatment().toLowerCase());
        cse.setClient_is_lost_to_follow_up(cs.getClient_is_lost_to_follow_up().equals("")?"":cs.getClient_is_lost_to_follow_up().toLowerCase());
        cse.setClient_transferred_out(cs.getClient_transferred_out()==null?"":cs.getClient_transferred_out().toLowerCase());
        cse.setClient_transferred_out_date(cs.getClient_transferred_out_date()==null?null:cs.getClient_transferred_out_date());
        cse.setFacility_transferred_to(cs.getFacility_transferred_to());
        return cse;

    }

    void syncFacilityData() {
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
                if(response.body()==null){
                    Toast.makeText(myContext,"Facility or Location data has not been created on the server.", Toast.LENGTH_LONG).show();
                }else {
                    for (Location l : response.body()) {
                        dbHandler.addNewLocation(l.getUuid(), l.getName(), l.getCode(), l.getType(), l.getParent());
                        //Toast.makeText(myContext, "Syncing location: " + l.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Toast.makeText(myContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    void syncDrugDispensations() {
        ArrayList<ClientDispensation> listOfClientDispensationsFromDatabase = dbHandler.getListOfClientDispensationsFromDatabase();
        for(ClientDispensation cd : listOfClientDispensationsFromDatabase) {

            ClientDispensationEvent cde = setValuesForClientDispensationEvent(cd);
            Call<ClientDispensationEvent> call = getApiInterface().postDispensationData(cde, "Token " + getAuthToken());

            call.enqueue(new Callback<ClientDispensationEvent>() {
                @Override
                public void onResponse(Call<ClientDispensationEvent> call, Response<ClientDispensationEvent> response) {
                    //Toast.makeText(myContext, "Syncing dispensations: " + cd.getMedDrugName(response.body().getMed_drug_uuid(), myContext), Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Call<ClientDispensationEvent> call, Throwable t) {
                    Toast.makeText(myContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }


            });
            //We upload the client dispensation video here. First we check if video was already uploaded
            if (!cd.getVideoUploadStatus(cd.getDispensation_uuid(), myContext)) {
                File fileObject = null;
                InputStream inStream = null;
                try {

                    try {
                        Uri uri = Uri.parse(cd.getVideo_path());
                        inStream = myContext.getContentResolver().openInputStream(uri);
                        fileObject = new File(Environment.getExternalStorageDirectory().getPath() + "/" + cd.getMedDrugName(cd.getMed_drug_uuid(), myContext) + "_" + cd.getCleanNrc(cd.getClient_uuid(), myContext) +"_"+ System.currentTimeMillis() + ".mp4");
                        copyInputStreamToFile(inStream, fileObject);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    //fileObject = new File(Utils.getFileName(Uri.parse(filePath), myContext));

                } catch (Exception e) {
                    Toast.makeText(myContext, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), fileObject);

                MultipartBody.Part file = MultipartBody.Part.createFormData("file", fileObject.getName(), requestBody);
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), fileObject.getName());
                RequestBody uuid = RequestBody.create(MediaType.parse("text/plain"), Utils.getNewUuid());
                RequestBody dispensation_uuid = RequestBody.create(MediaType.parse("text/plain"), cd.getDispensation_uuid());


                Call<ClientVideoUploadServerResponse> call2 = getApiInterface().uploadVideo(uuid, dispensation_uuid, file, name, "Token " + getAuthToken());

                call2.enqueue(new Callback<ClientVideoUploadServerResponse>() {

                    @Override
                    public void onResponse(Call<ClientVideoUploadServerResponse> call, Response<ClientVideoUploadServerResponse> response) {
                       // Toast.makeText(myContext, "Syncing dispensation videos. ", Toast.LENGTH_SHORT).show();
                        dbHandler.updateClientVideoAfterSync(cd.getDispensation_uuid());
                    }

                    @Override
                    public void onFailure(Call<ClientVideoUploadServerResponse> call, Throwable t) {
                        Toast.makeText(myContext, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
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
        cde.setNext_clinic_appointment_date(cd.getNext_clinic_appointment_date());
        cde.setRefill_date_time(cd.getRefill_date_time());
        return cde;
    }

    void syncClientData() {

        Call<List<Client>> call = getApiInterface().getClients("Token "+getAuthToken());

        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                for (Client client: response.body()) {

                    dbHandler.addNewClient(client.getUuid(),client.getNrc_number(),client.getChreso_id(), client.getTb_id_number(),client.getAddress(), client.getType_of_client(),client.getType_of_client_other(), client.getArt_number(), client.getFirst_name(), client.getLast_name(), client.getDate_of_birth(),client.getRegistration_date(), client.getHow_many_individuals_are_in_the_same_household(), client.getAre_individuals_in_household_on_ipt(), client.getWhy_are_individuals_not_on_ipt(), client.getSex(), client.getMobile_phone_number(), client.getFacility(), client.getIs_client_on_server());
                   // Toast.makeText(myContext, "Syncing client from server: "+client.getNrc_number(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                Toast.makeText(myContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        //Upload new client records to server
        ArrayList<ClientEvent> listOfClientsFromDatabase = dbHandler.getListOfClientsFromDatabase();
        for(ClientEvent c : listOfClientsFromDatabase) {
            c.setSex(c.getSex().toLowerCase());
            c.setArt_number(c.getArt_number()==""?null:c.getArt_number());
            c.setAre_individuals_in_household_on_ipt(c.getAre_individuals_in_household_on_ipt().toLowerCase());
            Call<ClientEvent> call2 = getApiInterface().postClient(c,"Token " + getAuthToken());
            call2.enqueue(new Callback<ClientEvent>() {


                @Override
                public void onResponse(Call<ClientEvent> call, Response<ClientEvent> response) {
                    //Toast.makeText(myContext, "Syncing client to server: "+response.body().getNrc_number(), Toast.LENGTH_SHORT).show();
                    dbHandler.updateClientStatusAfterSync(c.getUuid());
                }

                @Override
                public void onFailure(Call<ClientEvent> call, Throwable t) {

                }
            });
        }
    }

    void syncMedDrugs() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(1000, TimeUnit.SECONDS)
                .connectTimeout(1000, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(PreferenceManager
                        .getDefaultSharedPreferences(myContext).getString("server",null))
                .client(okHttpClient)
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<List<MedDrug>> call = api.getMedDrugs("Token "+getAuthToken());

        call.enqueue(new Callback<List<MedDrug>>() {
            @Override
            public void onResponse(Call<List<MedDrug>> call, Response<List<MedDrug>> response) {
                if(response.body()==null){
                    Toast.makeText(myContext, "Drug dictionary on server is empty.", Toast.LENGTH_LONG).show();
                }else {
                    for (MedDrug drug : response.body()) {

                        dbHandler.addNewMedDrug(drug.getUuid(), drug.getGeneric_name(), drug.getBrand_name(), drug.getFormulation(), drug.getGeneric_ingredients(), drug.getGeneric_strength());
                        //Toast.makeText(myContext, "Syncing drug: " + drug.getGeneric_name(), Toast.LENGTH_SHORT).show();
                    }
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
    
    private ApiInterface getApiInterface(){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(900000, TimeUnit.SECONDS)
                .connectTimeout(900000, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(PreferenceManager
                        .getDefaultSharedPreferences(myContext).getString("server",null))
                .client(okHttpClient)
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        return api;
    }
    private ClientDOTCardPartBEvent setValuesForClientDOTCardPartBEvent(ClientDOTCardPartB ces) {
        ClientDOTCardPartBEvent cdpbe = new ClientDOTCardPartBEvent();
        cdpbe.setDot_card_uuid(ces.getDot_card_uuid());
        cdpbe.setClient_uuid(ces.getClient_uuid());
        cdpbe.setInitial_phase_start_date(ces.getInitial_phase_start_date());
        cdpbe.setObserver(ces.getObserver());
        cdpbe.setDot_plan(ces.getDot_plan());
        cdpbe.setStart_weight(ces.getStart_weight());
        cdpbe.setDot_plan_initiation(ces.getDot_plan_initiation());
        cdpbe.setContinuation_phase_start_date(ces.getContinuation_phase_start_date());
        cdpbe.setDot_plan_continuation_month_1(ces.getDot_plan_continuation_month_1());
        cdpbe.setDot_plan_continuation_month_2(ces.getDot_plan_continuation_month_2());
        cdpbe.setDot_plan_continuation_month_3(ces.getDot_plan_continuation_month_3());
        cdpbe.setDot_plan_continuation_month_4(ces.getDot_plan_continuation_month_4());
        return cdpbe;
    }

}

