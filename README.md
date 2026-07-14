# Taller GA — ORM vs. SQL puro

**Aplicaciones Web · Ingeniería de Software · UTEQ**
Comparación de un mismo CRUD implementado con **JDBC puro + PreparedStatement** y con **Spring Data JPA + Hibernate**, sobre PostgreSQL 16.

## Estructura del repositorio

```
taller-ga-orm-vs-sql/
├── taller-jdbc-puro/     # CRUD con java.sql (JDBC) y PreparedStatement
├── taller-jpa/            # Mismo CRUD con Spring Data JPA + Hibernate
├── tabla_comparativa_taller_GA.docx   # Tabla comparativa final (4 criterios)
└── README.md
```

Ambos proyectos son módulos Maven **independientes** que apuntan a la **misma base de datos y tabla** (`taller_db.productos`, 100 registros sembrados), para que la comparación de tiempos sea válida.

## Requisitos previos

- Java 21 LTS
- PostgreSQL 16 corriendo en `localhost:5432`
- Base `taller_db`, usuario `taller` / contraseña `taller`, tabla `productos` con 100 filas sembradas

## Cómo ejecutar cada módulo

### `taller-jdbc-puro`
```bash
cd taller-jdbc-puro
mvn compile exec:java -Dexec.mainClass="ec.edu.uteq.taller.Main"
```
Ejecuta: demo de inyección SQL (versión insegura vs. segura), medición del listado con `System.nanoTime()` y `StopWatch`, y un ciclo crear/eliminar.

### `taller-jpa`
```bash
cd taller-jpa
mvn compile exec:java -Dexec.mainClass="ec.edu.uteq.tallerjpa.Main"
```
Ejecuta lo mismo usando `JpaRepository`: `findByNombre` (consulta derivada), `findAll`, `save`, `deleteById`.

## Resultados

La tabla comparativa completa (líneas de código, tiempo del listado, mantenibilidad y prevención de inyección SQL) está en **`tabla_comparativa_taller_GA.docx`**.

En resumen: ambas versiones bloquean correctamente el ataque `' OR '1'='1'` cuando usan consultas parametrizadas (`PreparedStatement` / Hibernate); la versión JDBC insegura (concatenación de cadenas) sí es vulnerable, como se demuestra en `ProductoRepositorioInseguro.java`.

## Autor
Zaida Taipe Mora
Asignatura: Aplicaciones Web (5.º semestre) · Docente: Dr. Gleiston Cicerón Guerrero Ulloa, Ph.D. · Período: PPA 2026–2027
