package com.a0x03.wythe.easytransport.API;

import com.a0x03.wythe.easytransport.Model.IndentDetail;
import com.a0x03.wythe.easytransport.Model.IndentList;
import com.a0x03.wythe.easytransport.Model.Status;
import com.a0x03.wythe.easytransport.Model.Succee;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by wythe on 2016/7/29.
 */
public interface APICustomer {

    @FormUrlEncoded
    @POST("customer/login")
    Call<Status> loginAsCustomer(@Field("phone") String phone,
                                 @Field("passwd") String passwd);


    @FormUrlEncoded
    @POST("customer/register")
    Call<Status> registerAsCustomer(@Field("nickname") String nickname,
                                    @Field("gender") String gender,
                                    @Field("phone") String phone,
                                    @Field("passwd") String passwd);


    @FormUrlEncoded
    @POST("customer/indent/publish")
    Call<Succee> publishIndent(@Field("cost") double cost,
                               @Field("phone_customer") String phoneCustomer,
                               @Field("publish_time") String publishTime,
                               @Field("publish_date") String publishDate,
                               @Field("setout_time") String setoutTime,
                               @Field("setout_date") String setoutDate,
                               @Field("car_type") String carType,
                               @Field("start_loc") String StartLoc,
                               @Field("s_lat") double sLat,
                               @Field("s_lon") double sLon,
                               @Field("end_loc") String EndLoc,
                               @Field("e_lat") double eLat,
                               @Field("e_lon") double eLon,
                               @Field("is_need_load") String yesOrNot,
                               @Field("car_len") double carLen,
                               @Field("car_weight") double catWeight,
                               @Field("goods_weight" ) double goodsWei,
                               @Field("goods_vol") double goodsVol,
                               @Field("goods_type") String goodsType);


    @FormUrlEncoded
    @POST("customer/indent/detail")
    Call<IndentDetail> getIndentDetail(@Field("id_indent") String idIndent);


    @FormUrlEncoded
    @POST("customer/indent/evaluate")
    Call<Succee> evaluateIndent(@Field("id_indent") String idIndent,
                                @Field("phone_driver") String phoneDriver,
                                @Field("eva_1") int e1,
                                @Field("eva_2") int e2,
                                @Field("eva_3") int e3,
                                @Field("comment") String comment,
                                @Field("on_time") int onTime,
                                @Field("enthusiasm") int enthusiasm,
                                @Field("reliable") int reliable,
                                @Field("fast") int fast);


    @POST("customer/indent/{status}/{page}")
    Call<IndentList> getIndentPage(@Path("status") int status,
                                   @Path("page") int page);
}
