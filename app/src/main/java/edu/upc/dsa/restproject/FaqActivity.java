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
import edu.upc.dsa.restproject.models.Game;
import edu.upc.dsa.restproject.models.Item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqActivity extends AppCompatActivity implements RecyclerClickViewListener {
    Api APIservice;
    String idUser;

    private RecyclerView recyclerViewFAQ;
    private RecyclerViewAdapterItems adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        getidUser();

        recyclerViewFAQ = findViewById(R.id.my_new_recycler_view);
        recyclerViewFAQ.setLayoutManager(new LinearLayoutManager(this));
        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<List<Item>> call = APIservice.getShop();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    adapterItems = new RecyclerViewAdapterItems(response.body(), FaqActivity.this);
                    recyclerViewFAQ.setAdapter(adapterItems);
                } else {
                    Toast.makeText(FaqActivity.this, "Failed to fetch items", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(FaqActivity.this, "Network Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void returnFunction(View view) {
        Intent intent = new Intent(FaqActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void saveVariables(Item itemClicked, String idUser) {
        SharedPreferences sharedPreferences = getSharedPreferences("Item", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name", itemClicked.getName());
        editor.putString("Description", itemClicked.getDescription());
        editor.putString("Price", String.valueOf(itemClicked.getPrice()));
        editor.putString("idUser", idUser);
        editor.apply();
    }

    @Override
    public void recyclerViewListClicked(int position) {
        Item item = adapterItems.items.get(position);
        saveVariables(item, idUser);
        Intent intent = new Intent(FaqActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void getidUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("idUser", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getString("idUser", null);
    }

    public void FAQQuestions(View view) {
        Intent intent = new Intent(FaqActivity.this, FaqActivity.class);
        startActivity(intent);
    }
}
