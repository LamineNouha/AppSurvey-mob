package com.vayetek.vayesurvey.activities;

import com.vayetek.vayesurvey.models.Citizen;

/**
 * Created by nouha on 8/21/17.
 */

public class Config {

    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "mysurveyapp";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "email";

    //This would be used to store the user  of the current logged in personal
    public static final String PERSONAL_USER_SHARED_PREF = "personalUser";

    //This would be used to store the id of the current logged in personal
    public static final String PERSONAL_SHARED_PREF = "personal";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    public static final String CITIZEN_SHARED_PREF ="";
    public static final String SURVEY_ID_SHARED_PREF ="";

    public static final int D_PREF=0;


}
