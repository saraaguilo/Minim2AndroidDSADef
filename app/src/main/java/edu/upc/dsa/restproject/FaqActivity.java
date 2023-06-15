package edu.upc.dsa.restproject;

import static edu.upc.dsa.restproject.MainActivity.SHARED_PREFS;
import static edu.upc.dsa.restproject.MainActivity.TEXT1;
import static edu.upc.dsa.restproject.MainActivity.TEXT2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.restproject.Api;
import edu.upc.dsa.restproject.R;
import edu.upc.dsa.restproject.RetrofitClient;
import edu.upc.dsa.restproject.models.FAQ;
import edu.upc.dsa.restproject.models.Game;
import edu.upc.dsa.restproject.models.Item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqActivity extends AppCompatActivity implements RecyclerClickViewListener {
    Api APIservice;
    private RecyclerView recyclerViewFAQ;
    private RecycleViewAdapterFAQ adapterFAQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        recyclerViewFAQ = findViewById(R.id.my_new_recycler_view);
        recyclerViewFAQ.setLayoutManager(new LinearLayoutManager(this));
        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<List<FAQ>> call = APIservice.getFAQs();
        try {
            adapterFAQ = new RecycleViewAdapterFAQ(call.execute().body(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        recyclerViewFAQ.setAdapter(adapterFAQ);
    }
    @Override
    public void recyclerViewListClicked(int position) {

    }

    public void returnFunction(View view) {
        Intent intent = new Intent(FaqActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void FAQQuestions(View view) {
        Intent intent = new Intent(FaqActivity.this, FaqActivity.class);
        startActivity(intent);
    }
}
