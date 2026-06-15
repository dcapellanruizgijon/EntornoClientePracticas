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
        // Configurar RestTemplate con timeouts cortos
        org.springframework.http.client.SimpleClientHttpRequestFactory rf = new org.springframework.http.client.SimpleClientHttpRequestFactory();
        rf.setConnectTimeout(5000);
        rf.setReadTimeout(10000);
        RestTemplate rt = new RestTemplate(rf);

        Exception lastEx = null;
        for (String url : CANDIDATE_URLS) {
            try {
                org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
                headers.set("Accept", "application/json");
                // Algunos servidores rechazan requests sin User-Agent
                headers.set("User-Agent", "Mozilla/5.0 (compatible; cliente-practicas/1.0)");
                org.springframework.http.HttpEntity<Void> request = new org.springframework.http.HttpEntity<>(headers);

                org.springframework.http.ResponseEntity<Object[]> resp = rt.exchange(url, HttpMethod.GET, request, Object[].class);
                if (resp != null && resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null && resp.getBody().length > 0) {
                    return ResponseEntity.ok().body(resp.getBody());
                }
            } catch (Exception ex) {
                lastEx = ex;
                // intentar siguiente URL
            }
        }
        if (lastEx != null) lastEx.printStackTrace();
        return ResponseEntity.status(502).body("[]");
    }
}
