package edu.upc.dsa.restproject;
import static com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.android.material.textfield.TextInputEditText;
import edu.upc.dsa.restproject.models.idUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public Button buttonEmpezarPartida;
    public Button buttonVerPartidas;
    public Button shopButton;
    public Button buttonAbuse;
    public Button FAQButton;
    public Button LanguageButton;

    public ProgressBar progressBar;
   // public String userId;
    Api APIservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
/*
        SharedPreferences sharedPreferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
        this.userId = sharedPreferences.getString("userId", null).toString();
        this.getUserById(this.userId);*/
        this.initializeViews();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.buttonEmpezarPartida:
                i = new Intent(this, LoginActivity.class); //s'haura de canviar
                startActivity(i);
                break;
            case R.id.shopButton:
                //saveUserId(this.userId);
                i = new Intent(this, TiendaActivity.class);
                startActivity(i);
                break;
            case R.id.buttonAbuse:
                i = new Intent(this, AbuseActivity.class);
                startActivity(i);
                break;
            case R.id.FAQButton:
                i = new Intent(this, FaqActivity.class);
                startActivity(i);
                break;
            case R.id.LanguageButton:
                i = new Intent(this, LanguageActivity.class);
                startActivity(i);
                break;
        }
    }

    public void initializeViews() {
        buttonEmpezarPartida = findViewById(R.id.buttonEmpezarPartida);
        buttonVerPartidas = findViewById(R.id.buttonVerPartidas);
        shopButton = findViewById(R.id.shopButton);
        buttonAbuse = findViewById(R.id.buttonAbuse);
        progressBar = findViewById(R.id.progressBar);
        FAQButton = findViewById(R.id.FAQButton);
        LanguageButton = findViewById(R.id.LanguageButton);


        buttonEmpezarPartida.setOnClickListener(this);
        buttonVerPartidas.setOnClickListener(this);
        shopButton.setOnClickListener(this);
        buttonAbuse.setOnClickListener(this);
        FAQButton.setOnClickListener(this);
        LanguageButton.setOnClickListener(this);
    }

    public void saveVariables(idUser userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", userId.getIdUser());
        Log.i("SAVING: ", userId.getIdUser());
        editor.apply();
    }

    public void saveUserId(String userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId", userId);
        Log.i("SAVING: ", userId);
        editor.apply();
    }

    public void subscribeToFirebase() {
        FirebaseMessaging.getInstance().subscribeToTopic("admin")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed as ADMIN";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void returnFunction(View view){
        Intent intentRegister = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(intentRegister);
    }
}





/*
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import java.io.IOException;
import java.util.List;
import edu.upc.dsa.restproject.models.Game;
import edu.upc.dsa.restproject.models.VOPlayerGameCredencials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    TextInputEditText username;
    Api APIservice;
    Button buttonEmpezarPartida;
    Button buttonVerPartidas;
    Button shopButton;
    Button buttonAbuse;
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonEmpezarPartida = (Button) findViewById(R.id.buttonEmpezarPartida);
        buttonVerPartidas = (Button) findViewById(R.id.buttonVerPartidas);
        shopButton = (Button) findViewById(R.id.shopButton);
        buttonAbuse = (Button) findViewById(R.id.buttonAbuse);
        progressBar = findViewById(R.id.progressBar);
    }

    public void startGame(View view){
        progressBar.setVisibility(View.VISIBLE);
        APIservice = RetrofitClient.getInstance().getMyApi();

        VOPlayerGameCredencials credencials = new VOPlayerGameCredencials(username.getText().toString());
        Call<Game> call = APIservice.startGame(credencials);

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                switch (response.code()) {
                    case 201:
                        Toast.makeText(LoginActivity.this, "Game started", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(LoginActivity.this, "This game does not exists", Toast.LENGTH_SHORT).show();
                        break;
                    case 401:
                        Toast.makeText(LoginActivity.this, "This player is already playing", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Snackbar snakyfail = Snackbar.make(view, "NETWORK FAILURE", 3000);
                snakyfail.show();
            }
        });
    }

    public void seeGames(View view) throws IOException {
        progressBar.setVisibility(View.VISIBLE);
        username = findViewById(R.id.nombreUsuariotext);
        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<List<Game>> call = APIservice.getPartidasPlayer(username.getText().toString());

        List<Game> games = call.execute().body();
        assert games != null;
        TableLayout tableLayout = findViewById(R.id.tableLayout);

        for (Game game : games) {
            View tableRow = findViewById(R.id.tableLayout);
            TextView puntos = tableRow.findViewById(R.id.puntos);
            TextView nivelactual = tableRow.findViewById(R.id.nivelactual);

            puntos.setText(Integer.toString(game.getPuntos()));
            nivelactual.setText(Integer.toString(game.getNivelActual()));
            tableLayout.addView(tableRow);
        }
    }

    public void returnFunction(View view){
        Intent intentRegister = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(intentRegister);
    }

    public void shopFunction (View view){
        Intent intent = new Intent(LoginActivity.this, ShopActivity.class);
        LoginActivity.this.startActivity(intent);
    }


    public void btnClicked(View view) throws IOException {
        if(view== buttonVerPartidas){
            TableLayout tableLayout = findViewById(R.id.tableLayout);
            View tableRow = findViewById(R.id.tableLayout);
            TextView puntos = tableRow.findViewById(R.id.puntos);
            TextView nivelActual = tableRow.findViewById(R.id.nivelactual);
            Game game = new Game();
            puntos.setText(Integer.toString(game.getPuntos()));
            nivelActual.setText(Integer.toString(game.getNivelActual()));
            tableLayout.addView(tableRow);
        }
    }

    @Override
    public void onClick(View view) {

    }
}*/