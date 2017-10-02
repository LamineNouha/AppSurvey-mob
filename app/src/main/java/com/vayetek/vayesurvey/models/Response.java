package com.vayetek.vayesurvey.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nouha on 8/21/17.
 */

public class Response implements Parcelable {
    private String id;
    private String choice;
    private String question;
    private boolean checked;


    public Response(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Response> CREATOR = new Parcelable.Creator<Response>() {
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        public Response[] newArray(int size) {

            return new Response[size];
        }

    };


    public void readFromParcel(Parcel in) {
        id = in.readString();
        choice = in.readString();
        checked = in.readByte() != 0;



    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(choice);
        dest.writeByte((byte) (checked ? 1 : 0));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
