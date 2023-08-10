package br.com.grupoacert.deliveryms.service;

import br.com.grupoacert.deliveryms.domain.Cargo;
import br.com.grupoacert.deliveryms.domain.Usuario;
import br.com.grupoacert.deliveryms.repository.CargoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CargoServiceTest {

    @InjectMocks
    private CargoService rolesService;
    @Mock
    private CargoRepository rolesRepository;

    @Test
    public void shouldTestFindByRoleName() {
        Cargo rolesEntity = getRolesEntity();
        when(rolesRepository.findByNomeCargoIgnoreCase(anyString())).thenReturn(Optional.of(rolesEntity));

        Cargo roleRecovery = rolesService.encontrarPeloNomeDoCargo(rolesEntity.getNomeCargo());

        assertNotNull(roleRecovery);
        assertEquals(rolesEntity.getId(), roleRecovery.getId());
        assertEquals(rolesEntity.getNomeCargo(), roleRecovery.getNomeCargo());
        assertEquals(rolesEntity.getUsuarios(), roleRecovery.getUsuarios());
    }

    private static Cargo getRolesEntity() {
        Cargo rolesEntity = new Cargo();
        rolesEntity.setId(1L);
        rolesEntity.setNomeCargo("ROLE_FACILITATOR");
        rolesEntity.setUsuarios(Collections.singleton(getUserEntity()));
        return rolesEntity;
    }

    private static Usuario getUserEntity() {
        Usuario userEntity = new Usuario();
        userEntity.setId(1L);
        userEntity.setEmail("willian@gmail.com");
        userEntity.setSenha("123");
        return userEntity;
    }

}