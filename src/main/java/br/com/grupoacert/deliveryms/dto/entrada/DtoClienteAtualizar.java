package br.com.grupoacert.deliveryms.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class DtoClienteAtualizar {

    @Schema(description = "Nome do cliente a ser atualizado", example = "Dayvidson Veiga")
    @Size(min = 2, message = "O nome deve conter ao menos 2 caracteres")
    @Null
    private String nome;

    @Schema(description = "Número do cpf válido, somente números", example = "32976406006")
    @CPF(message = "CPF inválido")
    @Null
    private String cpf;

    @Schema(description = "Email do cliente a ser atualizado", example = "nome@gmail.com")
    @Email(message = "Deve ser informado um e-mail válido")
    @Null
    private String email;

    @Schema(description = "Cep do cliente a ser atualizado, somente números", example = "55012484")
    @Pattern(regexp = "^(([0-9]{2}\\.[0-9]{3}-[0-9]{3})|([0-9]{2}[0-9]{3}-[0-9]{3})|([0-9]{8}))$", message = "O cep deve conter apénas números")
    @Size(min = 8, max = 8, message = "O cep deve 8 digitos, apenas números")
    @Null
    private String cep;

}
