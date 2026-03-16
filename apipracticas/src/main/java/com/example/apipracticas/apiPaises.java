package com.example.apipracticas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("apipracticas/paises")
public class apiPaises {

    @Autowired
    servicioPaises serv;

    @GetMapping
    public List<Pais> getAllPaises() {
        return serv.getAllPaises();
    }

    @GetMapping("/{id}")
    public Pais getPais(@PathVariable Integer id) {
        return serv.getPais(id);
    }

    //espera un JSON en el body de la petición
    @PostMapping
    public void guardarPais(@RequestBody Pais pais) {
        serv.guardaPais(pais);
    }

    // @PutMapping("/{id}")  // PUT para actualizar
    // public void actualizarPais(@PathVariable Integer id, @RequestBody Pais pais) {
    //     serv.setPais(id, pais);
    // }

    @DeleteMapping("/{id}")
    public void eliminarPais(Integer id) {
        Pais p=serv.getPais(id);
        serv.eliminarPais(p);
    }

}
