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

public class ShopActivity extends AppCompatActivity implements RecyclerClickViewListener {

    Api APIservice;
    String idUser;
    String idItem;
    String name;
    String description;
    String price;
    private RecyclerView recyclerViewItems;
    private RecyclerViewAdapterItems adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        this.getidUser();

        recyclerViewItems=(RecyclerView)findViewById(R.id.recyclerItem);
        Log.d("DDDD", ""+recyclerViewItems);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<List<Item>> call = APIservice.getShop();
        try {
            adapterItems = new RecyclerViewAdapterItems(call.execute().body(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        recyclerViewItems.setAdapter(adapterItems);

    }
    public void returnFunction(View view) {
        Intent intent=new Intent(ShopActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    public void saveVariables(Item itemClicked, String idUser) {
        SharedPreferences sharedPreferences= getSharedPreferences("Item", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", itemClicked.getName());
        editor.putString("description", itemClicked.getDescription());
        editor.putString("price",String.valueOf(itemClicked.getPrice()));
        editor.putString("idUser",idUser);
        editor.putString("idItem",itemClicked.getIdItem());
        editor.apply();
    }
    public void getVariables(){
        SharedPreferences sharedPreferences = getSharedPreferences("Item", Context.MODE_PRIVATE);
        this.idUser = sharedPreferences.getString("idUser", null);
        this.idItem = sharedPreferences.getString("idItem", null);
        this.name = sharedPreferences.getString("name",null);
        this.description = sharedPreferences.getString("description", null);
        this.price = sharedPreferences.getString("price", null);
    }
    @Override
    public void recyclerViewListClicked(int position) {
        Item item = adapterItems.items.get(position);
        saveVariables(item, this.idUser);
        this.getVariables();
        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<Void> call = APIservice.buyItems(this.idItem,this.name,this.idUser);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()){
                    case 201:
                        Toast.makeText(ShopActivity.this,"Successful", Toast.LENGTH_SHORT).show();
                        break;
                    case 403:
                        Toast.makeText(ShopActivity.this,"Insufficient money!", Toast.LENGTH_SHORT).show();
                        break;
                    case 401:
                        Toast.makeText(ShopActivity.this,"Item does not exist", Toast.LENGTH_SHORT).show();
                        break;
                    case 409:
                        Toast.makeText(ShopActivity.this,"Item already purchased", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ShopActivity.this,"Network Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getidUser(){
        SharedPreferences sharedPreferences = getSharedPreferences("idUser", Context.MODE_PRIVATE);
        this.idUser = sharedPreferences.getString("idUser",null);
    }

}