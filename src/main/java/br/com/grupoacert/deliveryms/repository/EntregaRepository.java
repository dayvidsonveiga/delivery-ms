package br.com.grupoacert.deliveryms.repository;

import br.com.grupoacert.deliveryms.domain.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {

}
