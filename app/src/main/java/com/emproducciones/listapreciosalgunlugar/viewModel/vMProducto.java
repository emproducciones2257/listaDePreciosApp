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

    public MutableLiveData<producto> getProducto(String codMar, String codProdu, String categoria){return cRepo.obtenerProducto(codMar,codProdu,categoria);}

    public MutableLiveData<precio> getPrecioProducto(producto producto, int porcentaje, String categoria){return cRepo.obtenerPrecioProducto(producto,porcentaje,categoria);}

    public MutableLiveData<Integer> getPorcentaje() {return cRepo.obtenerPorcentaje();}
}
