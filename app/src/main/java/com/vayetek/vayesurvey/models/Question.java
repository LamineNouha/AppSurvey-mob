package com.vayetek.vayesurvey.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;



public class Question implements Parcelable {
    private String id;
    private String content;
    private String survey;
    private Response[] responses;


    public Question(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {

            return new Question[size];
        }

    };


    public void readFromParcel(Parcel in) {
        id = in.readString();
        content = in.readString();
        responses = in.createTypedArray(Response.CREATOR);


    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(content);
        dest.writeTypedArray(responses,0);
    }





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Response[] getResponses() {
        return responses;
    }

    public void setResponses(Response[] responses) {
        this.responses = responses;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }
}
