package br.com.grupoacert.deliveryms.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class DtoCliente {

    @Schema(description = "Nome do associado a ser cadastrado", example = "Dayvidson Veiga", required = true)
    @NotBlank
    @Size(min = 2, message = "O nome deve conter ao menos 2 caracteres")
    private String nome;

    @Schema(description = "Número do cpf válido, somente números", example = "32976406006", required = true)
    @CPF(message = "CPF inválido")
    @NotBlank(message = "O cpf deve ser preenchido.")
    private String cpf;

    @Schema(example = "nome@gmail.com")
    @NotBlank(message = "O campo de email não pode ser vazio/nulo.")
    @Email(message = "Deve ser informado um e-mail válido.")
    private String email;

    @Schema(description = "Cep do cliente, somente números", example = "55012484")
    @Pattern(regexp = "^(([0-9]{2}\\.[0-9]{3}-[0-9]{3})|([0-9]{2}[0-9]{3}-[0-9]{3})|([0-9]{8}))$", message = "O cep deve conter apénas números.")
    @NotBlank(message = "O cep deve ser preenchido.")
    @Size(min = 8, max = 8, message = "O cep deve 8 digitos, apenas números.")
    private String cep;

}
