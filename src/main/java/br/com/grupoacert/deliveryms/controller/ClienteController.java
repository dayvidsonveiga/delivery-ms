package br.com.grupoacert.deliveryms.controller;

import br.com.grupoacert.deliveryms.contract.ClienteContract;
import br.com.grupoacert.deliveryms.dto.entrada.DtoCliente;
import br.com.grupoacert.deliveryms.dto.entrada.DtoClienteAtualizar;
import br.com.grupoacert.deliveryms.dto.entrada.DtoClienteCpf;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoClienteCriado;
import br.com.grupoacert.deliveryms.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class ClienteController implements ClienteContract {

    private final ClienteService clienteService;

    @PostMapping("v1/cliente/criar")
    public ResponseEntity<DtoRetornoClienteCriado> criar(@RequestBody @Valid DtoCliente dtoCliente) {
        return new ResponseEntity<>(clienteService.criar(dtoCliente), HttpStatus.OK);
    }

    @PatchMapping("v1/cliente/atualizar/{id}")
    public ResponseEntity<DtoRetornoClienteCriado> atualizar(@PathVariable Long id,
                                                             @RequestBody DtoClienteAtualizar dtoClienteAtualizar) {
        return new ResponseEntity<>(clienteService.atualizar(id, dtoClienteAtualizar), HttpStatus.OK);
    }

    @PostMapping("v1/cliente/encontrar-cpf")
    public ResponseEntity<DtoRetornoClienteCriado> encontrarPeloCpf(@RequestBody @Valid DtoClienteCpf dtoClienteCpf) {
        return new ResponseEntity<>(clienteService.encontrarPeloCpf(dtoClienteCpf), HttpStatus.OK);
    }

    @GetMapping("v1/cliente/listar")
    public ResponseEntity<List<DtoRetornoClienteCriado>> listarClientes() {
        return new ResponseEntity<>(clienteService.listarClientes(), HttpStatus.OK);
    }

    @DeleteMapping("v1/cliente/deletar/{id}")
    public ResponseEntity<Long> deletar(@PathVariable Long id) {
        return new ResponseEntity<>(clienteService.deletar(id), HttpStatus.OK);
    }

}
