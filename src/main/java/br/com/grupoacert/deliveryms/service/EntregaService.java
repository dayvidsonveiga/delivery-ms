package br.com.grupoacert.deliveryms.service;

import br.com.grupoacert.deliveryms.domain.Entrega;
import br.com.grupoacert.deliveryms.domain.Pedido;
import br.com.grupoacert.deliveryms.dto.entrada.DtoEntrega;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoEntregaCriado;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoPedidoCriado;
import br.com.grupoacert.deliveryms.exception.CadastroNaoEncontradoException;
import br.com.grupoacert.deliveryms.repository.EntregaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class EntregaService {

    private final EntregaRepository entregaRepository;
    private final PedidoService pedidoService;
    private final LogService logService;


    public DtoRetornoEntregaCriado criar(Long idPedido, DtoEntrega dtoEntrega) {

        log.info("Criando entrega para o entregador {}...", dtoEntrega.getNomeEntregador());

        Pedido pedido = pedidoService.encontrarPeloId(idPedido);

        Entrega entrega = dtoEntregaParaDomain(dtoEntrega, pedido);

        log.info("Entrega para o entregador {} criada com sucesso", dtoEntrega.getNomeEntregador());

        return domainParaDtoRetornoEntregaCriado(salvar(entrega));

    }

    public DtoRetornoEntregaCriado atualizar(Long id, DtoEntrega dtoEntrega) {

        log.info("Buscando entrega com id {}", id);

        Entrega entrega = encontrarPeloId(id);

        entrega.setNomeEntregador(Objects.nonNull(dtoEntrega.getNomeEntregador()) ? dtoEntrega.getNomeEntregador() : entrega.getNomeEntregador());

        log.info("Entrega com id {} atualizada", id);

        return domainParaDtoRetornoEntregaCriado(salvar(entrega));

    }

    public Long deletar(Long id) {

        log.info("Buscando entrega...");

        Entrega entrega = encontrarPeloId(id);

        entregaRepository.delete(entrega);

        log.info("Entrega do entregador {} deletada", entrega.getNomeEntregador());

        return id;

    }

    public DtoRetornoEntregaCriado encontrarPeloIdRetornaDto(Long id) {
        return domainParaDtoRetornoEntregaCriado(encontrarPeloId(id));
    }

    public Entrega encontrarPeloId(Long id) {

        log.info("Procurando entrega pelo id {} ", id);

        Optional<Entrega> entrega = entregaRepository.findById(id);

        if (!entrega.isPresent()) {

            String msg = "Não há entrega cadastrada com esse id";
            logService.salvar(msg);
            log.error(msg);
            throw new CadastroNaoEncontradoException(msg);

        }

        log.info("Entrega do entregador {} listado", entrega.get().getNomeEntregador());

        return entrega.get();

    }

    public List<DtoRetornoEntregaCriado> listarEntregas() {

        log.info("Listando todas as entregas...");

        List<Entrega> listaEntregas = entregaRepository.findAll();

        if (listaEntregas.isEmpty()) {

            String msg = "Não há entregas cadastradas";
            logService.salvar(msg);
            log.error(msg);
            throw new CadastroNaoEncontradoException(msg);

        } else {
            log.info("Entregas listadas");
            return listaEntregas.stream()
                    .map(this::domainParaDtoRetornoEntregaCriado).collect(Collectors.toList());
        }

    }

    public Entrega salvar(Entrega entrega) {
        return entregaRepository.save(entrega);
    }

    private DtoRetornoEntregaCriado domainParaDtoRetornoEntregaCriado(Entrega entrega) {

        List<DtoRetornoPedidoCriado> pedidoCriados = entrega.getPedidos().stream()
                .map(pedidoService::domainParaDtoRetornoPedidoCriado)
                .collect(Collectors.toList());

        return DtoRetornoEntregaCriado.builder()
                .id(entrega.getId())
                .nomeEntregador(entrega.getNomeEntregador())
                .pedidoCriados(pedidoCriados)
                .build();

    }

    private Entrega dtoEntregaParaDomain(DtoEntrega dtoEntrega, Pedido pedido) {

        Entrega entrega = Entrega.builder()
                .nomeEntregador(dtoEntrega.getNomeEntregador())
                .dataEntrega(LocalDateTime.now())
                .build();

        entrega.getPedidos().add(pedido);

        return entrega;

    }

}
