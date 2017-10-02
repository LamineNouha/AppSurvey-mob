package com.vayetek.vayesurvey.models;

/**
 * Created by nouha on 10/1/17.
 */

public class FilledSurvey extends Survey {
    private String citizen;




    public String getCitizen() {
        return citizen;
    }

    public void setCitizen(String citizen) {
        this.citizen = citizen;
    }

    public FilledSurvey(Survey survey, String citizen) {
        this.setId(survey.getId());
        this.setTitle(survey.getTitle());
        this.setQuestions(survey.getQuestions());
        this.setUser(survey.getUser());
        this.citizen = citizen;
    }
}
