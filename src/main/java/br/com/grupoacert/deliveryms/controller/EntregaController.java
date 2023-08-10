package br.com.grupoacert.deliveryms.controller;

import br.com.grupoacert.deliveryms.contract.EntregaContract;
import br.com.grupoacert.deliveryms.dto.entrada.DtoEntrega;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoEntregaCriado;
import br.com.grupoacert.deliveryms.service.EntregaService;
import io.swagger.v3.oas.annotations.Operation;
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
public class EntregaController implements EntregaContract {

    private final EntregaService entregaService;

    @Operation(summary = "Criar nova entrega")
    @PostMapping("v1/entrega/criar/{idPedido}")
    public ResponseEntity<DtoRetornoEntregaCriado> criar(@PathVariable("idPedido") Long idPedido,
                                                         @RequestBody @Valid DtoEntrega dtoEntrega) {
        return new ResponseEntity<>(entregaService.criar(idPedido, dtoEntrega), HttpStatus.OK);
    }

    @Operation(summary = "Atualizar entrega pelo id")
    @PatchMapping("v1/entrega/atualizar/{id}")
    public ResponseEntity<DtoRetornoEntregaCriado> atualizar(@PathVariable Long id,
                                                             @RequestBody DtoEntrega dtoEntrega) {
        return new ResponseEntity<>(entregaService.atualizar(id, dtoEntrega), HttpStatus.OK);
    }

    @Operation(summary = "Encontrar entrega pelo id")
    @GetMapping("v1/entrega/encontrar/{id}")
    public ResponseEntity<DtoRetornoEntregaCriado> encontrarPeloId(@PathVariable Long id) {
        return new ResponseEntity<>(entregaService.encontrarPeloIdRetornaDto(id), HttpStatus.OK);
    }

    @Operation(summary = "Listar todos as entregas cadastradas")
    @GetMapping("v1/entrega/listar")
    public ResponseEntity<List<DtoRetornoEntregaCriado>> listarPedidos() {
        return new ResponseEntity<>(entregaService.listarEntregas(), HttpStatus.OK);
    }

    @Operation(summary = "Deletar entrega pelo id")
    @DeleteMapping("v1/entrega/deletar/{id}")
    public ResponseEntity<Long> deletar(@PathVariable Long id) {
        return new ResponseEntity<>(entregaService.deletar(id), HttpStatus.OK);
    }

}
