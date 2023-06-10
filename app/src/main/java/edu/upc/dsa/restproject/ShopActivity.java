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

public class ShopActivity extends AppCompatActivity implements RecyclerClickViewListener {

    Api APIservice;
    Button returnBtn;
    String idUser;
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
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("Name", itemClicked.getName());
        editor.putString("Description", itemClicked.getDescription());
        editor.putString("Price",String.valueOf(itemClicked.getPrice()));
        editor.putString("idUser",idUser);
        editor.apply();
    }
    @Override
    public void recyclerViewListClicked(int position) {
        Item item = adapterItems.items.get(position);
        Intent intent=new Intent(ShopActivity.this,BuyActivity.class);
        saveVariables(item, idUser);
        ShopActivity.this.startActivity(intent);
    }
    public void getidUser(){
        SharedPreferences sharedPreferences = getSharedPreferences("idUser", Context.MODE_PRIVATE);
        this.idUser = sharedPreferences.getString("idUser",null);
    }

}