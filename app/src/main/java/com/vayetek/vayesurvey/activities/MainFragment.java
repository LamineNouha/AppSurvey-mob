package com.vayetek.vayesurvey.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.vayetek.vayesurvey.R;
import com.vayetek.vayesurvey.models.Survey;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    public ArrayList<Survey> getSurveys() {
        return surveys;
    }

    public void setSurveys(ArrayList<Survey> surveys) {
        this.surveys = surveys;
    }

    public static ArrayList<Survey> surveys;

    private Context mFragmentContext;
    Intent intent;


    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        LinearLayout myLayout = (LinearLayout)rootView.findViewById(R.id.mainLayout);

        myLayout.setBackgroundColor(getContext().getResources().getColor(R.color.bg_color));
        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        buttonParams.addRule(RelativeLayout.CENTER_VERTICAL);
        buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);



        mFragmentContext=MainFragment.this.getContext();
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            surveys = bundle.getParcelableArrayList("surveylist");



            for (int i=0; i<surveys.size();i++){
                Log.d("Creating Button N° ",String.valueOf(i));
                final Button myButton = new Button(mFragmentContext);
                myButton.setText( surveys.get(i).getTitle());
                myButton.setTextColor(getContext().getResources().getColor(R.color.white));
                myButton.setBackgroundColor(getContext().getResources().getColor(R.color.cyan_dark));
                myButton.setId(i);
                myButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                buttonParams.topMargin=buttonParams.topMargin+40;




                myButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        intent = new Intent(getActivity(), SurveyActivity.class);
                        intent.putExtra("survey", surveys.get(myButton.getId()));

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();



                        editor.putString(Config.SURVEY_ID_SHARED_PREF, surveys.get(myButton.getId()).getQuestions()[0].getSurvey() );


                        //Saving values to editor
                        editor.commit();

                        Log.d("fragggggggids",surveys.get(myButton.getId()).getQuestions()[0].getSurvey());
                        startActivity(intent);


                    }

                });
                myLayout.addView(myButton,buttonParams);
            }
        }









        return rootView;
    }


}
