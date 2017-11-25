package com.vayetek.vayesurvey.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.gson.Gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vayetek.vayesurvey.R;
import com.vayetek.vayesurvey.activities.BaseActivity;
import com.vayetek.vayesurvey.activities.Config;
import com.vayetek.vayesurvey.activities.LoginActivity;
import com.vayetek.vayesurvey.activities.MainActivity;
import com.vayetek.vayesurvey.models.Citizen;
import com.vayetek.vayesurvey.models.FilledSurvey;
import com.vayetek.vayesurvey.models.Question;
import com.vayetek.vayesurvey.models.Survey;
import com.vayetek.vayesurvey.models.Response;
import com.vayetek.vayesurvey.services.SurveyApiRetrofitServices;
import com.vayetek.vayesurvey.utils.Utils;
import com.vayetek.vayesurvey.utils.wizardpager.model.MultipleFixedChoicePage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class AlertSubmitSurveyDFragment extends DialogFragment {

    SurveyApiRetrofitServices surveyApiRetrofitServices;
    Citizen citizen;
    HashMap<String,String> tempQuests;
    Survey survey;
    FilledSurvey filledSurvey;
    public static FragmentActivity thisfragActivity;
    //this is index for question in survey questions tab
    public static ArrayList<String> questIdArrayList;

    public static FragmentActivity getThisfragActivity() {
        return thisfragActivity;
    }

    public static void setThisfragActivity(FragmentActivity thisfragActivity) {
        AlertSubmitSurveyDFragment.thisfragActivity = thisfragActivity;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public HashMap<String, String> getTempQuests() {
        return tempQuests;
    }

    public void setTempQuests(HashMap<String, String> tempQuests) {
        this.tempQuests = tempQuests;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        questIdArrayList = new ArrayList<>();
        thisfragActivity = getActivity();
        surveyApiRetrofitServices = Utils.getSurveyApiRetrofitServicesInstance();
        return new AlertDialog.Builder(getActivity())
                // Set Dialog Icon
                .setIcon(R.drawable.goodjob)
                // Set Dialog Title
                .setTitle("Submit Survey")
                // Set Dialog Message
                .setMessage(R.string.submit_confirm_message)

                // Positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else



                        Gson json = new Gson();
                        String citizenJson = json.toJson(citizen);
                        Log.d("survey non filled: ",json.toJson(survey));
                        Log.d("citizen filled",citizenJson);
                        storeCitizen();

                        String questionsJson = json.toJson(tempQuests);
                        Log.d("questions filled",questionsJson);

                        //destroying the static tampon hashmap of questions-responses
                        MultipleFixedChoicePage.setTempQuests(new HashMap<String, String>());

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                })

                // Negative Button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        // Do something else
                    }
                }).create();
    }

    public void storeCitizen(){

        Call<ResponseBody> call1 = surveyApiRetrofitServices.storeCitizen(Utils.getAuthorization(getActivity()),citizen.getSex(), citizen.getAge(), citizen.getSocLevel(),citizen.getEducLevel(),citizen.getProfession(),citizen.getRegion(),citizen.getLocality());


        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call1, retrofit2.Response<ResponseBody> response1) {


                        if(response1.isSuccessful()) {
                            Log.d("saving citizen success ","");
                           // Intent intent = new Intent(getActivity(), MainActivity.class);
                            //startActivity(intent);

                            try {
                                JSONObject jsonObject = new JSONObject(response1.body().string());
                                String id = jsonObject.getString("_id");

                                //still non filled survey


                                Log.d("***** string citizen ",id);

                                ////this is for storing survey questions

                                Iterator iterator = tempQuests.entrySet().iterator();
                                //Log.d("temppppp ",tempQuests.toString());
                                Question[] questions =survey.getQuestions();

                                for(int i = questions.length-1; i>= 0; i--){
                                    Map.Entry mapentry = (Map.Entry) iterator.next();


                                    String t = (String) mapentry.getKey();

                                    String[] ques_interog = t.split("\\?");

                                    String tiltle= ques_interog[0];

                                    String r = (String) mapentry.getValue();
                                    String[] responsestmp;
                                    if(r.contains(",")) {
                                         responsestmp = r.split(",");
                                        for (int k = 0; k < responsestmp.length; k++) {
                                            responsestmp[k] = responsestmp[k].replaceAll("\\s+", "");

                                        }}else {
                                        responsestmp= new String[1];
                                        responsestmp[0]=r;
                                    }


                                    com.vayetek.vayesurvey.models.Response[] responses = questions[i].getResponses();


                                    boolean b=(questions[i].getContent().equals(tiltle));

                                    Log.d("s1",questions[i].getContent());
                                    Log.d("s1t",tiltle);



                                        if(b )

                                        {
                                            for(int p=0; p<responses.length; p++)
                                            {
                                                for(int o=0; o<responsestmp.length;o++)
                                                {
                                                    if(responses[p].getChoice().equals(responsestmp[o]))
                                                    { Log.d("trueeeee","vrai");
                                                        responses[p].setChecked(true);
                                                    }
                                                }

                                            }

                                            questions[i].setResponses(responses);

                                        }





                                }

                                survey.setQuestions(questions);

                                //String id = AlertSubmitSurveyDFragment.getCitizen_id();
                                //In onresume fetching value from sharedpreference
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                //Fetching the boolean value form sharedpreferences
                                String surveyId =  sharedPreferences.getString(Config.SURVEY_ID_SHARED_PREF,"");
                                String personalId = sharedPreferences.getString(Config.PERSONAL_SHARED_PREF,"");

                                filledSurvey = new FilledSurvey(survey,id,surveyId,personalId);

                                Log.d("zzz ",surveyId);
                                Log.d("zz ",personalId);
                                JsonParser parser = new JsonParser();



                                Gson json1 = new Gson();
                                String qq = json1.toJson(filledSurvey);

                                JSONObject filedS = new JSONObject(qq);
                                Log.d("*****modeliii ",filedS.toString());



                                Call<ResponseBody> call2 = surveyApiRetrofitServices.storeSurvey(Utils.getAuthorization(AlertSubmitSurveyDFragment.getThisfragActivity()), filedS);


                                call2.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call2, retrofit2.Response<ResponseBody> response2) {



                                    }

                                        ////this is for storing survey questions



                                    @Override
                                    public void onFailure(Call<ResponseBody> call2, Throwable t2) {

                                        Log.d("saving survey error: ","",t2);
                                    }
                                });



                            } catch (JSONException | IOException | NullPointerException e1) {

                                e1.printStackTrace();
                            }
                        }

            }

            @Override
            public void onFailure(Call<ResponseBody> call1, Throwable t1) {

                Log.d("saving citizen error: ","",t1);
            }
        });



    }

}