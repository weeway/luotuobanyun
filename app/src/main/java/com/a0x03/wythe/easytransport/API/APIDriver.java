package com.a0x03.wythe.easytransport.API;

import com.a0x03.wythe.easytransport.Model.IndentList;
import com.a0x03.wythe.easytransport.Model.Status;
import com.a0x03.wythe.easytransport.Model.Succee;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by wythe on 2016/7/28.
 */
public interface APIDriver {

    @FormUrlEncoded
    @POST("driver/indent/nearby")
    Call<IndentList> getIndentList(@Field("distance") int distance,
                                   @Field("latitude") float latitude,
                                   @Field("longitude") float longitude) ;


    @FormUrlEncoded
    @POST("driver/login")
    Call<Status> loginAsDriver(@Field("phone") String phone,
                               @Field("passwd") String passwd);


    @FormUrlEncoded
    @POST("driver/register")
    Call<Status> registerAsDriver(@Field("name") String name,
                                  @Field("gender") String gender,
                                  @Field("phone") String phone,
                                  @Field("passwd") String passwd,
                                  @Field("car_type") String carType,
                                  @Field("id_drive") String idDrive,
                                  @Field("id_car") String idCar);

    @FormUrlEncoded
    @POST("driver/indent/take")
    Call<Succee> takeIndent(@Field("id_indent") String idIndent,
                            @Field("phone") String phone);


    @FormUrlEncoded
    @POST("driver/indent/deliver")
    Call<Succee> deliverIndent(@Field("id_indent") String idIndent);


    @FormUrlEncoded
    @POST("driver/indent/done")
    Call<Succee> doneIndent(@Field("id_indent") String idIndent);

}
