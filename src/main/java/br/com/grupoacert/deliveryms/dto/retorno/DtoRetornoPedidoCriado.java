package br.com.grupoacert.deliveryms.dto.retorno;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoRetornoPedidoCriado {

    @Schema(description = "Id do pedido")
    private Long id;

    @Schema(description = "Descricao do pedido cadastrado")
    private String descricao;

    @Schema(description = "Valor do pedido cadastrado")
    private Double valor;

    @Schema(description = "Cliente que realizou o pedido cadastrado")
    private DtoRetornoClienteCriado cliente;

}
