package ar.edu.utn.frbb.tup.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @GetMapping
    public void hola() {

        System.out.println("hola");
    }
}
