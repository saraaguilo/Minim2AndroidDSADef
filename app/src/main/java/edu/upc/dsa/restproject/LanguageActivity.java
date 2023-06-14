package edu.upc.dsa.restproject;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
    }

    public void CatalanLanguage(View view) {
        setLocale("català");
    }

    public void SpanishLanguage(View view) {
        setLocale("español");
    }

    public void EnglishLanguage(View view) {
        setLocale("english");
    }

    private void setLocale(String language) {
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(new Locale(language));
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        restartActivity(LoginActivity.class); //s'haura de modificar i ficar l'activitat del perfil
    }

    private void restartActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void returnFunction(View view) {
        finish();
    }
}
