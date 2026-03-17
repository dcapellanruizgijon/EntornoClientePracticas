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
        return serv.actualizarPais(id, pais);  // 👈 Descomenta y crea este método
    }

    @DeleteMapping("/{id}")
    public void eliminarPais(@PathVariable Integer id) {  // 👈 AÑADE @PathVariable
        serv.eliminarPais(serv.getPais(id));
    }
}