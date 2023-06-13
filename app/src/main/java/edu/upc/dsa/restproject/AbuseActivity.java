package edu.upc.dsa.restproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import edu.upc.dsa.restproject.models.Abuse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbuseActivity extends AppCompatActivity {
    private String date;
    private Api APIservice;
    private TextInputEditText infoInput;
    private TextInputEditText descAbuse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abuse_activity);
        this.date=obtainDate("yyyy-MM-dd");
    }

    public void returnFunction(View view) {
        Intent intentRegister = new Intent(AbuseActivity.this, LoginActivity.class);
        startActivity(intentRegister);
    }

    public void abuseReport(View view) {
        infoInput = findViewById(R.id.nombreUsuariotext);
        descAbuse = findViewById(R.id.descriptionText);
        Abuse a = new Abuse(this.date,this.infoInput.getText().toString(),this.descAbuse.getText().toString());
        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<Void> call = APIservice.postAbuse(a);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()){
                    case 201:
                        Snackbar snaky201 = Snackbar.make(view, "Correct abuse report!", 3000);
                        snaky201.show();
                        setLabel();
                        break;
                    case 403:
                        Snackbar snaky403 = Snackbar.make(view, "Error reporting an abuse", 3000);
                        snaky403.show();
                        break;
                    case 500:
                        Snackbar snaky500 = Snackbar.make(view, "Please fill in the informers text!", 3000);
                        snaky500.show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showSnackbar(view, "NETWORK FAILURE");
            }
        });

    }
    @SuppressLint("SimpleDateFormat")
    public String obtainDate(String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }
    public void setLabel(){
        infoInput.setText("");
        descAbuse.setText("");
    }
    private void showSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    /*
    public static String obtenerFechaActual(String zonaHoraria) {
        String formato = "yyyy-MM-dd";
        return obtenerFechaConFormato(formato, zonaHoraria);
    }
     */
}