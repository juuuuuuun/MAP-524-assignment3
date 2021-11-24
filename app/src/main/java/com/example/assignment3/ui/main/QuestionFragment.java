package com.example.assignment3.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Fragment;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.assignment3.R;
import com.example.assignment3.model.Question;

public class QuestionFragment extends Fragment {
    private static final String TEXT_KEY = "TEXT_KEY";
    private static final String COLOR_KEY = "COLOR_KEY";
    private static final String LANGUAGE_KEY = "LANGUAGE_KEY";

    private String question;
    private int colorId;

    TextView questionTextView;
    ConstraintLayout mLayout;

    public static QuestionFragment newInstance(Question question) {

        Bundle bundle = new Bundle();

        QuestionFragment fragment = new QuestionFragment();
        bundle.putString(TEXT_KEY, question.getQuestion());
        bundle.putInt(COLOR_KEY, question.getColor());

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = getArguments().getString(TEXT_KEY);
            colorId = getArguments().getInt(COLOR_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_fragment, container,false);

        mLayout = view.findViewById(R.id.qLayout);
        mLayout.setBackgroundColor(colorId);

        questionTextView = view.findViewById(R.id.q);
        questionTextView.setText(question);
//        questionTextView.setBackgroundColor(colorId);

        return view;
    }

}
