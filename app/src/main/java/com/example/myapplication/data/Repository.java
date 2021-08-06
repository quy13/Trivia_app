package com.example.myapplication.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.myapplication.controller.AppController;
import com.example.myapplication.model.Question;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    // making an ArrayList that have Question
    ArrayList<Question> questionArrayList = new ArrayList<>();

    String URL = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    //moved from MainActivity
    //return an array of list that contains Questions
    public List<Question> getQuestions(final AnswerListAsyncResponse callBack) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request
                .Method.GET, URL, null, response -> {

            for (int i = 0; i < response.length(); i++) {

                try {

                    Question question = new Question(response.getJSONArray(i).getString(0),
                            response.getJSONArray(i).getBoolean(1));

                    //different way
//                    question.setAnswer(response.getJSONArray(i).getString(0));
//                    question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));
                    //Also can do this
                    //question.setAnswer(response.getJSONArray(i).get(0).toString());

                    //Add question to ArrayList/list
                    questionArrayList.add(question);
                    //try to access Array List
                    Log.d("TAG", "getQuestions: " + questionArrayList);
                    //Log.d("JSON", "onCreate: "+ response.getJSONArray(i).getString(0));//Accessing the Array get the data
                    //Log.d("JSON", "onCreate: "+ response.getJSONArray(i).getBoolean(1));//Accessing the Array
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            //when done then pass processFinished that have questionArrayList that have question to main
            if (null != callBack) callBack.processFinished(questionArrayList);


        }, error -> {
            Log.d("JSON", "onCreate: Request failed");
        });

        //passing jsonArrayRequest onto RequestQueue
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionArrayList;
    }
}
