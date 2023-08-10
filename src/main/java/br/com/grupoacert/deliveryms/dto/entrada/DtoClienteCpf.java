package br.com.grupoacert.deliveryms.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;

@Data
public class DtoClienteCpf {

    @Schema(description = "Número do cpf válido, somente números", example = "32976406006", required = true)
    @CPF(message = "CPF inválido")
    @NotBlank(message = "O cpf deve ser preenchido.")
    private String cpf;

}
