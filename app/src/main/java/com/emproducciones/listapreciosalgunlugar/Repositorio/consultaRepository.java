package com.emproducciones.listapreciosalgunlugar.Repositorio;

import androidx.lifecycle.MutableLiveData;

import com.emproducciones.listapreciosalgunlugar.MainActivity;
import com.emproducciones.listapreciosalgunlugar.model.dtosNecesarios;
import com.emproducciones.listapreciosalgunlugar.model.precio;
import com.emproducciones.listapreciosalgunlugar.model.producto;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class consultaRepository {
    private FirebaseFirestore db;
    private producto pr;
    private precio pre;
    private dtosNecesarios dtosGlobal;

    public consultaRepository (){
        db = FirebaseFirestore.getInstance();
        pr = new producto();
        pre = new precio();
        dtosGlobal = new dtosNecesarios();
    }

    public MutableLiveData<producto> obtenerProducto(String codMar, String codProdu){
        MutableLiveData<producto> listita = new MutableLiveData<>();

        db.collection(CONSTANTES.PRODUCTOS)
                .whereEqualTo(CONSTANTES.CODMAR,codMar)
                .whereEqualTo(CONSTANTES.CODPROD,codProdu)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot docu) {
                        if (!docu.isEmpty()){
                            for (QueryDocumentSnapshot document : docu) {
                                pr = document.toObject(producto.class);
                            }
                        }
                        listita.setValue(pr);
                    }
                });

        return listita;
    }

    public MutableLiveData<precio> obtenerPrecioProducto(producto producto,int porcentaje){
        MutableLiveData<precio> listita = new MutableLiveData<>();

        db.collection(CONSTANTES.PRECIOS)
                .whereEqualTo(CONSTANTES.IDPRECIO,producto.getPrecio())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot docu) {
                        if (!docu.isEmpty()){
                            for (QueryDocumentSnapshot document : docu) {
                                pre = document.toObject(precio.class);
                                System.out.println(pre.toString());
                            }
                        }
                        if (producto.getUnidadDeVenta()!=0){
                           pre.setPrecio(retornarValorPorcentaje(pre.getPrecio()/producto.getUnidadDeVenta(),porcentaje));
                        }else pre.setPrecio(retornarValorPorcentaje(pre.getPrecio(),porcentaje));

                        listita.setValue(pre);
                    }
                });

        return listita;
    }

    public MutableLiveData<Integer> obtenerPorcentaje() {
        MutableLiveData<Integer> porcen = new MutableLiveData<>();
        db.collection(CONSTANTES.DTOSNECESARIO)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot docu) {
                        if (!docu.isEmpty()){
                            dtosNecesarios dtos = new dtosNecesarios();
                            for (QueryDocumentSnapshot document : docu) {
                                dtos = document.toObject(dtosNecesarios.class);
                            }
                            dtosGlobal = dtos;
                        }
                        porcen.setValue(dtosGlobal.getPorcentaje());
                    }
                });
        return porcen;
    }

    private Double retornarValorPorcentaje(double precio, int porcentaje) {
        double temp = (((precio * porcentaje)/100)+precio);
        double retorno = redondearPrecio(temp);
        return retorno;
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
