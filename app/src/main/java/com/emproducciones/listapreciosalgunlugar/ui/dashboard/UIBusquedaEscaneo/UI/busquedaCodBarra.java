package com.emproducciones.listapreciosalgunlugar.ui.dashboard.UIBusquedaEscaneo.UI;

import androidx.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.*;
import android.widget.*;
import com.emproducciones.listapreciosalgunlugar.R;
import com.emproducciones.listapreciosalgunlugar.Repositorio.ModeloClases.proPreCloud;
import com.emproducciones.listapreciosalgunlugar.Repositorio.ModeloClases.producto;
import com.emproducciones.listapreciosalgunlugar.ui.dashboard.UIBusquedaEscaneo.Evtn.evtnClickItemLista;
import com.emproducciones.listapreciosalgunlugar.viewModel.vMProducto;
import com.google.zxing.integration.android.*;
import java.util.ArrayList;

public class busquedaCodBarra extends Fragment implements evtnClickItemLista {

    private TextView txtDescripcion, txtPrecio,txtTotalVta;
    private Button btn,btnPerfumeria;
    private String codMar,codPro,categoria;
    private int totalVta, indiceSeleccionado;
    private vMProducto vMProducto;
    private proPreCloud proPreCloud;
    private ArrayList<proPreCloud> listaProductos;
    private RecyclerView recycler_lista_escaneado;
    private RecyclerView.LayoutManager layoutManager;
    private BusquedaCodBarraViewModel viewModel;

    public busquedaCodBarra() {
        vMProducto = new vMProducto();
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

        enviarLista(listaProductos);
    }

