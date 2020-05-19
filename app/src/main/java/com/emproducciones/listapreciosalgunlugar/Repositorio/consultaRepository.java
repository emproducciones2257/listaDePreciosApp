package com.emproducciones.listapreciosalgunlugar.Repositorio;

import android.util.Log;

import androidx.annotation.NonNull;

import com.emproducciones.listapreciosalgunlugar.model.precio;
import com.emproducciones.listapreciosalgunlugar.model.proPreCloud;
import com.emproducciones.listapreciosalgunlugar.model.producto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class consultaRepository {
    private FirebaseFirestore db;
    private producto pr;
    private precio pre;
    private proPreCloud producto;

    public consultaRepository (){
        db = FirebaseFirestore.getInstance();
        producto = new proPreCloud();
        pr = new producto();
    }

    public proPreCloud obtenerProducto(int codMar, int codProdu){
        db.collection(CONSTANTES.PRODUCTOS)
                .whereEqualTo(CONSTANTES.CODMAR,codMar)
                .whereEqualTo(CONSTANTES.CODPROD,codProdu)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            pr = document.toObject(producto.class);
                            producto.setProducto(pr);
                            obtenerPrecioProducto();
                        }
                    }else {
                    }
                });
        return producto;
    }

    private void obtenerPrecioProducto() {
        db.collection(CONSTANTES.PRECIOS)
                .whereEqualTo(CONSTANTES.IDPRECIO,producto.getProducto().getPrecio())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            pre = document.toObject(precio.class);
                            producto.setPrecio(pre);
                        }
                    }
                });
    }
}
