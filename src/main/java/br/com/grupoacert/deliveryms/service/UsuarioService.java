package br.com.grupoacert.deliveryms.service;

import br.com.grupoacert.deliveryms.domain.Cargo;
import br.com.grupoacert.deliveryms.domain.Usuario;
import br.com.grupoacert.deliveryms.dto.entrada.DtoUsuario;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoUsuarioCriado;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoUsuarioLogado;
import br.com.grupoacert.deliveryms.exception.RegraDeNegocioException;
import br.com.grupoacert.deliveryms.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final CargoService cargoService;

    public DtoRetornoUsuarioCriado criar(DtoUsuario dto) throws RegraDeNegocioException {

        checkEmailExist(dto.getEmail());

        Usuario usuario = DtocriarUsuarioParaDomain(dto);
        Cargo cargo = cargoService.encontrarPeloNomeDoCargo("CARGO_ADM");
        cargo.getUsuarios().add(usuario);
        usuario.setCargo(cargo);

        return domainParaDtoRetornoUsuarioCriado(usuarioRepository.save(usuario));

    }

    public DtoRetornoUsuarioLogado logar(DtoUsuario dtoUsuario, String token) throws RegraDeNegocioException {

        Usuario usuario = findByEmail(dtoUsuario.getEmail());

        DtoRetornoUsuarioLogado dtoRetorno = new DtoRetornoUsuarioLogado();
        dtoRetorno.setId(usuario.getId());
        dtoRetorno.setEmail(usuario.getEmail());
        dtoRetorno.setToken(token);

        return dtoRetorno;

    }

    public List<DtoRetornoUsuarioCriado> listarUsuarios() throws RegraDeNegocioException {

        if (!usuarioRepository.findAll().isEmpty()) {

            return usuarioRepository.findAll().stream()
                    .map(this::domainParaDtoRetornoUsuarioCriado).collect(Collectors.toList());

        } else {

            throw new RegraDeNegocioException("Não há usuários a serem listados!");

        }

    }

    public Optional<Usuario> findByEmailOptional(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario findByEmail(String email) throws RegraDeNegocioException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RegraDeNegocioException("Email não registrado!"));
    }

    public Usuario findById(Integer id) throws RegraDeNegocioException {
        return usuarioRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado!"));
    }

    public Boolean checkPasswordIsCorrect(String passwordInput, String passwordDB) {

        if (passwordEncoder.matches(passwordInput, passwordDB)) {
            return true;
        } else {
            return false;
        }

    }

    private void checkEmailExist(String email) throws RegraDeNegocioException {
        if (findByEmailOptional(email).isPresent()) {
            throw new RegraDeNegocioException("Email já está sendo utilizado!");
        }
    }

    private DtoRetornoUsuarioCriado domainParaDtoRetornoUsuarioCriado(Usuario usuario) {

        DtoRetornoUsuarioCriado dtoRetorno = new DtoRetornoUsuarioCriado();
        dtoRetorno.setId(usuario.getId());
        dtoRetorno.setEmail(usuario.getEmail());

        return dtoRetorno;

    }

    private Usuario DtocriarUsuarioParaDomain(DtoUsuario dto) {

        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getPass()));

        return usuario;

    }

}
