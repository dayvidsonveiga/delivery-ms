package br.com.grupoacert.deliveryms.service;

import br.com.grupoacert.deliveryms.domain.Cargo;
import br.com.grupoacert.deliveryms.domain.Usuario;
import br.com.grupoacert.deliveryms.dto.entrada.DtoUsuario;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoUsuarioCriado;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoUsuarioLogado;
import br.com.grupoacert.deliveryms.exception.RegraDeNegocioException;
import br.com.grupoacert.deliveryms.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private CargoService cargoService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void shouldTestCreateWithSuccess() {
        DtoUsuario dtoUsuario = getDtoUsuario();
        Usuario userEntity = getUsuario();

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(userEntity);

        DtoRetornoUsuarioCriado response = usuarioService.criar(dtoUsuario);

        assertNotNull(response);
        assertEquals(userEntity.getEmail(), response.getEmail());
    }

    @Test
    public void shouldTestLoginWithSuccess() {
        Usuario userEntity = getUsuario();
        DtoUsuario userLoginDTO = getDtoUsuario();

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));

        DtoRetornoUsuarioLogado userLogin = usuarioService.logar(userLoginDTO, "string");

        assertNotNull(userLogin);
    }

    @Test
    public void shouldTestCheckPasswordIsCorrectTrue() {
        String pass1 = "teste";
        String pass2 = "teste";
        Boolean verify = true;

        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(verify);

        Boolean test = usuarioService.checkPasswordIsCorrect(pass1, pass2);

        assertEquals(test, true);
    }

    @Test
    public void shouldTestCheckPasswordIsCorrectFalse() {
        String pass1 = "teste";
        String pass2 = "string";
        Boolean verify = false;

        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(verify);

        Boolean test = usuarioService.checkPasswordIsCorrect(pass1, pass2);

        assertEquals(test, false);
    }

    @Test
    public void shouldTestListAllWithSuccess() {

        List<Usuario> listUsers = Collections.singletonList(getUsuario());

        when(usuarioRepository.findAll()).thenReturn(listUsers);

        List<DtoRetornoUsuarioCriado> userDTO = usuarioService.listarUsuarios();

        assertNotNull(userDTO);

    }

    @Test()
    public void shouldTestListAllWithoutSuccess() {

        List<Usuario> listUsers = new ArrayList<>();

        when(usuarioRepository.findAll()).thenReturn(listUsers);

        assertThrows(RegraDeNegocioException.class, () -> usuarioService.listarUsuarios());
    }

    private static Usuario getUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("willian@gmail.com");
        usuario.setSenha("123");
        usuario.setCargo(getCargo());
        return usuario;
    }

    private static DtoUsuario getDtoUsuario() {
        DtoUsuario dtoUsuario = new DtoUsuario();
        dtoUsuario.setEmail("willian@gmail.com");
        dtoUsuario.setPass("123");
        return dtoUsuario;
    }

    private static Cargo getCargo() {
        Cargo cargo = new Cargo();
        cargo.setId(1L);
        cargo.setNomeCargo("CARGO_ADM");
        return cargo;
    }

}