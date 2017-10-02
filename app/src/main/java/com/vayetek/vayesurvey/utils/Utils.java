package com.vayetek.vayesurvey.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.vayetek.ecosapp.models.NoteModelRequest;
//import com.vayetek.ecosapp.models.NoteModelRequestArray;
import com.vayetek.vayesurvey.services.SurveyApiRetrofitServices;
//import com.vayetek.ecosapp.services.UniversityRetrofitServices;

import org.json.JSONException;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {

    private static final int TIME_OUT = 10000;
    public static String token;

    public static void saveToken(Context context, String token) {
        context.getSharedPreferences("survey_prefs", Context.MODE_PRIVATE).edit().putString("token", token).apply();
    }

    public static boolean isUserAuthenticated(Context context) {
        return context.getSharedPreferences("survey_prefs", Context.MODE_PRIVATE).getString("token", null) != null;
    }

    private static String retreiveToken(Context context) {
        return context.getSharedPreferences("survey_prefs", Context.MODE_PRIVATE).getString("token", null);
    }

    public static String getAuthorization(Context context) {
        return "Bearer " + retreiveToken(context);
    }


    //Ecos Api Retrofit Instance
    private static SurveyApiRetrofitServices SurveyApiRetrofitServicesInstance;

    public static SurveyApiRetrofitServices getSurveyApiRetrofitServicesInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (SurveyApiRetrofitServicesInstance == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SurveyApiRetrofitServices.ENDPOINT)
                    .client(new OkHttpClient().newBuilder().connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS).addInterceptor(interceptor).build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            SurveyApiRetrofitServicesInstance = retrofit.create(SurveyApiRetrofitServices.class);
        }
        return SurveyApiRetrofitServicesInstance;
    }

}
