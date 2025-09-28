package br.com.desafio.propostacredito;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/hello")
    public String hello() {
        return "Aplicação está Ativa!";
    }
}