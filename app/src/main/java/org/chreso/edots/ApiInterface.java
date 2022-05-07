package org.chreso.edots;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("/meds")
    Call<List<MedDrug>> getMedDrugs(@Header("Authorization") String authHeader);

    @GET("/clients")
    Call<List<Client>> getClients(@Header("Authorization") String authHeader);

    @POST("/dispensations/")
    Call<ClientDispensation> postDispensationData(@Body ClientDispensation cd, @Header("Authorization") String authHeader);

    @GET("/locations/")
    Call<List<Location>> getLocations(@Header("Authorization") String authHeader);

    @POST("/api-token-auth/")
    Call <AuthToken> login(@Body LoginBody loginBody);
}
