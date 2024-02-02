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

    @POST("/edot-card-part-a/")
    Call<ClientDOTCardPartAEvent> postClientDOTCardPartA(@Body ClientDOTCardPartAEvent cdcpa, @Header("Authorization") String authHeader);

    @POST("/edot-card-part-b/")
    Call<ClientDOTCardPartBEvent> postClientDOTCardPartB(@Body ClientDOTCardPartBEvent cdcpb, @Header("Authorization") String authHeader);

    @POST("/hiv-counselling-and-testing/")
    Call<ClientHIVCounsellingAndTestingEvent> postClientHIVCounsellingAndTesting(@Body ClientHIVCounsellingAndTestingEvent chctc, @Header("Authorization") String authHeader);

    @POST("/hiv-care/")
    Call<ClientHIVCareEvent> postClientHIVCare(@Body ClientHIVCareEvent chce, @Header("Authorization") String authHeader);

    @POST("/clients/")
    Call<ClientEvent> postClient(@Body ClientEvent client, @Header("Authorization") String authHeader);

    @POST("/dispensations/")
    Call<ClientDispensationEvent> postDispensationData(@Body ClientDispensationEvent cd, @Header("Authorization") String authHeader);

    @POST("/client-status/")
    Call<ClientStatusEvent> postClientStatus(@Body ClientStatusEvent cse, @Header("Authorization") String authHeader);

    @POST("/client-labs/")
    Call<ClientTBLabEvent> postClientTBLab(@Body ClientTBLabEvent ctl, @Header("Authorization") String authHeader);

    @POST("/client-feedback/")
    Call<ClientFeedbackEvent> postClientFeedback(@Body ClientFeedbackEvent cfe, @Header("Authorization") String authHeader);

    @POST("/edot-survey/")
    Call<ClientEDOTSurveyEvent> postClientEDOTSurvey(@Body ClientEDOTSurveyEvent cese, @Header("Authorization") String authHeader );

    @GET("/locations")
    Call<List<Location>> getLocations(@Header("Authorization") String authHeader);

    @GET("/chw-users/")
    Call<List<ChwUser>> getCheUserDetails(@Header("Authorization") String authHeader);

    @POST("/api-token-auth/")
    Call <AuthToken> login(@Body LoginBody loginBody);

    @POST("/patient-dispensation-status/")
    Call<PatientDispensationStatusEvent> postPatientDispensationStatus(@Body PatientDispensationStatusEvent pdse, @Header("Authorization") String authHeader);

    @GET("/logged-in-user-details/")
    Call <LoggedInUser> getLoggedInUser(@Header("Authorization") String authHeader);

    @Multipart
    @POST("/dispensation-videos/")
    Call <ClientVideoUploadServerResponse> uploadVideo(@Part("uuid") RequestBody uuid, @Part("dispensation_uuid") RequestBody dispensation_uuid, @Part MultipartBody.Part file, @Part("file") RequestBody name, @Header("Authorization") String authHeader);
}
