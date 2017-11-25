package com.vayetek.vayesurvey.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nouha on 8/21/17.
 */

public class Survey implements Parcelable {


    private String _id;
    private String title;
    private Question[] questions;
    private String user;

    public Survey() {
    }

    public Survey(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Survey> CREATOR = new Parcelable.Creator<Survey>() {
        public Survey createFromParcel(Parcel in) {
            return new Survey(in);
        }

        public Survey[] newArray(int size) {

            return new Survey[size];
        }

    };

    public void readFromParcel(Parcel in) {
        _id = in.readString();
        title = in.readString();
        questions = in.createTypedArray(Question.CREATOR);
        user = in.readString();


    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(title);
        dest.writeTypedArray(questions, 0);
        dest.writeString(user);
    }


    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "id='" + _id + '\'' +
                ", title='" + title + '\'' +
                ", questions=" + Arrays.toString(questions) +
                ", user='" + user + '\'' +
                '}';
    }
}
