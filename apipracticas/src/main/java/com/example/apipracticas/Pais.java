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
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Para autoincrementar
    private Integer id;
    @Column
    private String nombre;
    @Column
    private String bandera;

    @Column
    private String motivoViaje;
    @Column
    private String zona;
    @Column
    private Integer presupuesto;
    @Column
    private Integer prioridad;
    @Column
    private String notasPersonales;

    public Pais(Integer id, String nombre, String bandera, String motivoViaje, String zona, Integer presupuesto,
            Integer prioridad, String notasPersonales) {
        this.id = id;
        this.nombre = nombre;
        this.bandera = bandera;
        this.motivoViaje = motivoViaje;
        this.zona = zona;
        this.presupuesto = presupuesto;
        this.prioridad = prioridad;
        this.notasPersonales = notasPersonales;
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

    public String getBandera() {
        return bandera;
    }

    public void setBandera(String bandera) {
        this.bandera = bandera;
    }

    public String getMotivoViaje() {
        return motivoViaje;
    }

    public void setMotivoViaje(String motivoViaje) {
        this.motivoViaje = motivoViaje;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public Integer getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Integer presupuesto) {
        this.presupuesto = presupuesto;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public String getNotasPersonales() {
        return notasPersonales;
    }

    public void setNotasPersonales(String notasPersonales) {
        this.notasPersonales = notasPersonales;
    }

}
