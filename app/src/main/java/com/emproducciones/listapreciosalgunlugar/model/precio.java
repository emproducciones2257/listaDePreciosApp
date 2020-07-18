package com.emproducciones.listapreciosalgunlugar.model;

public class precio {

    private String idPrecio;
    private String idPrecioBDLocal;
    private Double precio;

    public precio() {}

    public String getIdPrecio() {
        return idPrecio;
    }

    public void setIdPrecio(String idPrecio) {
        this.idPrecio = idPrecio;
    }

    public String getIdPrecioBDLocal() {
        return idPrecioBDLocal;
    }

    public void setIdPrecioBDLocal(String idPrecioBDLocal) {
        this.idPrecioBDLocal = idPrecioBDLocal;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "precio{" +
                "idPrecio='" + idPrecio + '\'' +
                ", idPrecioBDLocal='" + idPrecioBDLocal + '\'' +
                ", precio=" + precio +
                '}';
    }
}
