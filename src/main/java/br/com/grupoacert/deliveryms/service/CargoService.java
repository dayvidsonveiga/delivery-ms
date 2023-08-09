package br.com.grupoacert.deliveryms.service;

import br.com.grupoacert.deliveryms.domain.Cargo;
import br.com.grupoacert.deliveryms.repository.CargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CargoService {

    private final CargoRepository cargoRepository;

    public Cargo criar(String nomeCargo) {

        Cargo cargo = new Cargo();

        cargo.setNomeCargo(nomeCargo);

        return cargoRepository.save(cargo);

    }

    public Cargo encontrarPeloNomeDoCargo(String nomeCargo) {

        Optional<Cargo> cargo = cargoRepository.findByNomeCargoIgnoreCase(nomeCargo);

        return cargo.orElseGet(() -> criar(nomeCargo));

    }

}
