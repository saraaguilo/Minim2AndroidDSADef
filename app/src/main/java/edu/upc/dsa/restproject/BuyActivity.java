package edu.upc.dsa.restproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyActivity extends AppCompatActivity {
    Api APIservice;
    String idUser;
    String idItem;
    String name;
    String price;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        this.getVariables();
        this.updateLabels();
    }
    public void getVariables() {
        SharedPreferences sharedPreferences = getSharedPreferences("idItem", Context.MODE_PRIVATE);
        this.idUser = sharedPreferences.getString("idUser", null);
        this.idItem = sharedPreferences.getString("idItem", null);
        this.name = sharedPreferences.getString("name",null);
        this.description = sharedPreferences.getString("description", null);
        this.price = sharedPreferences.getString("price", null);
    }
    public void buyItem(View view){
        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<Void> call =APIservice.buyAGadget(this.idItem,this.name,this.idUser);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()){
                    case 201:
                        Toast.makeText(BuyActivity.this,"Successful", Toast.LENGTH_SHORT).show();
                        break;
                    case 403:
                        Toast.makeText(BuyActivity.this,"Insufficient money!", Toast.LENGTH_SHORT).show();
                        break;
                    case 401:
                        Toast.makeText(BuyActivity.this,"Item does not exist", Toast.LENGTH_SHORT).show();
                        break;
                    case 409:
                        Toast.makeText(BuyActivity.this,"Item already purchased", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(BuyActivity.this,"Network Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void updateLabels(){
        String updateTitle =getString(R.string.updating_title_item);
        updateTitle=this.name+" !";
        EditText editorTitle = (EditText) findViewById (R.id.title_profile_item);
        editorTitle.setText(updateTitle);
        String updateIdentifier =getString(R.string.updating_idItem);
        updateIdentifier=this.idItem;
        EditText editorIdentifier = (EditText) findViewById (R.id.idItem);
        editorIdentifier.setText(updateIdentifier);
        String updateName =getString(R.string.updating_name);
        updateName=this.name;
        EditText editorName = (EditText) findViewById (R.id.name);
        editorName.setText(updateName);
        String updateDescription =getString(R.string.updating_description);
        updateDescription=this.description;
        EditText editorDescription = (EditText) findViewById (R.id.description);
        editorDescription.setText(updateDescription);
        String updateCost =getString(R.string.updating_price);
        updateCost=this.price;
        EditText editorCost = (EditText) findViewById (R.id.price);
        editorCost.setText(updateCost);
    }
    public void returnFunction(View view) {
        Intent intent = new Intent(BuyActivity.this, ShopActivity.class);
        startActivity(intent);
    }
}
