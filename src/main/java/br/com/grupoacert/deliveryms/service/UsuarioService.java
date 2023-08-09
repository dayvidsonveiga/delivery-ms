package br.com.grupoacert.deliveryms.service;

import br.com.grupoacert.deliveryms.domain.Cargo;
import br.com.grupoacert.deliveryms.domain.Usuario;
import br.com.grupoacert.deliveryms.dto.entrada.DtoUsuario;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoUsuarioCriado;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoUsuarioLogado;
import br.com.grupoacert.deliveryms.exception.CadastroDuplicadoException;
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
    private final LogService logService;

    public DtoRetornoUsuarioCriado criar(DtoUsuario dto) throws RegraDeNegocioException {

        log.info("Criando usuario...");

        checkEmailExist(dto.getEmail());

        Usuario usuario = dtoUsuarioParaDomain(dto);
        Cargo cargo = cargoService.encontrarPeloNomeDoCargo("CARGO_ADM");
        cargo.getUsuarios().add(usuario);
        usuario.setCargo(cargo);

        log.info("Usuario {} criado com sucesso", dto.getEmail());

        return domainParaDtoRetornoUsuarioCriado(usuarioRepository.save(usuario));

    }

    public DtoRetornoUsuarioLogado logar(DtoUsuario dtoUsuario, String token) throws RegraDeNegocioException {

        Usuario usuario = findByEmail(dtoUsuario.getEmail());

        return DtoRetornoUsuarioLogado.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .token(token)
                .build();

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

    private void checkEmailExist(String email) {
        log.info("Verificando se email {} já existe na base", email);

        if (findByEmailOptional(email).isPresent()) {

            String msg = "Email já está sendo utilizado!";
            log.error(msg);
            logService.salvar(msg);
            throw new CadastroDuplicadoException(msg);

        }

    }

    private DtoRetornoUsuarioCriado domainParaDtoRetornoUsuarioCriado(Usuario usuario) {

        return DtoRetornoUsuarioCriado.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .build();

    }

    private Usuario dtoUsuarioParaDomain(DtoUsuario dto) {

        return Usuario.builder()
                .email(dto.getEmail())
                .senha(passwordEncoder.encode(dto.getPass()))
                .build();

    }

}
