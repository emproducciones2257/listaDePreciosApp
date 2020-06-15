package com.emproducciones.listapreciosalgunlugar.viewModel;

import androidx.lifecycle.MutableLiveData;
import com.emproducciones.listapreciosalgunlugar.Repositorio.consultaRepository;
import com.emproducciones.listapreciosalgunlugar.model.precio;
import com.emproducciones.listapreciosalgunlugar.model.producto;


public class vMProducto {

    private consultaRepository cRepo;

    public vMProducto(){
        cRepo = new consultaRepository();
    }

    public MutableLiveData<producto> getProducto(String codMar, String codProdu){return cRepo.obtenerProducto(codMar,codProdu);}

    public MutableLiveData<precio> getPrecioProducto(int codPrecio){return cRepo.obtenerPrecioProducto(codPrecio);}

    public MutableLiveData<Integer> getPorcentaje() {return cRepo.obtenerPorcentaje();}
}
