package br.com.grupoacert.deliveryms.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class DtoUsuario {

    @Schema(description = "Email do usuario cadastrado", example = "nome@gmail.com", required = true)
    @NotBlank(message = "O campo de email não pode ser vazio/nulo.")
    @Email(message = "Deve ser informado um e-mail válido.")
    private String email;

    @Schema(description = "Senha do usuario cadastrado", example = "12345", required = true)
    @NotBlank(message = "O campo de password não pode ser vazio/nulo.")
    @Size(min = 5, max = 20)
    private String pass;

}
