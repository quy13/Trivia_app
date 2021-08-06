package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.myapplication.data.AnswerListAsyncResponse;
import com.example.myapplication.data.Repository;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.model.Question;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int Score = 0;
    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;
    List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        //all the question should come in as an ArrayList type int
         questionList = new Repository().getQuestions(questionArrayList -> {
                 // putting the question into the question_textview
                binding.questionTextview.setText(questionArrayList
                        .get(currentQuestionIndex).getAnswer());
                //Log.d("Volley", "onCreate: "+ questionArrayList)

                updateCounter(questionArrayList);
                updateScore();
                }
         );



         //next button
        binding.nextButton.setOnClickListener(v -> {
            IncreaseCurrentQuestionIndex();

            updateQuestion();
        });

        //true button
        binding.trueButton.setOnClickListener(v -> {
            checkAnswer(true);
        });

        //false button
        binding.falseButton.setOnClickListener(v -> {
            checkAnswer(false);
        });

    }

    private void IncreaseCurrentQuestionIndex() {
        currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
    }

    private void checkAnswer(boolean userChoseCorrect) {

        boolean answer = questionList.get(currentQuestionIndex).isAnswerTrue();
        int snackMessageId = 0;

        if(userChoseCorrect == answer){
            snackMessageId = R.string.correct_answer;

            fadeAnimation();
            IncreaseCurrentQuestionIndex();
            updateQuestion();
            updateScore();

        } else {
            snackMessageId = R.string.incorrect_answer;
            shakeAnimation();
            updateQuestion();


        }
        Snackbar.make(binding.questionCardview,snackMessageId,Snackbar.LENGTH_SHORT).show();
    }

    private void updateScore() {
        binding.ScoreValue.setText(String.format("%d%s", (Score++) * 10, getString(R.string.points)));
    }

    private void updateCounter(ArrayList<Question> questionArrayList) {
        binding.textViewOutOf.setText(String.format(getString(R.string.text_formatted),
                currentQuestionIndex, questionArrayList.size()));
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        binding.questionTextview.setText(question);
        updateCounter((ArrayList<Question>) questionList);
    }

    //Animation
    private void shakeAnimation(){
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.shake_animation);

        binding.questionCardview.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextview.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextview.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void fadeAnimation(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(400);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        //after finish u can attach it to a view or a widget
        binding.questionCardview.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextview.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextview.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


}