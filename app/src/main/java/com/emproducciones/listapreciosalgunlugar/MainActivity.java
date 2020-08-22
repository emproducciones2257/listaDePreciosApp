package com.emproducciones.listapreciosalgunlugar;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Algun Lugar - Castilla");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
