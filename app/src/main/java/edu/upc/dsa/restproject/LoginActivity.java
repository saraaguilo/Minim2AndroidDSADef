package edu.upc.dsa.restproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

//UN COP T'HAS LOGGEJAT EN EL MAIN ENTESEN EL ACTIVITY_LOGIN ON POTS COMENÇAR LA PARTIDA AMB EL USUARI ESTABLERT
//I VEURE CERTES DADES, es a dir, LA FUNCIÓ DOLOGIN ESTA AL MAINACTIVITY
public class LoginActivity extends AppCompatActivity {
    TextInputEditText username;
    Api APIservice;
    Button buttonEmpezarPartida;
    Button buttonVerPartidas;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.nombreUsuariotext);
        buttonEmpezarPartida = (Button) findViewById(R.id.buttonEmpezarPartida);
        buttonVerPartidas = (Button) findViewById(R.id.buttonVerPartidas);
    }

    public void startGame(View view){
        username = findViewById(R.id.nombreUsuariotext);
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
                    case 400:
                        Toast.makeText(LoginActivity.this, "This player is already playing", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Snackbar snakyfail = Snackbar.make(view, "NETWORK FAILURE", 3000);
                snakyfail.show();
            }
        });
    }

    public void seeGames(View view) throws IOException {
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
        Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(intentRegister);
    }

    public void btnClicked(View view) throws IOException {
        /*if(view== buttonEmpezarPartida){

        }*/
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
}