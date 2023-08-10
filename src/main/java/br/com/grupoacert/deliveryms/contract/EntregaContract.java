package br.com.grupoacert.deliveryms.contract;

import br.com.grupoacert.deliveryms.dto.entrada.DtoEntrega;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoEntregaCriado;
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

@Tag(name = "4. Entrega", description = "Gerenciamento de entregas.")
public interface EntregaContract {

    @Operation(summary = "Criar nova entrega")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
    })
    ResponseEntity<DtoRetornoEntregaCriado> criar(@PathVariable("idPedido") Long idPedido,
                                                  @RequestBody @Valid DtoEntrega dtoEntrega);

    @Operation(summary = "Atualizar entrega pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
    })
    ResponseEntity<DtoRetornoEntregaCriado> atualizar(@PathVariable Long id,
                                                      @RequestBody DtoEntrega dtoEntrega);

    @Operation(summary = "Encontrar entrega pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
    })
    ResponseEntity<DtoRetornoEntregaCriado> encontrarPeloId(@PathVariable Long id);

    @Operation(summary = "Listar todos as entregas cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
    })
    ResponseEntity<List<DtoRetornoEntregaCriado>> listarPedidos();

    @Operation(summary = "Deletar entrega pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoEntrega.class))),
    })
    ResponseEntity<Long> deletar(@PathVariable Long id);

}
