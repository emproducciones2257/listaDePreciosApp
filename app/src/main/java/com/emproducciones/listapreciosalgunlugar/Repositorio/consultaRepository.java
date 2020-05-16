package com.emproducciones.listapreciosalgunlugar.Repositorio;

import androidx.annotation.NonNull;

import com.emproducciones.listapreciosalgunlugar.model.producto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class consultaRepository {
    private FirebaseFirestore db;
    private producto pr;

    public consultaRepository (){
        db = FirebaseFirestore.getInstance();
    }

    public producto obtenerProducto(int codMar, int codProdu){
        db.collection(CONSTANTES.PRODUCTOS)
                .whereEqualTo(CONSTANTES.CODMAR,codMar)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                pr = document.toObject(producto.class);
                            }
                        } else {

                        }
                    }
                });
        return pr;
    }
}
