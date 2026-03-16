package com.example.apipracticas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "paises")
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Para autoincrementar    
    private Integer id;
    @Column
    private String nombre;
    @Column
    private boolean miembroOnu;
    @Column
    private String moneda;// nombre y simbolo
    @Column
    private String capital;//la capital del pais
    @Column
    private String region;
    @Column
    private String banderas;
    @Column
    private Integer poblacion;

    public Pais(Integer id, String nombre, boolean miembroOnu, String moneda, String capital, String region, String banderas,
            Integer poblacion) {
        this.id=id;
        this.nombre = nombre;
        this.miembroOnu = miembroOnu;
        this.moneda = moneda;
        this.capital = capital;
        this.region = region;
        this.banderas = banderas;
        this.poblacion = poblacion;
    }

    public Pais(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isMiembroOnu() {
        return miembroOnu;
    }

    public void setMiembroOnu(boolean miembroOnu) {
        this.miembroOnu = miembroOnu;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBanderas() {
        return banderas;
    }

    public void setBanderas(String banderas) {
        this.banderas = banderas;
    }

    public Integer getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(Integer poblacion) {
        this.poblacion = poblacion;
    }

}
