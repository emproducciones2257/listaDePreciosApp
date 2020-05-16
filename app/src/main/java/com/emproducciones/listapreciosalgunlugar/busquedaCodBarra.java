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

import com.emproducciones.listapreciosalgunlugar.model.producto;
import com.emproducciones.listapreciosalgunlugar.viewModel.vMProducto;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class busquedaCodBarra extends Fragment {

    private BusquedaCodBarraViewModel mViewModel;
    private TextView textView;
    private Button btn;
    private String descri, precio;
    private int codMar,codPro;
    private vMProducto vMProducto;
    private producto produ;

    public busquedaCodBarra() {

        vMProducto = new vMProducto();
        produ = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

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
                if (produ!=null)
                    textView.setText(produ.getDtosExtras());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void obtenerDtosDB(String contents) {
        procesarCB(contents);
        produ = vMProducto.getProducto(codMar,codPro);

    }

    private void procesarCB(String contents) {

        codMar = Integer.parseInt(contents.substring(3,8));
        codPro = Integer.parseInt(contents.substring(8,12));
    }
}
