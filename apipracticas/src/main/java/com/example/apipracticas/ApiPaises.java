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
// http://localhost:8080/api/paises
@RestController
@RequestMapping("/api/paises")
public class ApiPaises {

    @Autowired
    private servicioPaises serv;

    @GetMapping
    public List<Pais> getAllPaises() {
        return serv.getAllPaises();
    }

    @GetMapping("/{id}")
    public Pais getPais(@PathVariable Integer id) {
        return serv.getPais(id);
    }

    @PostMapping
    public Pais guardarPais(@RequestBody Pais pais) {  //
        return serv.guardaPais(pais);
    }

    @PutMapping("/{id}")
    public Pais actualizarPais(@PathVariable Integer id, @RequestBody Pais pais) {
        return serv.actualizarPais(id, pais);  
    }

    @DeleteMapping("/{id}")
    public void eliminarPais(@PathVariable Integer id) {  
        serv.eliminarPais(serv.getPais(id));
    }
}