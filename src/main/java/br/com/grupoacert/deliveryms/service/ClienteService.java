package br.com.grupoacert.deliveryms.service;

import br.com.grupoacert.deliveryms.domain.Cliente;
import br.com.grupoacert.deliveryms.dto.entrada.DtoCliente;
import br.com.grupoacert.deliveryms.dto.entrada.DtoClienteAtualizar;
import br.com.grupoacert.deliveryms.dto.entrada.DtoClienteCpf;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoClienteCriado;
import br.com.grupoacert.deliveryms.exception.CadastroDuplicadoException;
import br.com.grupoacert.deliveryms.exception.CadastroNaoEncontradoException;
import br.com.grupoacert.deliveryms.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    public DtoRetornoClienteCriado atualizar(Long id, DtoClienteAtualizar dtoCliente) {

        log.info("Buscando cliente com id {}", id);

        Cliente cliente = encontrarPeloId(id);

        cliente.setNome(Objects.nonNull(dtoCliente.getNome()) ? dtoCliente.getNome() : cliente.getNome());
        cliente.setCpf(Objects.nonNull(dtoCliente.getCpf()) ? dtoCliente.getCpf() : cliente.getCpf());
        cliente.setEmail(Objects.nonNull(dtoCliente.getEmail()) ? dtoCliente.getEmail() : cliente.getEmail());
        cliente.setCep(Objects.nonNull(dtoCliente.getCep()) ? dtoCliente.getCpf() : cliente.getCep());

        log.info("Cliente com id {} atualizado", id);

        return domainParaDtoRetornoClienteCriado(salvar(cliente));

    }

    public Long deletar(Long id) {

        log.info("Buscando cliente...");

        Cliente cliente = encontrarPeloId(id);

        clienteRepository.delete(cliente);

        log.info("Cliente {} deletado", cliente.getNome());

        return id;

    }

    public DtoRetornoClienteCriado encontrarPeloCpf(DtoClienteCpf dtoClienteCpf) {

        log.info("Procurando cliente pelo cpf {} ", dtoClienteCpf.getCpf());

        Optional<Cliente> cliente = clienteRepository.findByCpfEqualsIgnoreCase(dtoClienteCpf.getCpf());

        if (!cliente.isPresent()) {

            String msg = "Não há cliente cadastrado com esse cpf";
            logService.salvar(msg);
            log.error(msg);
            throw new CadastroNaoEncontradoException(msg);

        }

        log.info("Cliente {} listado", cliente.get().getNome());

        return domainParaDtoRetornoClienteCriado(cliente.get());

    }

    public Cliente encontrarPeloId(Long id) {

        log.info("Procurando cliente pelo id {} ", id);

        Optional<Cliente> cliente = clienteRepository.findById(id);

        if (!cliente.isPresent()) {

            String msg = "Não há cliente cadastrado com esse id";
            logService.salvar(msg);
            log.error(msg);
            throw new CadastroNaoEncontradoException(msg);

        }

        log.info("Cliente {} listado", cliente.get().getNome());

        return cliente.get();

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

    public DtoRetornoClienteCriado domainParaDtoRetornoClienteCriado(Cliente cliente) {

        return DtoRetornoClienteCriado.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .email(cliente.getEmail())
                .cep(cliente.getCep())
                .build();

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

    private Cliente dtoClienteParaDomain(DtoCliente dtoCliente) {

        return Cliente.builder()
                .nome(dtoCliente.getNome())
                .cpf(dtoCliente.getCpf())
                .email(dtoCliente.getEmail())
                .cep(dtoCliente.getCep())
                .build();

    }

}
