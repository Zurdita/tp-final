// src/main/java/com/ifes/tpfinal/app/App.java
package com.ifes.tpfinal.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.ifes.tpfinal")
public class App {

    // src/main/java/com/ifes/tpfinal/app/App.java
    @Bean
    public javax.jdo.PersistenceManagerFactory pmf() {
        java.util.Properties p = new java.util.Properties();
        p.setProperty("javax.jdo.PersistenceManagerFactoryClass",
                "org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
        p.setProperty("javax.jdo.option.ConnectionURL", "jdbc:mysql://dante-dev.duckdns.org:3306/datanucleus");
        p.setProperty("javax.jdo.option.ConnectionDriverName", "com.mysql.cj.jdbc.Driver");
        p.setProperty("javax.jdo.option.ConnectionUserName", "root");
        p.setProperty("javax.jdo.option.ConnectionPassword", "tierrasanta");
        p.setProperty("datanucleus.autoCreateSchema", "true");

        return javax.jdo.JDOHelper.getPersistenceManagerFactory(p);
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
