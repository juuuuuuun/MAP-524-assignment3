package com.example.assignment3.manager;

import android.content.Context;
import android.content.res.Resources;

import com.example.assignment3.R;
import com.example.assignment3.model.QuestionBank;

import java.util.ArrayList;
import java.util.List;

public class QuestionBankManager {

    public static int attempt = 1;

    private QuestionBank questionBank;

    public QuestionBankManager(Context context, List<Integer> orders, int position, int score) {
        if(position == -1) {
            position = 0;
        }

        Resources r = context.getResources();

        List<String> questions = new ArrayList<>();
        questions.add(r.getString(R.string.question1));
        questions.add(r.getString(R.string.question2));
        questions.add(r.getString(R.string.question3));
        questions.add(r.getString(R.string.question4));
        questions.add(r.getString(R.string.question5));
        questions.add(r.getString(R.string.question6));
        questions.add(r.getString(R.string.question7));
        questions.add(r.getString(R.string.question8));
        List<String> answers = new ArrayList<>();
        answers.add(r.getString(R.string.answer1));
        answers.add(r.getString(R.string.answer2));
        answers.add(r.getString(R.string.answer3));
        answers.add(r.getString(R.string.answer4));
        answers.add(r.getString(R.string.answer5));
        answers.add(r.getString(R.string.answer6));
        answers.add(r.getString(R.string.answer7));
        answers.add(r.getString(R.string.answer8));
        List<Integer> colors = new ArrayList<>();
        int[] colorsArr = r.getIntArray(R.array.colors);
        colors = new ArrayList<>(8);
        for (int i : colorsArr) {
            colors.add(i);
        }
        questionBank = new QuestionBank(questions, answers, colors, orders, position, score);
    }

    public QuestionBank getQuestionBank() {
        return questionBank;
    }

}
