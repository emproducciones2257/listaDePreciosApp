package com.emproducciones.listapreciosalgunlugar.viewModel;

import com.emproducciones.listapreciosalgunlugar.Repositorio.consultaRepository;
import com.emproducciones.listapreciosalgunlugar.model.producto;

public class vMProducto {

    private consultaRepository cRepo;

    public vMProducto(){
        cRepo = new consultaRepository();
    }

    public producto getProducto(int codMar, int codProdu){return cRepo.obtenerProducto(codMar,codProdu);}
}
