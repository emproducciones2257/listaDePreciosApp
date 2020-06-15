package com.emproducciones.listapreciosalgunlugar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.emproducciones.listapreciosalgunlugar.viewModel.vMProducto;

public class MainActivity extends AppCompatActivity {
    private vMProducto vMPro;
    Button btn;
    static int porcentaje=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnScan);
        vMPro = new vMProducto();
        getSupportActionBar().setTitle("Algun Lugar - Castilla");
        obtenerPorcentaje();
    }

    private void evtnBtn() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, busquedasActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void obtenerPorcentaje(){
        vMPro.getPorcentaje().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer porcen) {
                porcentaje = porcen;
                if(porcentaje!=0){
                    btn.setEnabled(true);
                    btn.setText(getResources().getText(R.string.consultar_precio));
                    evtnBtn();
                }
            }
        });
    }
}
