package com.ifes.tpfinal.configuracion;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracionJDO {

    @Bean
    public PersistenceManagerFactory persistenceManagerFactory() {
        // El nombre "mysql" debe ser igual al <persistence-unit name="mysql"> en persistence.xml
        return JDOHelper.getPersistenceManagerFactory("mysql");
    }
}
