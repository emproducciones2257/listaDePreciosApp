package com.emproducciones.listapreciosalgunlugar.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.emproducciones.listapreciosalgunlugar.Repositorio.consultaRepository;
import com.emproducciones.listapreciosalgunlugar.Repositorio.ModeloClases.precio;
import com.emproducciones.listapreciosalgunlugar.Repositorio.ModeloClases.producto;


public class vMProducto extends ViewModel {

    public static int porcentaje=0;

    private consultaRepository cRepo;

    public void vMProducto(){
        obtenerPorcentaje();
    }

    private void obtenerPorcentaje(){
        cRepo.obtenerPorcentaje().observe(null, por -> porcentaje=por);
    }

    public vMProducto(){
        cRepo = new consultaRepository();
    }

    public MutableLiveData<producto> getProducto(String codMar, String codProdu, String categoria){return cRepo.obtenerProducto(codMar,codProdu,categoria);}

    public MutableLiveData<precio> getPrecioProducto(producto producto, int porcentaje, String categoria){return cRepo.obtenerPrecioProducto(producto,porcentaje,categoria);}

    public MutableLiveData<Integer> getPorcentaje() {return cRepo.obtenerPorcentaje();}
}
