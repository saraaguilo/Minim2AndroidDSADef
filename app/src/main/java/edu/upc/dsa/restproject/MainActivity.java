package edu.upc.dsa.restproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import java.io.IOException;
import edu.upc.dsa.restproject.models.Credentials;
import edu.upc.dsa.restproject.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String SHARED_PREFS = "PROVA" ;
    Button buttonLogin;
    Button buttonRegistro;
    TextInputEditText email;
    TextInputEditText password;
    Api APIservices;
    public static final String TEXT1 = "User's email: ";
    public static final String TEXT2 = "User's password: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.nombreUsuariotext);
        password = findViewById(R.id.passwordtext);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonRegistro = (Button) findViewById(R.id.buttonRegistro);

        Toast.makeText(this,"Please Register.", Toast.LENGTH_SHORT).show();
    }

     public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT1, email.getText().toString());
        editor.putString(TEXT2, password.getText().toString());
        editor.apply();
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void login(View view) throws IOException {
        email = findViewById(R.id.nombreUsuariotext);
        password = findViewById(R.id.passwordtext);
        APIservices = RetrofitClient.getInstance().getMyApi();

        Log.i("PROBLEM", "textbox:");
        Log.i("PROBLEM", email.getText().toString());
        Log.i("PROBLEM", password.getText().toString());

        Credentials credentials = new Credentials(email.getText().toString(), password.getText().toString());
        Call<User> call = APIservices.login(credentials);
        Log.i("PROBLEM", "credentials:");
        Log.i("PROBLEM", credentials.getEmail());
        Log.i("PROBLEM", credentials.getPassword());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                switch (response.code()){
                    case 201:
                        saveData();
                        Intent intentRegister = new Intent(MainActivity.this, RecyclerViewAdapterUsers.class);
                        User user =response.body();
                        //assert usuarioId != null;
                        saveVariable(user);
                        MainActivity.this.startActivity(intentRegister);
                        Toast.makeText(MainActivity.this,"Login OK", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(MainActivity.this,"Wrong Credentials!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Snackbar snakyfail = Snackbar.make(view, "NETWORK FAILURE", 3000);
                snakyfail.show();
            }
        });
    }

    //EN REALITAT HAURIA DE MIRAR D'AGAFAR EL ID S HA DE MIRAR DE CANVIAR MES ENDAVANT:
    public void saveVariable(User user) {
        SharedPreferences sharedPreferences= getSharedPreferences("userId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("Id of the user ", user.getEmail());
        Log.i("Saved ", user.getEmail());
        editor.commit();
    }

    public void btnClicked(View view) throws IOException {
        if(view== buttonLogin){
            Intent intentRegister = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(intentRegister);
        }
        if(view== buttonRegistro){
            Intent intentRegister = new Intent(MainActivity.this, RegisterActivity.class);
            MainActivity.this.startActivity(intentRegister);
        }
    }
}