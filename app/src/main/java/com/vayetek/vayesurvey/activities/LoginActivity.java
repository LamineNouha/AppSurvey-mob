package com.vayetek.vayesurvey.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vayetek.vayesurvey.services.SurveyApiRetrofitServices;

import com.vayetek.vayesurvey.R;
import com.vayetek.vayesurvey.utils.Utils;
import com.vayetek.vayesurvey.utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;

public class LoginActivity extends BaseActivity implements Button.OnClickListener{

    Button sigin_btn;
    EditText email_edit;
    EditText passwrod_edit;
    SurveyApiRetrofitServices surveyApiRetrofitServices;
    private boolean loggedIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //ba  = new BaseActivity();



        sigin_btn = (Button) findViewById(R.id.btn_server_login);
        email_edit = (EditText) findViewById(R.id.et_email);
        passwrod_edit = (EditText) findViewById(R.id.et_password);

        surveyApiRetrofitServices = Utils.getSurveyApiRetrofitServicesInstance();

        sigin_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //validate email and password
        if (email_edit.getText().toString().isEmpty()) {
            onError(getString(R.string.empty_email));
            return;
        }
        if (!CommonUtils.isEmailValid(email_edit.getText().toString())) {
            onError(getString(R.string.invalid_email));
            return;
        }
        if (passwrod_edit.getText().toString().isEmpty()) {
            onError(getString(R.string.empty_password));
            return;
        }
            this.signin(email_edit.getText().toString(), passwrod_edit.getText().toString());


    }


    private void signin(String login, String password) {
        showLoading();
        Call<ResponseBody> call = surveyApiRetrofitServices.signin(login, password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideLoading();

                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String token = jsonObject.getString("token");
                    JSONObject jsonObject1 = new JSONObject(jsonObject.getString("personal"));
                    String email = jsonObject1.getString("email");
                    String personalUser = jsonObject1.getString("user");


                    Utils.saveToken(LoginActivity.this, token);
                    Utils.token = "Bearer " + token;

                    SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    //Adding values to editor
                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);

                    editor.putString(Config.EMAIL_SHARED_PREF, email );

                    editor.putString(Config.PERSONAL_USER_SHARED_PREF, personalUser );

                    //Saving values to editor
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException | IOException | NullPointerException e) {
                    onError(getString(R.string.info_err));
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onError(getString(R.string.info_err));
                hideLoading();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}


