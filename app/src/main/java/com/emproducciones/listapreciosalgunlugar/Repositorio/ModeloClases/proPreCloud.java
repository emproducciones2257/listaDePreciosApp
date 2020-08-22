package com.emproducciones.listapreciosalgunlugar.Repositorio.ModeloClases;


public class proPreCloud {
    private com.emproducciones.listapreciosalgunlugar.Repositorio.ModeloClases.precio precio;
    private com.emproducciones.listapreciosalgunlugar.Repositorio.ModeloClases.producto producto;
    private int cantidad;

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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "proPreCloud{" +
                "precio=" + precio +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                '}';
    }
}
