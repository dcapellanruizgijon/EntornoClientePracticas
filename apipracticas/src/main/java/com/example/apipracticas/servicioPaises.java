package com.example.apipracticas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class servicioPaises{    //solo implementa si tenemos los servicios en una interfaz a parte

    @Autowired
    private repositorio repo;   

    public List<Pais> getAllPaises(){
        List<Pais> paises=repo.findAll();
        return paises;
    }

    public Pais getPais(Integer id){
        return repo.findById(id).get();
    }

    public void guardaPais(Pais pais){
        repo.save(pais);
        System.out.println("Pais guardado con exito");
    }

    public void setPais(Integer id, Pais PaisActualizado){
        Pais p=repo.findById(id).get();
        p.setBanderas(PaisActualizado.getBanderas());
        p.setCapital(PaisActualizado.getCapital());
        p.setMiembroOnu(PaisActualizado.isMiembroOnu());
        p.setMoneda(PaisActualizado.getMoneda());
        p.setNombre(PaisActualizado.getNombre());
        p.setRegion(PaisActualizado.getRegion());
        p.setPoblacion(PaisActualizado.getPoblacion());
    }

    public void eliminarPais(Pais p){
        try {
            repo.delete(p);
            System.out.println("Pais eliminado correctamente");
        } catch (Exception e) {
            System.out.println("El país no ha podido ser eliminado");
        }
    }

}
