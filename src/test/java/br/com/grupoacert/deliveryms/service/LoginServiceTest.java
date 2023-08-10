package br.com.grupoacert.deliveryms.service;

import br.com.grupoacert.deliveryms.domain.Cargo;
import br.com.grupoacert.deliveryms.domain.Usuario;
import br.com.grupoacert.deliveryms.dto.entrada.DtoUsuario;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoUsuarioLogado;
import br.com.grupoacert.deliveryms.exception.RegraDeNegocioException;
import br.com.grupoacert.deliveryms.security.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class LoginServiceTest {


    @InjectMocks
    private LoginService logginService;
    @Mock
    private UsuarioService userService;
    @Mock
    private TokenService tokenService;
    @Mock
    private AuthenticationManager authenticationManager;


    @Test
    public void shouldTestLoginWithSuccess() {
        DtoUsuario userLoginDTO = getUserLoginDTO();
        Usuario userEntity = getUserEntity();
        DtoRetornoUsuarioLogado userLoginReturnDTO = getUserLoginReturnDTO();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userEntity,
                        userLoginDTO.getPass()
                );
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        when(userService.findByEmail(anyString())).thenReturn(userEntity);
        when(userService.checkPasswordIsCorrect(anyString(), anyString())).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(usernamePasswordAuthenticationToken);
        when(userService.logar(userLoginDTO, null)).thenReturn(userLoginReturnDTO);

        DtoRetornoUsuarioLogado userLoginReturnDTO1 = logginService.login(userLoginDTO);

        assertNotNull(userLoginReturnDTO1);
    }

    @Test()
    public void shouldTestLoginWithoutSuccess() {
        Usuario userEntity = getUserEntity();
        DtoUsuario userLoginDTO = getUserLoginDTO();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPass());
        usernamePasswordAuthenticationToken.setDetails(userEntity);

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        when(userService.findByEmail(anyString())).thenReturn(userEntity);

        assertThrows(RegraDeNegocioException.class, () -> logginService.login(userLoginDTO));
    }

    private static DtoUsuario getUserLoginDTO() {
        DtoUsuario userLoginDTO = new DtoUsuario();
        userLoginDTO.setEmail("danyllo@gmail.com");
        userLoginDTO.setPass("123");
        return userLoginDTO;
    }

    private static Usuario getUserEntity() {
        Usuario userEntity = new Usuario();
        userEntity.setId(1L);
        userEntity.setEmail("danyllo@gmail.com");
        userEntity.setSenha("123");
        Cargo roles = new Cargo();
        roles.setNomeCargo("ROLE_MEMBER");
        userEntity.setCargo(roles);
        return userEntity;
    }

    public static DtoRetornoUsuarioLogado getUserLoginReturnDTO() {
        return DtoRetornoUsuarioLogado.builder()
                .id(1L)
                .email("danyllo@gmail.com")
                .token("token")
                .build();
    }

}