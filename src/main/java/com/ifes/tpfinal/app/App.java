// src/main/java/com/ifes/tpfinal/app/App.java
package com.ifes.tpfinal.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import java.util.Properties;

@SpringBootApplication(scanBasePackages = "com.ifes.tpfinal")
public class App {

// src/main/java/com/ifes/tpfinal/app/App.java
   @Bean
public javax.jdo.PersistenceManagerFactory pmf() {
    java.util.Properties p = new java.util.Properties();
    // ESTA es la clave correcta:
    p.setProperty("javax.jdo.option.PersistenceUnitName", "mysql");
    return javax.jdo.JDOHelper.getPersistenceManagerFactory("mysql");

}


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
