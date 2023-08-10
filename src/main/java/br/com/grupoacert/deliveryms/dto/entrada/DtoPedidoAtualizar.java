package br.com.grupoacert.deliveryms.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class DtoPedidoAtualizar {

    @Schema(description = "Descricao do pedido a ser atualizado", example = "Suco de laranja")
    @Size(min = 2, message = "A descricao deve conter ao menos 2 caracteres")
    @Null
    private String descricao;

    @Schema(description = "Valor do pedido a ser atualizado , o decimal deve ser separado por '.' e não por ',' ", example = "6.50")
    @Positive(message = "O valor deve ser positivo")
    @Null
    private Double valor;

}
