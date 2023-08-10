package br.com.grupoacert.deliveryms.contract;

import br.com.grupoacert.deliveryms.dto.entrada.DtoCliente;
import br.com.grupoacert.deliveryms.dto.entrada.DtoClienteAtualizar;
import br.com.grupoacert.deliveryms.dto.entrada.DtoClienteCpf;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoClienteCriado;
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

@Tag(name = "2. Cliente", description = "Gerenciamento de clientes.")
public interface ClienteContract {

    @Operation(summary = "Criar novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = DtoCliente.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = DtoCliente.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoCliente.class))),
    })
    ResponseEntity<DtoRetornoClienteCriado> criar(@RequestBody @Valid DtoCliente dtoCliente);

    @Operation(summary = "Atualizar cliente pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = DtoClienteAtualizar.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = DtoClienteAtualizar.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = DtoClienteAtualizar.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoClienteAtualizar.class))),
    })
    ResponseEntity<DtoRetornoClienteCriado> atualizar(@PathVariable Long id,
                                                      @RequestBody DtoClienteAtualizar dtoClienteAtualizar);

    @Operation(summary = "Encontrar cliente pelo cpf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = DtoClienteCpf.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = DtoClienteCpf.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = DtoClienteAtualizar.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoClienteCpf.class))),
    })
    ResponseEntity<DtoRetornoClienteCriado> encontrarPeloCpf(@RequestBody @Valid DtoClienteCpf dtoClienteCpf);

    @Operation(summary = "Listar todos os clientes cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = DtoRetornoClienteCriado.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = DtoRetornoClienteCriado.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = DtoClienteAtualizar.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoRetornoClienteCriado.class))),
    })
    ResponseEntity<List<DtoRetornoClienteCriado>> listarClientes();

    @Operation(summary = "Deletar cliente pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = DtoRetornoClienteCriado.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = DtoRetornoClienteCriado.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = DtoClienteAtualizar.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = DtoRetornoClienteCriado.class))),
    })
    ResponseEntity<Long> deletar(@PathVariable Long id);

}
