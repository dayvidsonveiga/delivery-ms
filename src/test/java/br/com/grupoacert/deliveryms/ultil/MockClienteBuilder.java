package br.com.grupoacert.deliveryms.ultil;

import br.com.grupoacert.deliveryms.domain.Cliente;
import br.com.grupoacert.deliveryms.dto.entrada.DtoCliente;
import br.com.grupoacert.deliveryms.dto.entrada.DtoClienteAtualizar;
import br.com.grupoacert.deliveryms.dto.entrada.DtoClienteCpf;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoClienteCriado;

public class MockClienteBuilder {

    public DtoCliente mockDtoCliente(String nome, String cpf, String email, String cep) {
        DtoCliente dtoCliente = new DtoCliente();
        dtoCliente.setNome(nome);
        dtoCliente.setCpf(cpf);
        dtoCliente.setEmail(email);
        dtoCliente.setCep(cep);
        return dtoCliente;
    }

    public DtoClienteAtualizar mockDtoClienteAtualizar(String nome, String cpf, String email, String cep) {
        DtoClienteAtualizar dtoClienteAtualizar = new DtoClienteAtualizar();
        dtoClienteAtualizar.setNome(nome);
        dtoClienteAtualizar.setCpf(cpf);
        dtoClienteAtualizar.setEmail(email);
        dtoClienteAtualizar.setCep(cep);
        return dtoClienteAtualizar;
    }

    public DtoClienteCpf mockDtoClienteCpf(String cpf) {
        DtoClienteCpf dtoClienteCpf = new DtoClienteCpf();
        dtoClienteCpf.setCpf(cpf);
        return dtoClienteCpf;
    }

    public Cliente mockCliente(Long id, String nome, String cpf, String email, String cep) {
        return Cliente.builder()
                .id(id)
                .nome(nome)
                .cpf(cpf)
                .email(email)
                .cep(cep)
                .build();
    }

    public DtoRetornoClienteCriado mockDtoRetornoClienteCriado(Long id, String nome, String cpf, String email, String cep) {
        return DtoRetornoClienteCriado.builder()
                .id(id)
                .nome(nome)
                .cpf(cpf)
                .email(email)
                .cep(cep)
                .build();
    }

}
