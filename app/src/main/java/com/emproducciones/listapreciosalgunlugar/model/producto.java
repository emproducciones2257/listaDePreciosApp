package com.emproducciones.listapreciosalgunlugar.model;

import androidx.annotation.NonNull;

public class producto {

    private int idProducto;
    private int Precio;
    private int unidadDeVenta = 0;
    private String dtosExtras;
    private String codProd;
    private String codMarc;

    public producto() {
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int precio) {
        Precio = precio;
    }

    public int getUnidadDeVenta() {
        return unidadDeVenta;
    }

    public void setUnidadDeVenta(int unidadDeVenta) {
        this.unidadDeVenta = unidadDeVenta;
    }

    public String getDtosExtras() {
        return dtosExtras;
    }

    public void setDtosExtras(String dtosExtras) {
        this.dtosExtras = dtosExtras;
    }

    public String getCodProd() {
        return codProd;
    }

    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }

    public String getCodMarc() {
        return codMarc;
    }

    public void setCodMarc(String codMarc) {
        this.codMarc = codMarc;
    }

    @Override
    public String toString() {
        return "producto{" +
                "idProducto=" + idProducto +
                ", Precio=" + Precio +
                ", unidadDeVenta=" + unidadDeVenta +
                ", dtosExtras='" + dtosExtras + '\'' +
                ", codProd=" + codProd +
                ", codMarc=" + codMarc +
                '}';
    }
}
