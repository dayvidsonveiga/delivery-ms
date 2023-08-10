package br.com.grupoacert.deliveryms.service;

import br.com.grupoacert.deliveryms.domain.Cliente;
import br.com.grupoacert.deliveryms.domain.Pedido;
import br.com.grupoacert.deliveryms.dto.entrada.DtoPedido;
import br.com.grupoacert.deliveryms.dto.entrada.DtoPedidoAtualizar;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoPedidoCriado;
import br.com.grupoacert.deliveryms.exception.CadastroNaoEncontradoException;
import br.com.grupoacert.deliveryms.repository.PedidoRepository;
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
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;
    private final LogService logService;


    public DtoRetornoPedidoCriado criar(Long idCliente, DtoPedido dtoPedido) {

        log.info("Criando pedido {}...", dtoPedido.getDescricao());

        Cliente cliente = clienteService.encontrarPeloId(idCliente);

        Pedido pedido = dtoPedidoParaDomain(dtoPedido, cliente);

        log.info("Pedido {} criado com sucesso", dtoPedido.getDescricao());

        return domainParaDtoRetornoPedidoCriado(salvar(pedido));

    }

    public DtoRetornoPedidoCriado atualizar(Long id, DtoPedidoAtualizar dtoPedidoAtualizar) {

        log.info("Buscando cliente com id {}", id);

        Pedido pedido = encontrarPeloId(id);

        pedido.setDescricao(Objects.nonNull(dtoPedidoAtualizar.getDescricao()) ? dtoPedidoAtualizar.getDescricao() : pedido.getDescricao());
        pedido.setValor(Objects.nonNull(dtoPedidoAtualizar.getValor()) ? dtoPedidoAtualizar.getValor() : pedido.getValor());

        log.info("Cliente com id {} atualizado", id);

        return domainParaDtoRetornoPedidoCriado(salvar(pedido));

    }

    public Long deletar(Long id) {

        log.info("Buscando pedido...");

        Pedido pedido = encontrarPeloId(id);

        pedidoRepository.delete(pedido);

        log.info("Pedido {} deletado", pedido.getDescricao());

        return id;

    }

    public DtoRetornoPedidoCriado encontrarPeloIdRetornaDto(Long id) {
        return domainParaDtoRetornoPedidoCriado(encontrarPeloId(id));
    }

    public Pedido encontrarPeloId(Long id) {

        log.info("Procurando pedido pelo id {} ", id);

        Optional<Pedido> pedido = pedidoRepository.findById(id);

        if (!pedido.isPresent()) {

            String msg = "Não há pedido cadastrado com esse id";
            logService.salvar(msg);
            log.error(msg);
            throw new CadastroNaoEncontradoException(msg);

        }

        log.info("Pedido {} listado", pedido.get().getDescricao());

        return pedido.get();

    }

    public List<DtoRetornoPedidoCriado> listarPedidos() {

        log.info("Listando todos os pedidos...");

        List<Pedido> listaPedidos = pedidoRepository.findAll();

        if (listaPedidos.isEmpty()) {

            String msg = "Não há pedidos cadastrados";
            logService.salvar(msg);
            log.error(msg);
            throw new CadastroNaoEncontradoException(msg);

        } else {
            log.info("Pedidos listados");
            return listaPedidos.stream()
                    .map(this::domainParaDtoRetornoPedidoCriado).collect(Collectors.toList());
        }

    }

    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public DtoRetornoPedidoCriado domainParaDtoRetornoPedidoCriado(Pedido pedido) {

        return DtoRetornoPedidoCriado.builder()
                .id(pedido.getId())
                .descricao(pedido.getDescricao())
                .valor(pedido.getValor())
                .cliente(clienteService.domainParaDtoRetornoClienteCriado(pedido.getCliente()))
                .build();

    }

    private Pedido dtoPedidoParaDomain(DtoPedido dtoPedido, Cliente cliente) {

        return Pedido.builder()
                .descricao(dtoPedido.getDescricao())
                .valor(dtoPedido.getValor())
                .cliente(cliente)
                .build();

    }

}
