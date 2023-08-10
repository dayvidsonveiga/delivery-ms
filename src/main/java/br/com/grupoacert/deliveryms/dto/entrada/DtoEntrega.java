package br.com.grupoacert.deliveryms.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class DtoEntrega {

    @Schema(description = "Nome do entregador", example = "Dayvidson Veiga", required = true)
    @NotBlank(message = "O nome deve ser preenchido")
    @Size(min = 2, message = "O nome deve conter ao menos 2 caracteres")
    private String nomeEntregador;

}
