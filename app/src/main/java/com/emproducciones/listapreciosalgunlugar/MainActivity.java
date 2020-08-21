package com.emproducciones.listapreciosalgunlugar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.emproducciones.listapreciosalgunlugar.viewModel.vMProducto;


public class MainActivity extends AppCompatActivity {

    private NavController navController;
    public static int porcentaje = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Algun Lugar - Castilla");
    }
}
