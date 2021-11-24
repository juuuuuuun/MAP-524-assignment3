package com.example.assignment3.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    private String question;
    private String answer;
    private int color;

    public Question(String question, String answer, int color) {
        this.question = question;
        this.answer = answer;
        this.color = color;
    }

    public Question(Parcel in) {
        question = in.readString();
        answer = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[0];
        }
    };

    public String getQuestion() {
        return question;
    }

    public boolean getAnswer() {
        return answer.equals("true");
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeInt(color);
    }
}
