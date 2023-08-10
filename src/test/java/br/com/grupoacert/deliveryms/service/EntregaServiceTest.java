package br.com.grupoacert.deliveryms.service;

import br.com.grupoacert.deliveryms.domain.Cliente;
import br.com.grupoacert.deliveryms.domain.Entrega;
import br.com.grupoacert.deliveryms.domain.Pedido;
import br.com.grupoacert.deliveryms.dto.entrada.DtoEntrega;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoEntregaCriado;
import br.com.grupoacert.deliveryms.exception.CadastroNaoEncontradoException;
import br.com.grupoacert.deliveryms.repository.EntregaRepository;
import br.com.grupoacert.deliveryms.ultil.MockClienteBuilder;
import br.com.grupoacert.deliveryms.ultil.MockEntregaBuilder;
import br.com.grupoacert.deliveryms.ultil.MockPedidoBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EntregaServiceTest {

    @InjectMocks
    private EntregaService entregaService;

    @Mock
    private EntregaRepository entregaRepository;

    @Mock
    private LogService logService;

    @Mock
    private PedidoService pedidoService;

    private MockPedidoBuilder mockPedidoBuilder;

    private MockEntregaBuilder mockEntregaBuilder;

    private MockClienteBuilder mockClienteBuilder;

    @BeforeEach
    void setUp() {

        entregaService = new EntregaService(entregaRepository, pedidoService, logService);
        mockPedidoBuilder = new MockPedidoBuilder();
        mockEntregaBuilder = new MockEntregaBuilder();
        mockClienteBuilder = new MockClienteBuilder();

    }

    @Test
    public void criarComSucesso() {
        Long idPedido = 1L;
        Long idCliente = 1L;
        Long idEntrega = 1L;
        LocalDateTime dataEntrega = LocalDateTime.now();

        Cliente cliente = mockClienteBuilder.mockCliente(idCliente, "Teste", "123", "exemplo@gmail.com", "280100");
        Pedido pedido = mockPedidoBuilder.mockPedido(idPedido, "Suco de laranja", 6.50, cliente);
        Set<Pedido> pedidos = new HashSet<>();
        pedidos.add(pedido);
        Entrega entrega = mockEntregaBuilder.mockEntrega(idEntrega, dataEntrega, pedidos);

        DtoEntrega dtoEntrega = mockEntregaBuilder.mockDtoEntrega("Joao");

        when(pedidoService.encontrarPeloId(idPedido)).thenReturn(pedido);
        when(entregaRepository.save(any(Entrega.class))).thenReturn(entrega);

        DtoRetornoEntregaCriado resposta = entregaService.criar(idPedido, dtoEntrega);

        assertNotNull(resposta);
    }

    @Test
    public void testAtualizarPedido() {
        Long idCliente = 1L;
        Long idPedido = 1L;
        Long idEntrega = 1L;
        DtoEntrega dtoEntrega = mockEntregaBuilder.mockDtoEntrega("Novo");
        Cliente cliente = mockClienteBuilder.mockCliente(idCliente, "Teste", "123", "exemplo@gmail.com", "280100");
        Pedido pedido = mockPedidoBuilder.mockPedido(idPedido, "Suco de laranja", 6.50, cliente);
        Set<Pedido> pedidos = new HashSet<>();
        pedidos.add(pedido);
        Entrega entrega = mockEntregaBuilder.mockEntrega(idEntrega, LocalDateTime.now(), pedidos);

        when(pedidoService.encontrarPeloId(idPedido)).thenReturn(pedido);
        when(entregaRepository.findById(idEntrega)).thenReturn(Optional.ofNullable(entrega));
        when(entregaRepository.save(any(Entrega.class))).thenReturn(entrega);

        DtoRetornoEntregaCriado retorno = entregaService.atualizar(idPedido, dtoEntrega);

        verify(entregaRepository).findById(idEntrega);

        assertEquals(retorno.getId(), idEntrega);
        assertEquals(retorno.getNomeEntregador(), dtoEntrega.getNomeEntregador());
    }

    @Test
    public void testDeletarEntrega() {
        Long idCliente = 1L;
        Long idPedido = 1L;
        Long idEntrega = 1L;
        Cliente cliente = mockClienteBuilder.mockCliente(idCliente, "Teste", "123", "exemplo@gmail.com", "280100");
        Pedido pedido = mockPedidoBuilder.mockPedido(idPedido, "Suco de laranja", 6.50, cliente);
        Set<Pedido> pedidos = new HashSet<>();
        pedidos.add(pedido);
        Entrega entrega = mockEntregaBuilder.mockEntrega(idEntrega, LocalDateTime.now(), pedidos);

        when(pedidoService.encontrarPeloId(anyLong())).thenReturn(pedido);
        when(entregaRepository.findById(idEntrega)).thenReturn(Optional.of(entrega));

        Long idDeletado = entregaService.deletar(idEntrega);

        verify(entregaRepository).delete(entrega);

        assertEquals(idDeletado, idEntrega);
    }


    @Test
    public void testEncontrarPeloIdComSucesso() {
        when(entregaRepository.findById(1L)).thenReturn(Optional.of(new Entrega()));

        Entrega entrega = entregaService.encontrarPeloId(1L);

        verify(entregaRepository).findById(1L);
    }

    @Test
    public void testEncontrarPeloIdNaoEncontrado() {
        Long id = 1L;

        when(entregaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CadastroNaoEncontradoException.class, () -> entregaService.encontrarPeloId(id));
    }

    @Test
    public void testEncontrarPeloIdRetornaDto() {
        when(entregaRepository.findById(1L)).thenReturn(Optional.of(new Entrega()));

        DtoRetornoEntregaCriado dtoRetornoEntregaCriado = entregaService.encontrarPeloIdRetornaDto(1L);

        verify(entregaRepository).findById(1L);
    }


    @Test
    public void testListarEntregasComSucesso() {
        Long idCliente = 1L;
        Long idPedido = 1L;
        Long idEntrega = 1L;
        Cliente cliente = mockClienteBuilder.mockCliente(idCliente, "Teste", "123", "exemplo@gmail.com", "280100");
        Pedido pedido = mockPedidoBuilder.mockPedido(idPedido, "Suco de laranja", 6.50, cliente);
        Set<Pedido> pedidos = new HashSet<>();
        pedidos.add(pedido);
        Entrega entrega = mockEntregaBuilder.mockEntrega(idEntrega, LocalDateTime.now(), pedidos);
        List<Entrega> entregas = Collections.singletonList(entrega);

        when(entregaRepository.findAll()).thenReturn(entregas);

        List<DtoRetornoEntregaCriado> listaRetorno = entregaService.listarEntregas();

        verify(entregaRepository).findAll();
        assertNotNull(listaRetorno);
        assertTrue(listaRetorno.size() >= 1);
    }

    @Test
    public void testListarEntregasSemEntregaCadastrada() {
        List<Entrega> entregas = new ArrayList<>();

        when(entregaRepository.findAll()).thenReturn(entregas);

        assertThrows(CadastroNaoEncontradoException.class, () -> entregaService.listarEntregas());
    }

}