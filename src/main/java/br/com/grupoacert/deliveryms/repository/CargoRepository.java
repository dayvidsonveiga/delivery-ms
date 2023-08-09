package br.com.grupoacert.deliveryms.repository;

import br.com.grupoacert.deliveryms.domain.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
    Optional<Cargo> findByNomeCargoIgnoreCase(String nomeCargo);

}
