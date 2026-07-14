# taller-jpa — Spring Data JPA + Hibernate

Segundo módulo del taller, para comparar contra `taller-jdbc-puro`. Usa la
**misma tabla** `productos` que ya creaste y sembraste (Paso 3 de la guía
JDBC) — no crea nada nuevo, solo la mapea con `@Entity`.

No es un proyecto Spring Boot: es un `Main.java` de consola, igual que el
módulo JDBC, para que la comparación de líneas de código y tiempos sea justa.
`AppConfig.java` reemplaza lo que en Spring Boot haría `application.yml` +
autoconfiguración.

## Estructura

```
taller-jpa/
|-- pom.xml
`-- src/main/java/ec/edu/uteq/tallerjpa/
    |-- Producto.java          (@Entity mapeada a "productos")
    |-- ProductoRepository.java (extends JpaRepository<Producto, Long>)
    |-- AppConfig.java          (DataSource, EntityManagerFactory, TransactionManager)
    `-- Main.java                (benchmark + demo, espejo del Main del JDBC)
```

## Cómo correrlo

1. Abre la carpeta `taller-jpa` en IntelliJ como proyecto Maven (o ábrelo
   junto al de JDBC como un módulo más del mismo *root* si prefieres).
2. Espera a que descargue las dependencias.
3. Verifica que PostgreSQL esté arriba y `taller_db` / usuario `taller`
   existan (mismos del Paso 2 de la guía JDBC).
4. Ejecuta `Main.java`.

## Qué vas a ver en consola

- Que el "ataque" `' OR '1'='1'` devuelve 0 filas (porque
  `findByNombre` se traduce siempre a un `PreparedStatement` con `?`).
- Tiempo del `findAll()` con `nanoTime` y con `StopWatch`, para llenar la
  fila (2) de tu tabla comparativa.
- Creación y borrado de un producto de prueba.

## Para la tabla comparativa

- **Líneas de código**: cuenta `Producto.java` + `ProductoRepository.java`
  (no cuentes `AppConfig.java`, porque eso es *infraestructura de arranque*
  que en un proyecto Spring Boot real ya viene resuelta por el framework;
  el equivalente real de "tu código" en JPA son solo la entidad y el
  repositorio).
- **Prevención de SQL Injection**: Hibernate genera siempre sentencias
  parametrizadas para los métodos de `JpaRepository` y para las consultas
  derivadas como `findByNombre`; no hay forma de "olvidarlo" como sí puede
  pasar concatenando cadenas a mano en JDBC.
