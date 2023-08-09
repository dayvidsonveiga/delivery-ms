package br.com.grupoacert.deliveryms.dto.retorno;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoRetornoClienteCriado {

    @Schema(description = "Id do cliente")
    private Long id;

    @Schema(description = "Nome do cliente cadastrado")
    private String nome;

    @Schema(description = "CPF do cliente cadastrado")
    private String cpf;

    @Schema(description = "Email do cliente cadastrado")
    private String email;

    @Schema(description = "CEP do cliente cadastrado")
    private String cep;

}
