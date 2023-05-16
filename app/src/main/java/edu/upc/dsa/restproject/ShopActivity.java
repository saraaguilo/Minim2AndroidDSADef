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

public class ShopActivity extends AppCompatActivity {

    Api APIservice;
    Button returnBtn;
    Button shopBtn;
    ProgressBar progressBar;
    private ArrayList<TableRow> rows;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //username = findViewById(R.id.nombreUsuariotext);
        shopBtn = (Button) findViewById(R.id.shopButton2);
        returnBtn = (Button) findViewById(R.id.returnBtn3);
        progressBar = findViewById(R.id.progressBar2);
    }

    public void getShop(View view) throws IOException {
        //progressBar.setVisibility(View.VISIBLE);
        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<List<Item>> call = APIservice.getShop();

        List<Item> items = call.execute().body();
        assert items != null;
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        rows = new ArrayList<TableRow>();

        for (Item item : items) {
            TextView name = new TextView(this);
            TextView description = new TextView(this);
            TextView price = new TextView(this);

            name.setText(item.getName());
            description.setText(item.getDescription());
            price.setText(Double.toString(item.getPrice()));
            row.addView(name);
            row.addView(description);
            row.addView(price);
        }
        tableLayout.addView(row);
        rows.add(row);
        //rows++;
    }
    public void returnFunction2(View view){
        Intent intentRegister = new Intent(ShopActivity.this, LoginActivity.class);
        ShopActivity.this.startActivity(intentRegister);
    }


}