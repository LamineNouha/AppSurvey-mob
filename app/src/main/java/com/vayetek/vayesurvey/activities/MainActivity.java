package com.vayetek.vayesurvey.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vayetek.vayesurvey.R;
import com.vayetek.vayesurvey.models.Survey;
import com.vayetek.vayesurvey.services.SurveyApiRetrofitServices;
import com.vayetek.vayesurvey.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends BaseActivity {


    Toolbar toolbar = null;
    SurveyApiRetrofitServices surveyApiRetrofitServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surveyApiRetrofitServices = Utils.getSurveyApiRetrofitServicesInstance();

        //Set the fragment initially
        MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/


        //intent extras
        Bundle extras = getIntent().getExtras();
        //How to change elements in the header programatically


        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        String personalUser = sharedPreferences.getString(Config.PERSONAL_USER_SHARED_PREF, "");


        //playing the man fragment

        showLoading();

        Log.d("Tokenn", Utils.getAuthorization(this.getApplicationContext()));
        Call<ArrayList<Survey>> call = surveyApiRetrofitServices.getListSurveys(Utils.getAuthorization(this.getApplicationContext()), personalUser);
        call.enqueue(new Callback<ArrayList<Survey>>() {
            @Override
            public void onResponse(Call<ArrayList<Survey>> call, Response<ArrayList<Survey>> response) {
                hideLoading();

                ArrayList<Survey> surveys = new ArrayList<Survey>();

                surveys = response.body();
                Log.d("surveys", surveys.toString());

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("surveylist", surveys);




                               /* for (Survey r : response.body()) {
                                    textView.setText(textView.getText()+" "+r.getId()+" "+r.getName()+" \n");
                                }

*/
                if (surveys.size() != 0) {
                    //Set the fragment initially
                    MainFragment fragment = new MainFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.commit();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Survey>> call, Throwable t) {
                Log.d("error list survey", t.getMessage());
                onError(getString(R.string.info_errLoadingSurveys));
                hideLoading();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_item_logout) {
            showLoading();
            //Getting out sharedpreferences
            SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            //Getting editor
            SharedPreferences.Editor editor = preferences.edit();

            //Puting the value false for loggedin
            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

            //Putting blank value to email
            editor.putString(Config.EMAIL_SHARED_PREF, "");

            //Saving the sharedpreferences
            editor.commit();

            //Starting login activity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            //clearing the stack
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            hideLoading();
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}
