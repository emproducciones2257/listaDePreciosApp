package com.emproducciones.listapreciosalgunlugar.Repositorio;

import androidx.lifecycle.MutableLiveData;

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

    public MutableLiveData<precio> obtenerPrecioProducto(int codPrecio){
        MutableLiveData<precio> listita = new MutableLiveData<>();

        db.collection(CONSTANTES.PRECIOS)
                .whereEqualTo(CONSTANTES.IDPRECIO,codPrecio)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot docu) {
                        if (!docu.isEmpty()){
                            for (QueryDocumentSnapshot document : docu) {
                                pre = document.toObject(precio.class);
                            }
                        }
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
}
