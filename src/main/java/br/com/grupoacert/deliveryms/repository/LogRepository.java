package br.com.grupoacert.deliveryms.repository;

import br.com.grupoacert.deliveryms.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {


}
