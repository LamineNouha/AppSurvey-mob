package com.vayetek.vayesurvey.services;




import com.vayetek.vayesurvey.models.FilledSurvey;
import com.vayetek.vayesurvey.models.Question;
import com.vayetek.vayesurvey.models.Response;
import com.vayetek.vayesurvey.models.Survey;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
/**
 * Created by nouha on 8/18/17.
 */

public interface SurveyApiRetrofitServices {


        //String ENDPOINT = "http://137.74.165.25:3003/api/";
        String ENDPOINT = "http://192.168.43.104:3000/api/";

        @FormUrlEncoded
        @POST("users/auth/signinPer")
        Call<ResponseBody> signin(@Field("email") String login, @Field("password") String password);

        @GET("surveys/{userId}")
        Call<ArrayList<Survey>> getListSurveys(@Header("Authorization") String authorization, @Path("userId") String userId);

        @FormUrlEncoded
        @POST("citizens")
        Call<ResponseBody> storeCitizen(@Header("Authorization") String authorization,
                                     @Field("sex") String sex,@Field("age") int age , @Field("socLevel") String socLev,@Field("educLevel") String educLevel,
                                        @Field("profession") String profession,@Field("region") String region,@Field("locality") String locality);

        //@Headers({"Content-Type: application/json", "Cache-Control: max-age=640000"})
        @POST("filledsurveys")
        Call<ResponseBody> storeSurvey(@Header("Authorization") String authorization, @Body JSONObject body);





}
