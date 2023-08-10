package br.com.grupoacert.deliveryms.controller;

import br.com.grupoacert.deliveryms.contract.PedidoContract;
import br.com.grupoacert.deliveryms.dto.entrada.DtoPedido;
import br.com.grupoacert.deliveryms.dto.entrada.DtoPedidoAtualizar;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoPedidoCriado;
import br.com.grupoacert.deliveryms.service.PedidoService;
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
public class PedidoController implements PedidoContract {

    private final PedidoService pedidoService;

    @Operation(summary = "Criar novo pedido")
    @PostMapping("v1/pedido/criar/{idCliente}")
    public ResponseEntity<DtoRetornoPedidoCriado> criar(@PathVariable("idCliente") Long idCliente,
                                                        @RequestBody @Valid DtoPedido dtoPedido) {
        return new ResponseEntity<>(pedidoService.criar(idCliente, dtoPedido), HttpStatus.OK);
    }

    @Operation(summary = "Atualizar pedido pelo id")
    @PatchMapping("v1/pedido/atualizar/{id}")
    public ResponseEntity<DtoRetornoPedidoCriado> atualizar(@PathVariable Long id,
                                                            @RequestBody DtoPedidoAtualizar dtoPedidoAtualizar) {
        return new ResponseEntity<>(pedidoService.atualizar(id, dtoPedidoAtualizar), HttpStatus.OK);
    }

    @Operation(summary = "Encontrar pedido pelo id")
    @GetMapping("v1/pedido/encontrar/{id}")
    public ResponseEntity<DtoRetornoPedidoCriado> encontrarPeloId(@PathVariable Long id) {
        return new ResponseEntity<>(pedidoService.encontrarPeloIdRetornaDto(id), HttpStatus.OK);
    }

    @Operation(summary = "Listar todos os pedidos cadastrados")
    @GetMapping("v1/pedido/listar")
    public ResponseEntity<List<DtoRetornoPedidoCriado>> listarPedidos() {
        return new ResponseEntity<>(pedidoService.listarPedidos(), HttpStatus.OK);
    }

    @Operation(summary = "Deletar pedido pelo id")
    @DeleteMapping("v1/pedido/deletar/{id}")
    public ResponseEntity<Long> deletar(@PathVariable Long id) {
        return new ResponseEntity<>(pedidoService.deletar(id), HttpStatus.OK);
    }

}
