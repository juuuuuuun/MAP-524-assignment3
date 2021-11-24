package com.example.assignment3.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionBank implements Parcelable {
    private List<Question> questions;
    private List<Integer> orders;
    private int position;
    private int score;

    public QuestionBank(List<String> questions, List<String> answers, List<Integer> colors, List<Integer> orders, int position, int score) {
        this.questions = new ArrayList<>();
        for(int i = 0; i < questions.size(); i++) {
            this.questions.add(new Question(questions.get(i), answers.get(i), colors.get(i)));
        }
        if(orders == null) {
            this.orders = new ArrayList<>();
            for(int i = 0; i < questions.size(); i++) {
                this.orders.add(i);
            }
        } else {
            this.orders = orders;
        }
        this.position = position;
        this.score = score;
    }

    public QuestionBank(Parcel in) {
        in.readTypedList(questions, Question.CREATOR);
        position = in.readInt();
        score = in.readInt();
    }

    public static final Creator<QuestionBank> CREATOR = new Creator<QuestionBank>() {
        @Override
        public QuestionBank createFromParcel(Parcel source) {
            return new QuestionBank(source);
        }

        @Override
        public QuestionBank[] newArray(int size) {
            return new QuestionBank[0];
        }
    };

    public boolean isAnswer(String question, boolean answer) {
        boolean result = false;
        for(Question q: questions){
            if(q.getQuestion().equals(question)) {
                result = q.getAnswer() == answer;
            }
        }
        if(result) {
            ++score;
        }
        return result;
    }

    public int getScore(){
        return score;
    }

    public void start() {
        Collections.shuffle(orders);
        for(int i = 1; i <= questions.size(); i++) {
            int color = questions.get(i % questions.size()).getColor();
            questions.get(i % questions.size()).setColor(questions.get((i + 1) % questions.size()).getColor());
        }
        position = 0;
        score = 0;
    }

    public Question getQuestion() {
        return questions.get(orders.get(position++));
    }

    public List<Integer> getOrders() {
        return orders;
    }

    public boolean isFinal() {
        return position == questions.size();
    }

    public int getSize() {
        return questions.size();
    }

    public int getProgress() {
        return position;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<? extends Parcelable> getQuestions() {
        return (ArrayList<? extends Parcelable>) questions;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(questions);
        dest.writeInt(position);
        dest.writeInt(score);
    }
}
