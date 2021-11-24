package com.example.assignment3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.assignment3.manager.StorageManager;
import com.example.assignment3.model.Question;
import com.example.assignment3.model.QuestionBank;
import com.example.assignment3.manager.QuestionBankManager;
import com.example.assignment3.ui.main.QuestionFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private QuestionBankManager questionBankManager;
    private Question question;
    private AlertDialog.Builder alertBuilder;
    private FragmentManager fragmentManager;
    private Fragment fragment;
    private FragmentTransaction fragmentTransaction;
    private StorageManager storageManager;
    private Activity activity;
    private Button trueBtn;
    private Button falseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        init();
        prepareForQuizAndStart();
    }

    private void init() {
        trueBtn = findViewById(R.id.trueBtn);
        falseBtn = findViewById(R.id.falseBtn);
        trueBtn.setOnClickListener(this);
        falseBtn.setOnClickListener(this);
        storageManager = new StorageManager();
    }

    private void prepareForQuizAndStart() {
        Intent intent = getIntent();
        List<Integer> orders = intent.getIntegerArrayListExtra("questionOrders");
        int position = intent.getIntExtra("position", -1);
        int score = intent.getIntExtra("score", 0);

        questionBankManager = new QuestionBankManager(getApplicationContext(), orders, position, score);

        progressBar = findViewById(R.id.progressBar);
        alertBuilder = new AlertDialog.Builder(this);

        fragmentManager = getFragmentManager();

        startOver(position);
    }

    @Override
    public void onClick(View v) {
        QuestionBank questionBank = questionBankManager.getQuestionBank();
        activity = this;
        switch (v.getId()) {
            case R.id.trueBtn:
                if(questionBank.isAnswer(question.getQuestion(), true)) {
                    Toast.makeText(getApplicationContext(), R.string.correct_message, Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), R.string.incorrect_message, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.falseBtn:
                if(questionBank.isAnswer(question.getQuestion(), false)) {
                    Toast.makeText(getApplicationContext(), R.string.correct_message, Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), R.string.incorrect_message, Toast.LENGTH_SHORT).show();
                }
                break;

        }
        progressBar.setProgress(questionBank.getProgress());

        if(!questionBank.isFinal()) {
            question = questionBank.getQuestion();
            fragment = QuestionFragment.newInstance(question);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.q_fragment, fragment).commit();
        } else {
            QuestionBankManager.attempt++;
            alertBuilder.setMessage(getString(R.string.result, questionBank.getScore(), questionBank.getSize()))
                    .setCancelable(false)
                    .setNegativeButton("IGNORE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            storageManager.saveInternalStorage(activity, String.valueOf(questionBank.getScore()));
                            startOver(-1);
                        }
                    });
            AlertDialog alert = alertBuilder.create();
            alert.setTitle("Result");
            alert.show();
        }
    }

    public void startOver(int position) {
        QuestionBank questionBank = questionBankManager.getQuestionBank();
        if(position == -1) {
            questionBank.start();
        }
        progressBar.setMax(questionBank.getSize());
        progressBar.setProgress(questionBank.getProgress());
        question = questionBank.getQuestion();
        fragment = QuestionFragment.newInstance(question);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.q_fragment, fragment).commit();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.average:
                String data = storageManager.loadFromInternalStorage(this);
                int total = 0;
                char[] scoreArr = data.toCharArray();
                for(char score : scoreArr) {
                    try {
                        total += (int)(score - '0');
                    } catch (NumberFormatException e) {
                        total = 0;
                    }
                }
                int avg = total / scoreArr.length;
                alertBuilder.setMessage(getString(R.string.total_result, avg, QuestionBankManager.attempt))
                        .setCancelable(false)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                break;
            case R.id.reset:
                    storageManager.resetInternalStorage(this);
                break;
        }
        return true;
    }
}