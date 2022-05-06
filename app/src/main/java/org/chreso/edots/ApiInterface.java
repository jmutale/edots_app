package org.chreso.edots;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/clients/")
    Call<Client> getClientData(@Field("first_name") String first_name, @Field("last_name") String last_name, @Field("date_of_birth") LocalDate date_of_birth, @Field("sex") String sex, @Field("mobile_phone_number") String mobile_phone_number);


    @GET("/meds")
    Call<List<MedDrug>> getMedDrugs();

    @GET("/clients")
    Call<List<Client>> getClients();

    @POST("/dispensations/")
    Call<ClientDispensation> postDispensationData(@Body ClientDispensation cd);
}
