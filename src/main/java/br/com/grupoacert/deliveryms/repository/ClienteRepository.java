package br.com.grupoacert.deliveryms.repository;

import br.com.grupoacert.deliveryms.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCpfEqualsIgnoreCase(String cpf);

}
