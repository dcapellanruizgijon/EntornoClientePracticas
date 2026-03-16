package com.example.apipracticas;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface repositorio extends JpaRepository<Pais, Integer>{
    
}
