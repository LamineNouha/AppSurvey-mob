package com.vayetek.vayesurvey.activities.pages;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.vayetek.vayesurvey.activities.Config;
import com.vayetek.vayesurvey.activities.LoginActivity;
import com.vayetek.vayesurvey.models.Citizen;
import com.vayetek.vayesurvey.utils.wizardpager.model.ModelCallbacks;
import com.vayetek.vayesurvey.utils.wizardpager.model.Page;
import com.vayetek.vayesurvey.utils.wizardpager.model.ReviewItem;

import java.util.ArrayList;


/**
 * A page asking for a name and an email.
 */
public class CustomerInfoPage extends Page {
    public static final String SEX_DATA_KEY = "sex";
    public static final String AGE_DATA_KEY = "age";
    public static final String SOC_LEV_DATA_KEY = "soclevel";
    public static final String EDUC_LEV_DATA_KEY = "educlevel";
    public static final String PROFESSION_DATA_KEY = "profession";
    public static final String REGION_DATA_KEY = "region";
    public static final String LOCALITY_DATA_KEY = "locality";

    public CustomerInfoPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return CustomerInfoFragment.create(getKey());
    }

    private Citizen citizen;

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Citizen sex", mData.getString(SEX_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Citizen age", mData.getString(AGE_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Citizen social level", mData.getString(SOC_LEV_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Citizen education level", mData.getString(EDUC_LEV_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Citizen profession", mData.getString(PROFESSION_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Citizen region", mData.getString(REGION_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Citizen locality", mData.getString(LOCALITY_DATA_KEY), getKey(), -1));

        //need this to store citizen in preference then in DB
        this.citizen = new Citizen(mData.getString(SEX_DATA_KEY),Integer.parseInt(mData.getString(AGE_DATA_KEY)), mData.getString(SOC_LEV_DATA_KEY), mData.getString(EDUC_LEV_DATA_KEY), mData.getString(PROFESSION_DATA_KEY), mData.getString(REGION_DATA_KEY), mData.getString(LOCALITY_DATA_KEY));


    }

    @Override
    public boolean isCompleted() {


        return ( !TextUtils.isEmpty(mData.getString(AGE_DATA_KEY))

                && !TextUtils.isEmpty(mData.getString(PROFESSION_DATA_KEY))
                && !TextUtils.isEmpty(mData.getString(REGION_DATA_KEY))
                &&  !TextUtils.isEmpty(mData.getString(LOCALITY_DATA_KEY)));
    }
}
