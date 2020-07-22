package com.emproducciones.listapreciosalgunlugar;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.*;
import android.widget.*;
import com.emproducciones.listapreciosalgunlugar.model.*;
import com.emproducciones.listapreciosalgunlugar.viewModel.vMProducto;
import com.google.zxing.integration.android.*;

import java.util.ArrayList;

import static android.content.DialogInterface.*;

public class busquedaCodBarra extends Fragment {

    private BusquedaCodBarraViewModel mViewModel;
    private TextView txtDescripcion, txtPrecio;
    private Button btn,btnPerfumeria;
    private String codMar,codPro,categoria;
    private vMProducto vMProducto;
    private proPreCloud proPreCloud;
    private ArrayList<proPreCloud> listaProductos;
    private RecyclerView recycler_lista_escaneado;
    private RecyclerView.LayoutManager layoutManager;
    private BusquedaCodBarraViewModel viewModel;

    public busquedaCodBarra() {
        vMProducto = new vMProducto();
        proPreCloud = new proPreCloud();
        listaProductos = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.busqueda_cod_barra_fragment, container, false);

        initView(view);

        btn.setOnClickListener(view1 -> {
            categoria="LIBRERIA";
        IntentIntegrator.forSupportFragment(busquedaCodBarra.this)
                .setOrientationLocked(false)
                .initiateScan();
        });

        btnPerfumeria.setOnClickListener(view12 -> {
            categoria = "PERFUMERIA";
            IntentIntegrator.forSupportFragment(busquedaCodBarra.this)
                    .setOrientationLocked(false)
                    .initiateScan();
        });

        txtDescripcion.setText("ESCANEAR...");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        enviarLista();
    }

    private void initView(View view) {
        btnPerfumeria = view.findViewById(R.id.btnPerfumeria);
        btn=view.findViewById(R.id.btnScan);
        txtDescripcion = view.findViewById(R.id.txtDescripcion);
        txtPrecio = view.findViewById(R.id.txtPrecio);
        recycler_lista_escaneado = view.findViewById(R.id.recycler_lista_escaneado);
    }

    // Get the results:
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null) {
            if(result.getContents() == null) {
                txtDescripcion.setText("Nulo che");
            } else {
                obtenerDtosDB(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void obtenerDtosDB(String contents) {
        procesarCB(contents);
        vMProducto.getProducto(codMar,codPro,categoria).observe(this, new Observer<producto>() {
            @Override
            public void onChanged(producto p) {
                if (p!=null){
                    proPreCloud.setProducto(p);
                    txtDescripcion.setText(p.getDtosExtras());
                    recuperarPrecio(p);
                }else{
                    txtDescripcion.setText("");
                    txtPrecio.setText("$");
                    crearDialog();
                }
            }
        });
    }
    private void recuperarPrecio(producto producto) {
        vMProducto.getPrecioProducto(producto, MainActivity.porcentaje, categoria).observe(this, new Observer<com.emproducciones.listapreciosalgunlugar.model.precio>() {
            @Override
            public void onChanged(precio p) {
                if(p!=null){
                    proPreCloud.setPrecio(p);
                    listaProductos.add(proPreCloud);
                    txtPrecio.setText("$ " + p.getPrecio());
                    enviarLista();
                }else {
                    txtDescripcion.setText("No No che");
                }
            }
        });
    }

    private void enviarLista() {
        recycler_lista_escaneado.setLayoutManager(layoutManager);
        viewModel = new BusquedaCodBarraViewModel(getActivity(),listaProductos);
        layoutManager = new LinearLayoutManager(getActivity());
        recycler_lista_escaneado.setAdapter(viewModel);
        recycler_lista_escaneado.setHasFixedSize(true);
    }

    private void procesarCB(String contents) {
        codMar = contents.substring(3,8);
        codPro = contents.substring(8,12);
    }

    public void crearDialog(){
        androidx.appcompat.app.AlertDialog.Builder aviso = new androidx.appcompat.app.AlertDialog.Builder(getActivity());

        LayoutInflater elInflado = requireActivity().getLayoutInflater();
        aviso.setView(elInflado.inflate(R.layout.dialog_sin_resultado, null))
                .setPositiveButton("ACEPTAR", (dialog, id) -> {

                });
        aviso.create().show();
    }
}


