package edu.upc.dsa.restproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import edu.upc.dsa.restproject.models.User;
import edu.upc.dsa.restproject.models.VOPlayerGameCredencials;
import edu.upc.dsa.restproject.models.idUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//UN COP T'HAS LOGGEJAT EN EL MAIN ENTESEN EL ACTIVITY_LOGIN ON POTS COMENÇAR LA PARTIDA AMB EL USUARI ESTABLERT
//I VEURE CERTES DADES, es a dir, LA FUNCIÓ DOLOGIN ESTA AL MAINACTIVITY
public class LoginActivity extends AppCompatActivity{
    TextInputEditText username;
    Api APIservice;
    Button buttonEmpezarPartida;
    Button buttonVerPartidas;
    Button shopButton;
    Button buttonAbuse;
    ProgressBar progressBar;
    String idUser;

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

        SharedPreferences sharedPreferences = getSharedPreferences("idUser", Context.MODE_PRIVATE);
        this.idUser = sharedPreferences.getString("idUser",null);
        this.getUser(this.idUser);
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
        saveVariable(this.idUser);
        Intent intentRegister = new Intent(this, ShopActivity.class);
        LoginActivity.this.startActivity(intentRegister);
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
    public void getUser(String idUser){
        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<User> call = APIservice.getUser(this.idUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                switch (response.code()){
                    case 201:
                        saveVariable(idUser);
                        Toast.makeText(LoginActivity.this,"Successful", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(LoginActivity.this,"Something went wrong", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"NETWORK FAILURE :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void saveVariable(String idUser) {
        SharedPreferences sharedPreferences = getSharedPreferences("idUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("idUser",idUser);
        //Log.i("SAVING: ",idUser);
        editor.apply();
    }
}