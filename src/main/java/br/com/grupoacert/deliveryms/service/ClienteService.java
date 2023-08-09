package br.com.grupoacert.deliveryms.service;

import br.com.grupoacert.deliveryms.domain.Cliente;
import br.com.grupoacert.deliveryms.dto.entrada.DtoCliente;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoClienteCriado;
import br.com.grupoacert.deliveryms.exception.CadastroDuplicadoException;
import br.com.grupoacert.deliveryms.exception.CadastroNaoEncontradoException;
import br.com.grupoacert.deliveryms.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final LogService logService;

    public DtoRetornoClienteCriado criar(DtoCliente dtoCliente) {

        log.info("Criando cliente {}...", dtoCliente.getNome());

        verificarSeClienteJaCadastrado(dtoCliente);

        Cliente cliente = dtoClienteParaDomain(dtoCliente);

        log.info("Cliente {} criado com sucesso", dtoCliente.getNome());

        return domainParaDtoRetornoClienteCriado(salvar(cliente));

    }

    public List<DtoRetornoClienteCriado> listarClientes() {

        log.info("Listando todos os clientes...");

        List<Cliente> listaClientes = clienteRepository.findAll();

        if (listaClientes.isEmpty()) {

            String msg = "Não há clientes cadastrados";
            logService.salvar(msg);
            log.error(msg);
            throw new CadastroNaoEncontradoException(msg);

        } else {
            log.info("Clientes listados");
            return listaClientes.stream()
                    .map(this::domainParaDtoRetornoClienteCriado).collect(Collectors.toList());
        }

    }

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    private void verificarSeClienteJaCadastrado(DtoCliente dtoCliente) {

        log.info("Verificando se {} já esta cadastrado...", dtoCliente.getCpf());

        clienteRepository.findByCpfEqualsIgnoreCase(dtoCliente.getCpf())
                .ifPresent(cliente -> {
                    String msg = "O cliente de cpf " + cliente.getCpf() + " já esta cadastrado.";
                    log.error(msg);
                    logService.salvar(msg);
                    throw new CadastroDuplicadoException(msg);
                });

    }

    private DtoRetornoClienteCriado domainParaDtoRetornoClienteCriado(Cliente cliente) {

        return DtoRetornoClienteCriado.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .email(cliente.getEmail())
                .cep(cliente.getCep())
                .build();

    }

    private Cliente dtoClienteParaDomain(DtoCliente dtoCliente) {

        return Cliente.builder()
                .nome(dtoCliente.getNome())
                .cpf(dtoCliente.getCpf())
                .email(dtoCliente.getEmail())
                .cep(dtoCliente.getCep())
                .build();

    }


}
