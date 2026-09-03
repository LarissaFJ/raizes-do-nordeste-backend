package br.com.raizesdonordeste.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmokeTestController {

    private static final Logger log = LoggerFactory.getLogger(SmokeTestController.class);

    @GetMapping("/api/smoke")
    public String smokeTest() {
        log.info("Endpoint de smoke test foi acionado");
        return "API Raízes do Nordeste funcionando!";
    }
   // Commit do git
}
