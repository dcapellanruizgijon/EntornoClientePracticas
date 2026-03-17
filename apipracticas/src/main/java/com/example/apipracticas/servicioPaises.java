package com.example.apipracticas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class servicioPaises { // solo implementa si tenemos los servicios en una interfaz a parte

    @Autowired
    private repositorio repo; // Tu repositorio JPA

    public List<Pais> getAllPaises() {
        return repo.findAll();
    }

    public Pais getPais(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public Pais guardaPais(Pais pais) {
        return repo.save(pais); // Guarda y devuelve el objeto con ID
    }

    public Pais actualizarPais(Integer id, Pais paisActualizado) {
        Pais paisExistente = repo.findById(id).orElse(null);
        if (paisExistente != null) {
            // Actualizar campos
            paisExistente.setMotivoViaje(paisActualizado.getMotivoViaje());
            paisExistente.setZona(paisActualizado.getZona());
            paisExistente.setPresupuesto(paisActualizado.getPresupuesto());
            paisExistente.setPrioridad(paisActualizado.getPrioridad());
            paisExistente.setNotasPersonales(paisActualizado.getNotasPersonales());
            // nombre y bandera NO se actualizan
            return repo.save(paisExistente);
        }
        return null;
    }

    public void eliminarPais(Pais pais) {
        repo.delete(pais);
    }

}
