package com.emproducciones.listapreciosalgunlugar;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class busquedaCodBarra extends Fragment {

    private BusquedaCodBarraViewModel mViewModel;
    private TextView textView;

    private Button btn;

    public static busquedaCodBarra newInstance() {
        return new busquedaCodBarra();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.busqueda_cod_barra_fragment, container, false);
        btn=view.findViewById(R.id.btnScan);
        textView = view.findViewById(R.id.txtDescripcion);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Touch",Toast.LENGTH_LONG).show();
                IntentIntegrator.forSupportFragment(busquedaCodBarra.this)
                        .setOrientationLocked(false)
                        .initiateScan();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BusquedaCodBarraViewModel.class);
        // TODO: Use the ViewModel
    }

    // Get the results:
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                textView.setText("Nulo che");
            } else {
                textView.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
