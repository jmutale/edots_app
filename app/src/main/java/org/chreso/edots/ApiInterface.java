package org.chreso.edots;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @GET("/meds")
    Call<List<MedDrug>> getMedDrugs(@Header("Authorization") String authHeader);

    @GET("/clients")
    Call<List<Client>> getClients(@Header("Authorization") String authHeader);

    @POST("/dispensations/")
    Call<ClientDispensationEvent> postDispensationData(@Body ClientDispensationEvent cd, @Header("Authorization") String authHeader);

    @POST("/client-status/")
    Call<ClientStatusEvent> postClientStatus(@Body ClientStatusEvent cse, @Header("Authorization") String authHeader);

    @POST("/client-feedback/")
    Call<ClientFeedbackEvent> postClientFeedback(@Body ClientFeedbackEvent cfe, @Header("Authorization") String authHeader);

    @POST("/edot-survey/")
    Call<ClientEDOTSurveyEvent> postClientEDOTSurvey(@Body ClientEDOTSurveyEvent cese, @Header("Authorization") String authHeader );

    @GET("/locations")
    Call<List<Location>> getLocations(@Header("Authorization") String authHeader);

    @POST("/api-token-auth/")
    Call <AuthToken> login(@Body LoginBody loginBody);

    @Multipart
    @POST("/dispensation-videos/")
    Call <ClientVideoUploadServerResponse> uploadVideo(@Part("uuid") RequestBody uuid, @Part("dispensation_uuid") RequestBody dispensation_uuid, @Part MultipartBody.Part file, @Part("file") RequestBody name, @Header("Authorization") String authHeader);
}