    private void initView(View view) {
        btnPerfumeria = view.findViewById(R.id.btnPerfumeria);
        btn=view.findViewById(R.id.btnScan);
        txtDescripcion = view.findViewById(R.id.txtDescripcion);
        txtPrecio = view.findViewById(R.id.txtPrecio);
        txtTotalVta = view.findViewById(R.id.txtTotalVta);
        recycler_lista_escaneado = view.findViewById(R.id.recycler_lista_escaneado);
        totalVta = 0;
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

        proPreCloud = new proPreCloud();
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
                    crearDialog("PRODUCTO");
                }
            }
        });
    }
    private void recuperarPrecio(producto producto) {
        vMProducto.getPrecioProducto(producto, vMProducto.porcentaje, categoria).observe(this, p -> {
            if(p!=null){
                proPreCloud.setPrecio(p);
                proPreCloud.setCantidad(1);
                agregarProductoAListaVentaActual(proPreCloud);
                txtPrecio.setText("$ " + p.getPrecio());
                totalVta += p.getPrecio();
                txtTotalVta.setText("$" + totalVta);
                enviarLista(listaProductos);
            }else crearDialog("PRECIO");
        });
    }

    private void agregarProductoAListaVentaActual(com.emproducciones.listapreciosalgunlugar.Repositorio.ModeloClases.proPreCloud productoNuevo) {

        boolean estado = false;
        if(!listaProductos.isEmpty()){
            for (proPreCloud e:listaProductos) {
                if (e.getProducto().getDtosExtras().equals(productoNuevo.getProducto().getDtosExtras())){
                    e.setCantidad(e.getCantidad()+1);
                    estado=true;
                }
            }
            if (!estado) listaProductos.add(productoNuevo);
        }else listaProductos.add(productoNuevo);
    }

    private void enviarLista(ArrayList<proPreCloud> lis) {
        recycler_lista_escaneado.setLayoutManager(layoutManager);
        layoutManager = new LinearLayoutManager(getActivity());
        viewModel = new BusquedaCodBarraViewModel(getActivity(),lis,this);
        recycler_lista_escaneado.setAdapter(viewModel);
        recycler_lista_escaneado.setHasFixedSize(true);
    }

    private void procesarCB(String contents) {
        codMar = contents.substring(3,8);
        codPro = contents.substring(8,12);
    }

    public void crearDialog(String elPerdido){
        TextView txtTextoSinResultado;
        View view;
        androidx.appcompat.app.AlertDialog.Builder aviso = new androidx.appcompat.app.AlertDialog.Builder(getActivity());

        LayoutInflater elInflado = requireActivity().getLayoutInflater();
        view = elInflado.inflate(R.layout.dialog_sin_resultado, null);

        txtTextoSinResultado = view.findViewById(R.id.txtTextoSinResultado);
        txtTextoSinResultado.setText("NO SE ENCUENTRA EL " + elPerdido);
        aviso.setView(view)
                .setPositiveButton("ACEPTAR", (dialog, id) -> {
                });
        aviso.create().show();
    }

    public void crearDialogItemClick(String txtNombreEscaneado){
        TextView txtNombreProducto,txtCantidad,txtPrecioUnidad,txtTotal;
        ImageButton imgSumar,imgRestar;
        View view;
        proPreCloud prodSeleccionado = recuperarProductoSeleccionado(txtNombreEscaneado);

        androidx.appcompat.app.AlertDialog.Builder aviso = new androidx.appcompat.app.AlertDialog.Builder(getActivity());

        LayoutInflater elInflado = requireActivity().getLayoutInflater();
        view = elInflado.inflate(R.layout.dialog_click_item, null);

        txtNombreProducto = view.findViewById(R.id.txtNombreProducto);
        txtCantidad = view.findViewById(R.id.txtCantidad);
        txtPrecioUnidad = view.findViewById(R.id.txtPrecioUnidad);
        txtTotal = view.findViewById(R.id.txtTotal);
        imgSumar = view.findViewById(R.id.imgSumar);
        imgRestar = view.findViewById(R.id.imgRestar);

        txtNombreProducto.setText(prodSeleccionado.getProducto().getDtosExtras());
        txtCantidad.setText(prodSeleccionado.getCantidad()+"");
        txtPrecioUnidad.setText("$ " + prodSeleccionado.getPrecio().getPrecio());
        txtTotal.setText("$ " + prodSeleccionado.getCantidad()*prodSeleccionado.getPrecio().getPrecio());

        imgSumar.setOnClickListener(view1 -> {
            listaProductos.get(indiceSeleccionado).setCantidad((listaProductos.get(indiceSeleccionado).getCantidad()+1));
            totalVta += listaProductos.get(indiceSeleccionado).getPrecio().getPrecio();
            txtCantidad.setText(+listaProductos.get(indiceSeleccionado).getCantidad()+"");
            txtTotal.setText("$ " + listaProductos.get(indiceSeleccionado).getCantidad()*listaProductos.get(indiceSeleccionado).getPrecio().getPrecio());
        });

        imgRestar.setOnClickListener(view12 -> {
            if(listaProductos.get(indiceSeleccionado).getCantidad()>0){
                listaProductos.get(indiceSeleccionado).setCantidad((listaProductos.get(indiceSeleccionado).getCantidad()-1));
                txtTotal.setText("$ " + listaProductos.get(indiceSeleccionado).getCantidad()*listaProductos.get(indiceSeleccionado).getPrecio().getPrecio());
            }
            txtCantidad.setText(+listaProductos.get(indiceSeleccionado).getCantidad()+"");
            if(totalVta-listaProductos.get(indiceSeleccionado).getPrecio().getPrecio()>=0) totalVta -= listaProductos.get(indiceSeleccionado).getPrecio().getPrecio();

            txtTotal.setText("$ " + listaProductos.get(indiceSeleccionado).getCantidad()*listaProductos.get(indiceSeleccionado).getPrecio().getPrecio());
        });

        aviso.setView(view)
                .setPositiveButton("ACEPTAR", (dialog, id) -> {
                    enviarLista(listaProductos);
                    txtTotalVta.setText("$" + totalVta);
                });

        aviso.setNegativeButton("ELIMINAR PRODUCTO", (dialogInterface, i) -> {
            totalVta -= listaProductos.get(indiceSeleccionado).getCantidad()*listaProductos.get(indiceSeleccionado).getPrecio().getPrecio();
            listaProductos.remove(indiceSeleccionado);
            enviarLista(listaProductos);
            txtTotalVta.setText("$" + totalVta);
        });

        aviso.create().show();
    }

    private proPreCloud recuperarProductoSeleccionado(String txtNombreEscaneado) {
        proPreCloud prodSeleccionado = new proPreCloud();
        for (int i = 0; i<listaProductos.size();i++){
            if (listaProductos.get(i).getProducto().getDtosExtras().equals(txtNombreEscaneado)){
                prodSeleccionado.setCantidad(listaProductos.get(i).getCantidad());
                prodSeleccionado.setPrecio(listaProductos.get(i).getPrecio());
                prodSeleccionado.setProducto(listaProductos.get(i).getProducto());
                indiceSeleccionado=i;
            }
        }return prodSeleccionado;
    }

    @Override
    public void eventoItemClick(String txtNombreEscaneado) {
        crearDialogItemClick(txtNombreEscaneado);
    }
}


