# TP Final - Programación II (Con JDO / Spring Boot / Thymeleaf)

- Herencia `Rodado <- Auto / Camioneta` con **tablas separadas** (NEW_TABLE).
- Concesionaria única (seed en backend).
- CRUD de Rodados (crear/editar/eliminar) y páginas Thymeleaf.
- Repos/Servicios con IoC Spring e inyección de PMF JDO.
- NamedQuery `Concesionaria.findByDomicilio` implementada.

## Requisitos
- JDK 21
- Maven 3.9+
- MySQL en localhost (usuario root / pass Prog1234)

## Preparación de BD (opcional pero recomendado)
```sql
CREATE DATABASE IF NOT EXISTS tpfinal CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
```
y en `META-INF/persistence.xml` cambiar la URL a `jdbc:mysql://localhost/tpfinal` si preferís usar schema fijo.

## Build y Run
```bash
mvn -U clean package
mvn org.datanucleus:datanucleus-maven-plugin:6.0.4:schema-create -Dpu=mysql
mvn spring-boot:run
```

## Endpoints
- `/concesionarias` (lista + informe)
- `/concesionarias/domicilio?dir=...`
- `/concesionarias/{id}/informe`
- `/rodados` (listado)
- `/rodados/crear` (form nuevo)
- `/rodados/{id}/editar` (form edición)
- `/rodados/{id}/eliminar` (POST)
