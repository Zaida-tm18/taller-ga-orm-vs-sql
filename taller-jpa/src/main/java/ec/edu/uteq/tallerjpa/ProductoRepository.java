package ec.edu.uteq.tallerjpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Consulta derivada: Hibernate la traduce a SQL parametrizado (PreparedStatement)
    // exactamente igual que ProductoRepositorioJdbc.buscarPorNombreSeguro().
    List<Producto> findByNombre(String nombre);
}
