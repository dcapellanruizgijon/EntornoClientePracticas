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

    // Intentar varias versiones públicas hasta obtener datos válidos
    private static final String[] CANDIDATE_URLS = new String[]{
        "https://restcountries.com/v5.1/all?fields=name,unMember,currencies,capital,region,flags,population",
        "https://restcountries.com/v5/all?fields=name,unMember,currencies,capital,region,flags,population",
        "https://restcountries.com/v3.1/all?fields=name,unMember,currencies,capital,region,flags,population"
    };

    @GetMapping("/paises")
    public ResponseEntity<Object> getPaises() {
        RestTemplate rt = new RestTemplate();
        Exception lastEx = null;
        for (String url : CANDIDATE_URLS) {
            try {
                Object[] resp = rt.getForObject(url, Object[].class);
                if (resp != null && resp.length > 0) {
                    return ResponseEntity.ok().body(resp);
                }
            } catch (Exception ex) {
                lastEx = ex;
                // probar siguiente URL
            }
        }
        // Si llegamos aquí no obtuvimos datos válidos
        if (lastEx != null) lastEx.printStackTrace();
        return ResponseEntity.status(502).body("[]");
    }
}
