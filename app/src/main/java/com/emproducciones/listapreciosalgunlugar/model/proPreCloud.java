package com.emproducciones.listapreciosalgunlugar.model;

public class proPreCloud {
    private precio precio;
    private producto producto;

    public proPreCloud() {
        precio = new precio();
        producto= new producto();
    }

    public precio getPrecio() {
        return precio;
    }

    public void setPrecio(precio precio) {
        this.precio = precio;
    }

    public producto getProducto() {
        return producto;
    }

    public void setProducto(producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "proPreCloud{" +
                "precio=" + precio.toString() +
                ", producto=" + producto.toString() +
                '}';
    }
}
