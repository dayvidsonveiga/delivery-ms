package br.com.grupoacert.deliveryms.security;

import br.com.grupoacert.deliveryms.domain.Usuario;
import br.com.grupoacert.deliveryms.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Usuario> usuarioOptional = usuarioService.findByEmailOptional(email);

        return usuarioOptional
                .orElseThrow(() -> new UsernameNotFoundException("Usuario inválido"));

    }

}
