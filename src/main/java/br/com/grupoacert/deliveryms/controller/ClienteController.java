package br.com.grupoacert.deliveryms.controller;

import br.com.grupoacert.deliveryms.dto.entrada.DtoCliente;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoClienteCriado;
import br.com.grupoacert.deliveryms.exception.RegraDeNegocioException;
import br.com.grupoacert.deliveryms.service.ClienteService;
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
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Criar novo cliente")
    @PostMapping("v1/cliente/criar")
    public ResponseEntity<DtoRetornoClienteCriado> criar(@RequestBody @Valid DtoCliente dtoCliente) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.criar(dtoCliente), HttpStatus.OK);
    }

    @Operation(summary = "Listar todos os clientes cadastrados")
    @GetMapping("v1/cliente/listar")
    public ResponseEntity<List<DtoRetornoClienteCriado>> listarClientes() throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.listarClientes(), HttpStatus.OK);
    }

}
