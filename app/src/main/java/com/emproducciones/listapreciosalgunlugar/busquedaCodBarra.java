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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class busquedaCodBarra extends Fragment {

    private BusquedaCodBarraViewModel mViewModel;
    private TextView textView;
    private Button btn;
    private FirebaseFirestore db;
    private String descri, precio;

    public static busquedaCodBarra newInstance() {
        return new busquedaCodBarra();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        View view = inflater.inflate(R.layout.busqueda_cod_barra_fragment, container, false);

        initView(view);
        btn.setOnClickListener(view1 ->
                IntentIntegrator.forSupportFragment(busquedaCodBarra.this)
                .setOrientationLocked(false)
                .initiateScan());

        return view;
    }

    private void initView(View view) {
        btn=view.findViewById(R.id.btnScan);
        textView = view.findViewById(R.id.txtDescripcion);
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
                obtenerDtosDB(result.getContents());
                textView.setText(descri);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void obtenerDtosDB(String contents) {
    }
}
