package com.example.apipracticas;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/external")
public class ExternalApiController {

    private static final String RESTCOUNTRIES_URL = "https://restcountries.com/v5.1/all?fields=name,unMember,currencies,capital,region,flags,population";

    @GetMapping("/paises")
    public ResponseEntity<Object> getPaises() {
        RestTemplate rt = new RestTemplate();
        // Obtener como array de objetos para devolver JSON nativo
        Object[] resp = rt.getForObject(RESTCOUNTRIES_URL, Object[].class);
        if (resp == null) {
            return ResponseEntity.status(502).body("[]");
        }
        return ResponseEntity.ok().body(resp);
    }
}
