package com.emproducciones.listapreciosalgunlugar.model;

public class precio {

    private String idPrecio;
    private int idPrecioBDLocal;
    private Double precio;

    public precio() {}

    public String getIdPrecio() {
        return idPrecio;
    }

    public void setIdPrecio(String idPrecio) {
        this.idPrecio = idPrecio;
    }

    public int getIdPrecioBDLocal() {
        return idPrecioBDLocal;
    }

    public void setIdPrecioBDLocal(int idPrecioBDLocal) {
        this.idPrecioBDLocal = idPrecioBDLocal;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
