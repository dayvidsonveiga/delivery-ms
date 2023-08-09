package br.com.grupoacert.deliveryms.dto.retorno;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DtoRetornoUsuarioLogado {

    @Schema(description = "Id do Usuario")
    private Long id;

    @Schema(description = "Email do Usuario")
    private String email;

    @Schema(description = "Token do Usuario")
    private String token;

}
