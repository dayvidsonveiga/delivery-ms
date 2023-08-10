package br.com.grupoacert.deliveryms.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class DtoPedido {

    @Schema(description = "Descricao do pedido a ser cadastrado", example = "Suco de laranja", required = true)
    @NotBlank(message = "A descricao deve ser preenchida")
    @Size(min = 2, message = "A descricao deve conter ao menos 2 caracteres")
    private String descricao;

    @Schema(description = "Valor do pedido a ser cadastrado, o decimal deve ser separado por '.' e não por ',' ", example = "6.50", required = true)
    @NotNull
    @Positive(message = "O valor deve ser positivo")
    private Double valor;

}
