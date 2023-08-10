package br.com.grupoacert.deliveryms.controller;

import br.com.grupoacert.deliveryms.contract.UsuarioContract;
import br.com.grupoacert.deliveryms.dto.entrada.DtoUsuario;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoUsuarioCriado;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoUsuarioLogado;
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
public class UsuarioController implements UsuarioContract {

    private final UsuarioService usuarioService;
    private final LoginService loginService;


    @Operation(summary = "Criar novo usuário")
    @PostMapping("v1/usuario/criar")
    public ResponseEntity<DtoRetornoUsuarioCriado> criar(@RequestBody @Valid DtoUsuario dtoUsuario) {
        return new ResponseEntity<>(usuarioService.criar(dtoUsuario), HttpStatus.OK);
    }

    @Operation(summary = "Logar no sistema")
    @PostMapping("v1/usuario/login")
    public ResponseEntity<DtoRetornoUsuarioLogado> login(@RequestBody @Valid DtoUsuario dtoUsuario) {
        return new ResponseEntity<>(loginService.login(dtoUsuario), HttpStatus.OK);
    }

    @Operation(summary = "Listar todos os usuários cadastrados")
    @GetMapping("v1/usuario/listar")
    public ResponseEntity<List<DtoRetornoUsuarioCriado>> listarUsuarios() {
        return new ResponseEntity<>(usuarioService.listarUsuarios(), HttpStatus.OK);
    }

}
