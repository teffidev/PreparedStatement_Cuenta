package main.model;

public class Cuenta {

    private Long id;
    private String nombre;
    private int nroCuenta;
    private int saldo;

    public Cuenta(){}

    public Cuenta(int nroCuenta, String nombre, int saldo) {
        this.nombre = nombre;
        this.nroCuenta = nroCuenta;
        this.saldo = saldo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(int nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}
