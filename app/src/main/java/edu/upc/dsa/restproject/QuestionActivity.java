package edu.upc.dsa.restproject;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.upc.dsa.restproject.models.Question;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity{
    Api APIservice;
    String idUser;
    TextInputEditText message,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);
        getUserIdFromPreviousActivity();
    }
    public void sendMessage(View view) {
        message = findViewById(R.id.messageInput);
        title = findViewById(R.id.titleInput);
        if(!message.getText().toString().equals("")&&!title.getText().toString().equals("")){
            String currentTime = getDate();
            Question q = new Question(currentTime, title.getText().toString(),message.getText().toString(),this.idUser);
            APIservice = RetrofitClient.getInstance().getMyApi();
            Call<Void> call = APIservice.addQuestion(q);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    switch (response.code()){
                        case 201:
                            Snackbar snaky201 = Snackbar.make(view, "Question send", 3000);
                            snaky201.show();
                    }
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Snackbar snakyfail = Snackbar.make(view, "NETWORK FAILURE", 3000);
                    snakyfail.show();
                }
            });
        }
    }
    public String getDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = c.getTime();
        String currentDate = dateFormat.format(date);
        return currentDate;
    }
    public void getUserIdFromPreviousActivity(){
        SharedPreferences sharedPreferences = getSharedPreferences("userEditInfo", Context.MODE_PRIVATE);
        this.idUser = sharedPreferences.getString("userId", null).toString();
    }

    public void Return(View view) {
        Intent intentDashBoard = new Intent(QuestionActivity.this, FaqActivity.class);
        QuestionActivity.this.startActivity(intentDashBoard);
    }



}
