package br.com.grupoacert.deliveryms.service;

import br.com.grupoacert.deliveryms.domain.Usuario;
import br.com.grupoacert.deliveryms.dto.entrada.DtoUsuario;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoUsuarioLogado;
import br.com.grupoacert.deliveryms.exception.RegraDeNegocioException;
import br.com.grupoacert.deliveryms.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public DtoRetornoUsuarioLogado login(DtoUsuario dtoUsuario) throws RegraDeNegocioException {

        Usuario usuario = usuarioService.findByEmail(dtoUsuario.getEmail());

        if (usuarioService.checkPasswordIsCorrect(dtoUsuario.getPass(), usuario.getPassword())) {

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            dtoUsuario.getEmail(),
                            dtoUsuario.getPass()
                    );

            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            String token = tokenService.getToken((Usuario) authentication.getPrincipal());

            return usuarioService.logar(dtoUsuario, token);

        } else {
            throw new RegraDeNegocioException("Email ou senha incorreta!");
        }

    }

}
