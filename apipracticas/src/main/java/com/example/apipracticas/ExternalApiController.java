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
    public ResponseEntity<String> getPaises() {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        // aceptar JSON
        headers.add("Accept", "application/json");
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> resp = rt.exchange(RESTCOUNTRIES_URL, HttpMethod.GET, request, String.class);
        return ResponseEntity.status(resp.getStatusCode()).body(resp.getBody());
    }
}
