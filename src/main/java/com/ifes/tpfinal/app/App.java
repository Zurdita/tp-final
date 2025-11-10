package com.ifes.tpfinal.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ifes.tpfinal")   // 👈 ESTA LÍNEA ES LA CLAVE
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
