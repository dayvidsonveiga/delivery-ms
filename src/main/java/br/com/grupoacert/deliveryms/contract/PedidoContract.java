package br.com.grupoacert.deliveryms.contract;

import br.com.grupoacert.deliveryms.dto.entrada.DtoPedido;
import br.com.grupoacert.deliveryms.dto.entrada.DtoPedidoAtualizar;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoPedidoCriado;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "3. Pedido", description = "Gerenciamento de pedidos.")
public interface PedidoContract {

    @Operation(summary = "Criar novo pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = DtoPedido.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = DtoPedido.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoPedido.class))),
    })
    ResponseEntity<DtoRetornoPedidoCriado> criar(@PathVariable("idCliente") Long idCliente,
                                                 @RequestBody @Valid DtoPedido dtoPedido);

    @Operation(summary = "Atualizar pedido pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = DtoPedidoAtualizar.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = DtoPedidoAtualizar.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = DtoPedidoAtualizar.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoPedidoAtualizar.class))),
    })
    ResponseEntity<DtoRetornoPedidoCriado> atualizar(@PathVariable Long id,
                                                     @RequestBody DtoPedidoAtualizar dtoPedidoAtualizar);

    @Operation(summary = "Encontrar pedido pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = DtoPedido.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = DtoPedido.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = DtoPedido.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoPedido.class))),
    })
    ResponseEntity<DtoRetornoPedidoCriado> encontrarPeloId(@PathVariable Long id);

    @Operation(summary = "Listar todos os pedidos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = DtoPedido.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = DtoPedido.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = DtoPedido.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoPedido.class))),
    })
    ResponseEntity<List<DtoRetornoPedidoCriado>> listarPedidos();

    @Operation(summary = "Deletar pedido pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = DtoPedido.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = DtoPedido.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = DtoPedido.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoPedido.class))),
    })
    ResponseEntity<Long> deletar(@PathVariable Long id);

}
