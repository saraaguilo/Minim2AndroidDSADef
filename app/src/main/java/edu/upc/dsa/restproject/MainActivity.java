package edu.upc.dsa.restproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import edu.upc.dsa.restproject.models.Credentials;
import edu.upc.dsa.restproject.models.User;
import edu.upc.dsa.restproject.models.idUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    static final String SHARED_PREFS = "PROVA" ;
    Button buttonLogin;
    TextInputEditText email;
    TextInputEditText password;
    Api APIservices;
    ProgressBar progressBar;
    //DatabaseHelper db;
    public static final String TEXT1 = "User's email: ";
    public static final String TEXT2 = "User's password: ";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        email = findViewById(R.id.nombreUsuariotext);
        password = findViewById(R.id.passwordtext);


        progressBar = findViewById(R.id.progressBar);

        Toast.makeText(this,"Please register or login if you don't have an account", Toast.LENGTH_SHORT).show();
    }

     public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT1, email.getText().toString());
        editor.putString(TEXT2, password.getText().toString());
        editor.apply();
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void login(View view) {
        progressBar.setVisibility(View.VISIBLE);
        email = findViewById(R.id.nombreUsuariotext);
        password = findViewById(R.id.passwordtext);
        APIservices = RetrofitClient.getInstance().getMyApi();

        Log.i("PROBLEM", "textbox:");
        Log.i("PROBLEM", email.getText().toString());
        Log.i("PROBLEM", password.getText().toString());

        Credentials credentials = new Credentials(email.getText().toString(), password.getText().toString());
        Call<idUser> call = APIservices.login(credentials);
        Log.i("PROBLEM", "credentials:");
        Log.i("PROBLEM", credentials.getEmail());
        Log.i("PROBLEM", credentials.getPassword());
        call.enqueue(new Callback<idUser>() {
            @Override
            public void onResponse(Call<idUser> call, Response<idUser> response) {
                switch (response.code()){
                    case 201:
                        saveData();
                        Intent intentRegister = new Intent(MainActivity.this, LoginActivity.class);
                        idUser idUser = response.body();
                        assert idUser != null;
                        saveVariable(idUser);
                        Toast.makeText(MainActivity.this,"Login OK", Toast.LENGTH_SHORT).show();
                        MainActivity.this.startActivity(intentRegister);
                        break;
                    case 404:
                        Toast.makeText(MainActivity.this,"Wrong Credentials Email!", Toast.LENGTH_SHORT).show();
                        break;
                    case 401:
                        Toast.makeText(MainActivity.this,"Wrong Credentials Password!", Toast.LENGTH_SHORT).show();
                        break;
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onFailure(Call<idUser> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Snackbar snakyfail = Snackbar.make(view, "NETWORK FAILURE", 3000);
                snakyfail.show();
            }
        });
    }
    public void saveVariable(idUser idUser) {
        SharedPreferences sharedPreferences = getSharedPreferences("idUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("idUser",idUser.getIdUser());
        //Log.i("SAVING: ",idUser.getIdUser());
        editor.commit();
    }

    public void btnRegisterClicked(View view){
        Intent intentRegister = new Intent(MainActivity.this, RegisterActivity.class);
        MainActivity.this.startActivity(intentRegister);

    }
}