package br.com.grupoacert.deliveryms.dto.retorno;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class DtoRetornoEntregaCriado {

    @Schema(description = "Id da entrega")
    private Long id;

    @Schema(description = "Nome do entregador")
    private String nomeEntregador;

    @Schema(description = "Data entrega criada")
    private LocalDateTime dataEntrega;

    @Schema(description = "Pedidos criado para entrega")
    private List<DtoRetornoPedidoCriado> pedidoCriados;

}
