package br.com.grupoacert.deliveryms.controller;

import br.com.grupoacert.deliveryms.dto.entrada.DtoUsuario;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoUsuarioCriado;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoUsuarioLogado;
import br.com.grupoacert.deliveryms.exception.RegraDeNegocioException;
import br.com.grupoacert.deliveryms.service.LoginService;
import br.com.grupoacert.deliveryms.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final LoginService loginService;


    @Operation(summary = "Criar novo usuário")
    @PostMapping("/criar")
    public ResponseEntity<DtoRetornoUsuarioCriado> create(@RequestBody @Valid DtoUsuario dtoUsuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.criar(dtoUsuario), HttpStatus.OK);
    }

    @Operation(summary = "Logar no sistema")
    @PostMapping("/login")
    public ResponseEntity<DtoRetornoUsuarioLogado> login(@RequestBody @Valid DtoUsuario dtoUsuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(loginService.login(dtoUsuario), HttpStatus.OK);
    }

    @Operation(summary = "Listar todos os usuários cadastrados")
    @GetMapping("/listar")
    public ResponseEntity<List<DtoRetornoUsuarioCriado>> listAll() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.listarUsuarios(), HttpStatus.OK);
    }

}