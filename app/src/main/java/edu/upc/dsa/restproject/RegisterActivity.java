package edu.upc.dsa.restproject;

//import static edu.upc.dsa.restclientandroidproject.RegisterActivity.RegistroActivity.SHARED_PREFS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import edu.upc.dsa.restproject.models.User;
import edu.upc.dsa.restproject.models.UserRegister;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final String SHARED_PREFS = "PROVA";
    Button buttonRegister;
    TextInputEditText name;
    TextInputEditText surname;
    TextInputEditText email;
    TextInputEditText password;
    Api APIservice;
    ProgressBar progressBar;

    public static final String TEXT1 = "User's name: ";
    public static final String TEXT2 = "User's surname: ";
    public static final String TEXT3 = "User's email: ";
    public static final String TEXT4 = "User's password: ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        name = findViewById(R.id.nombreUsuariotext);
        surname = findViewById(R.id.surnameUsuariotext);
        email = findViewById(R.id.emailtext);
        password = findViewById(R.id.password1);
        buttonRegister = findViewById(R.id.buttonUsuarioRegistrado);
        progressBar = findViewById(R.id.progressBar);
        Toast.makeText(this,"Please Register", Toast.LENGTH_SHORT).show();
    }


    public void saveVariable(UserRegister user) {
        SharedPreferences sharedPreferences= getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("User email", user.getEmail() + "User password: " + user.getPassword());
        Log.i("Saved ", "User email: " + user.getEmail() + " User password: " + user.getPassword());
        editor.commit();
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT1, name.getText().toString());
        editor.putString(TEXT2, surname.getText().toString());
        editor.putString(TEXT3, email.getText().toString());
        editor.putString(TEXT4, password.getText().toString());
        editor.apply();
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void btnClicked(View view) throws IOException {
        /**if(view== buttonLogin){
            Intent intentRegister = new Intent(RegisterActivity.this, LoginActivity.class);
            RegisterActivity.this.startActivity(intentRegister);
        }**/
        if(view== buttonRegister){
            Intent intentRegister = new Intent(RegisterActivity.this, MainActivity.class);
            RegisterActivity.this.startActivity(intentRegister);
        }
    }
    public void returnFunction(View view){
        Intent intentRegister = new Intent(RegisterActivity.this, MainActivity.class);
        RegisterActivity.this.startActivity(intentRegister);
    }

    public void register(View view) throws IOException {
        progressBar.setVisibility(View.VISIBLE);
        name = findViewById(R.id.nombreUsuariotext);
        surname = findViewById(R.id.surnameUsuariotext);
        email = findViewById(R.id.emailtext);
        password = findViewById(R.id.password1);
        APIservice = RetrofitClient.getInstance().getMyApi();
        Log.i("PROBLEM", name.getText().toString());
        Log.i("PROBLEM", surname.getText().toString());
        Log.i("PROBLEM", email.getText().toString());
        Log.i("PROBLEM", password.getText().toString());

        UserRegister user = new UserRegister(name.getText().toString(),surname.getText().toString(),email.getText().toString(),password.getText().toString());
        Call<UserRegister> call = APIservice.register(user);
        call.enqueue(new Callback<UserRegister>() {
            @Override
            public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.i("PROBLEM","OnResponse");
                switch (response.code()){
                    case 201:
                        saveData();
                        Intent intentRegister = new Intent(RegisterActivity.this, MainActivity.class);
                        UserRegister user = response.body();
                        //assert user != null;
                        saveVariable(user);
                        Toast.makeText(RegisterActivity.this,"Register OK", Toast.LENGTH_SHORT).show();
                        RegisterActivity.this.startActivity(intentRegister);
                        break;
                    case 404:
                        Toast.makeText(RegisterActivity.this,"Wrong credentials!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            @Override
            public void onFailure(Call<UserRegister> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.i("PROBLEM","OnFailure",t);
                Snackbar snakyfail = Snackbar.make(view, "NETWORK FAILURE", 3000);
                snakyfail.show();
            }
        });
    }
}
