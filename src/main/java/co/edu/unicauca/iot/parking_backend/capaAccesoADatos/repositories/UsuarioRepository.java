package co.edu.unicauca.iot.parking_backend.capaAccesoADatos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.iot.parking_backend.capaAccesoADatos.models.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
}