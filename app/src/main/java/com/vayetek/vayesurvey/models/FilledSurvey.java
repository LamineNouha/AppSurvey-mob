package com.vayetek.vayesurvey.models;

/**
 * Created by nouha on 10/1/17.
 */

public class FilledSurvey extends Survey {
    private String citizen;
    private String originalSurveyId;
    private String personal;




    public String getCitizen() {
        return citizen;
    }

    public void setCitizen(String citizen) {
        this.citizen = citizen;
    }

    public FilledSurvey(Survey survey, String citizen, String originalSurveyId, String personal) {
        this.setId(survey.getId());
        this.setTitle(survey.getTitle());
        this.setQuestions(survey.getQuestions());
        this.setUser(survey.getUser());
        this.citizen = citizen;
        this.originalSurveyId = originalSurveyId;
        this.personal = personal;

    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getOriginalSurveyId() {
        return originalSurveyId;
    }

    public void setOriginalSurveyId(String originalSurveyId) {
        this.originalSurveyId = originalSurveyId;
    }
}
