package com.emproducciones.listapreciosalgunlugar;

import androidx.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.*;
import android.widget.*;
import com.emproducciones.listapreciosalgunlugar.model.*;
import com.emproducciones.listapreciosalgunlugar.viewModel.vMProducto;
import com.google.zxing.integration.android.*;

import java.util.ArrayList;

public class busquedaCodBarra extends Fragment {

    private BusquedaCodBarraViewModel mViewModel;
    private TextView txtDescripcion, txtPrecio;
    private Button btn;
    private String codMar,codPro;
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

        btn.setOnClickListener(view1 ->
                IntentIntegrator.forSupportFragment(busquedaCodBarra.this)
                .setOrientationLocked(false)
                .initiateScan());
        txtDescripcion.setText("ESCANEAR...");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        enviarLista();
    }

    private void initView(View view) {
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
        vMProducto.getProducto(codMar,codPro).observe(this, new Observer<producto>() {
            @Override
            public void onChanged(producto p) {
                if (!p.getDtosExtras().isEmpty()){
                    proPreCloud.setProducto(p);
                    txtDescripcion.setText(p.getDtosExtras());
                    recuperarPrecio(p.getPrecio());
                }else {
                    txtDescripcion.setText("No No che");
                    //TODO insertar cuadro de dialogo que no encontro producto
                }
            }
        });
    }
    private void recuperarPrecio(int precio) {
        vMProducto.getPrecioProducto(precio).observe(this, new Observer<com.emproducciones.listapreciosalgunlugar.model.precio>() {
            @Override
            public void onChanged(precio p) {
                if(p!=null){
                    proPreCloud.setPrecio(p);
                    listaProductos.add(proPreCloud);
                    if(proPreCloud.getProducto().getUnidadDeVenta()!=0){
                        txtPrecio.setText("$ " + retornarValorPorcentaje(p.getPrecio()/proPreCloud.getProducto().getUnidadDeVenta()));
                        enviarLista();
                    }
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

    private int retornarValorPorcentaje(double precio) {
        double temp = (((precio * MainActivity.porcentaje)/100)+precio);
        return redondearPrecio(temp);
    }

    private int redondearPrecio(double precio) {

        String str = String.valueOf(precio);
        int intNumber = Integer.parseInt(str.substring(0, str.indexOf('.')));
        long decNumberInt = Long.parseLong(str.substring(str.indexOf('.') + 1));
        String temp = String.valueOf(decNumberInt).substring(0, 1);

        if(Integer.parseInt(temp)<5) {
            return intNumber;
        }else {
            return intNumber +1;
        }
    }
}


