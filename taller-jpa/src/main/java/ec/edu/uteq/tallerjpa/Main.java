package ec.edu.uteq.tallerjpa;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext ctx =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {

            ProductoRepository repo = ctx.getBean(ProductoRepository.class);

            // ------------------------------------------------------------
            // 1) Demo: mismo "ataque" que en JDBC, pero via consulta derivada
            //    de Spring Data JPA. Hibernate SIEMPRE genera PreparedStatement
            //    parametrizado, asi que esto es la version segura por defecto.
            // ------------------------------------------------------------
            String ataque = "' OR '1'='1";
            System.out.println("=== JPA / Hibernate (parametrizado por defecto) ===");
            List<Producto> filas = repo.findByNombre(ataque);
            System.out.println("Filas devueltas al atacante: "
                    + filas.size()
                    + " (correcto: 0)");

            // ------------------------------------------------------------
            // 2) Medicion del listado (findAll) con System.nanoTime()
            // ------------------------------------------------------------
            long inicio = System.nanoTime();
            List<Producto> lista1 = repo.findAll();
            long fin = System.nanoTime();
            double ms1 = (fin - inicio) / 1_000_000.0;
            System.out.printf("nanoTime : %d filas en %.3f ms %n",
                    lista1.size(), ms1);

            // ------------------------------------------------------------
            // 3) Medicion del listado con Spring StopWatch
            // ------------------------------------------------------------
            StopWatch sw = new StopWatch("listar-jpa");
            sw.start("findAll()");
            List<Producto> lista2 = repo.findAll();
            sw.stop();
            System.out.printf("StopWatch: %d filas en %.3f ms %n",
                    lista2.size(),
                    sw.getTotalTimeNanos() / 1_000_000.0);
            System.out.println(sw.prettyPrint());

            // ------------------------------------------------------------
            // 4) Crear un producto nuevo
            // ------------------------------------------------------------
            Producto nuevo = repo.save(
                    new Producto("Producto de prueba JPA", new BigDecimal("99.99"), 5));
            System.out.println("Creado con id = " + nuevo.getId());

            // ------------------------------------------------------------
            // 5) Eliminarlo para dejar la base como estaba
            // ------------------------------------------------------------
            repo.deleteById(nuevo.getId());
            System.out.println("Eliminado: " + !repo.existsById(nuevo.getId()));
        }
    }
}
