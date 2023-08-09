package br.com.grupoacert.deliveryms.dto.retorno;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DtoRetornoUsuarioCriado {

    @Schema(description = "Id do Usuario")
    private Long id;

    @Schema(description = "Email do Usuario")
    private String email;

}
