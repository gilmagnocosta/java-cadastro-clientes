package br.com.gilmagno.cadastroclientes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gilmagno.cadastroclientes.entities.Cliente;

/**
 * Interface de repositorio do objeto Cliente
 * @author Gilmagno
 *
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
