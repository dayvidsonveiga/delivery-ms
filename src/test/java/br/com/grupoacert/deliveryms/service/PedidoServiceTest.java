package br.com.grupoacert.deliveryms.service;

import br.com.grupoacert.deliveryms.domain.Cliente;
import br.com.grupoacert.deliveryms.domain.Pedido;
import br.com.grupoacert.deliveryms.dto.entrada.DtoPedido;
import br.com.grupoacert.deliveryms.dto.entrada.DtoPedidoAtualizar;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoPedidoCriado;
import br.com.grupoacert.deliveryms.exception.CadastroNaoEncontradoException;
import br.com.grupoacert.deliveryms.repository.ClienteRepository;
import br.com.grupoacert.deliveryms.repository.PedidoRepository;
import br.com.grupoacert.deliveryms.ultil.MockClienteBuilder;
import br.com.grupoacert.deliveryms.ultil.MockPedidoBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private LogService logService;

    private ClienteService clienteService;

    private MockClienteBuilder mockClienteBuilder;

    private MockPedidoBuilder mockPedidoBuilder;

    @BeforeEach
    void setUp() {

        clienteService = new ClienteService(clienteRepository, logService);
        pedidoService = new PedidoService(pedidoRepository, clienteService, logService);
        mockClienteBuilder = new MockClienteBuilder();
        mockPedidoBuilder = new MockPedidoBuilder();

    }

    @Test
    public void testCriarPedidoComSucesso() {
        Long idCliente = 1L;
        DtoPedido dtoPedido = mockPedidoBuilder.mockDtoPedido("Suco de laranja", 6.50);
        Cliente cliente = mockClienteBuilder.mockCliente(idCliente, "Teste", "123", "exemplo@gmail.com", "280100");
        Pedido pedido = mockPedidoBuilder.mockPedido(1L, dtoPedido.getDescricao(), dtoPedido.getValor(), cliente);

        when(clienteRepository.findById(idCliente)).thenReturn(Optional.ofNullable(cliente));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        DtoRetornoPedidoCriado retorno = pedidoService.criar(idCliente, dtoPedido);

        verify(clienteRepository).findById(idCliente);
        verify(pedidoRepository).save(any(Pedido.class));

        assertNotNull(retorno);
        assertEquals(retorno.getDescricao(), dtoPedido.getDescricao());
        assertEquals(retorno.getValor(), dtoPedido.getValor());
        assertEquals(retorno.getCliente().getNome(), cliente.getNome());
    }

    @Test
    public void testCriarPedidoSemCliente() {
        Long idCliente = 1L;
        DtoPedido dtoPedido = mockPedidoBuilder.mockDtoPedido("Suco de laranja", 6.50);
        Pedido pedido = mockPedidoBuilder.mockPedido(1L, dtoPedido.getDescricao(), dtoPedido.getValor(), null);

        when(clienteRepository.findById(idCliente)).thenReturn(Optional.empty());
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        assertThrows(CadastroNaoEncontradoException.class, () -> pedidoService.criar(idCliente, dtoPedido));
    }

    @Test
    public void testAtualizarPedido() {
        Long idCliente = 1L;
        Long idPedido = 1L;
        DtoPedidoAtualizar dtoPedidoAtualizar = mockPedidoBuilder.mockDtoPedidoAtualizar("Novo", 1.0);
        Cliente cliente = mockClienteBuilder.mockCliente(idCliente, "Teste", "123", "exemplo@gmail.com", "280100");
        Pedido pedido = mockPedidoBuilder.mockPedido(idPedido, "Suco de laranja", 6.50, cliente);

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.ofNullable(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        DtoRetornoPedidoCriado retorno = pedidoService.atualizar(idPedido, dtoPedidoAtualizar);

        verify(pedidoRepository).findById(idPedido);

        assertEquals(retorno.getId(), idPedido);
        assertEquals(retorno.getDescricao(), dtoPedidoAtualizar.getDescricao());
        assertEquals(retorno.getValor(), dtoPedidoAtualizar.getValor());
    }

    @Test
    public void testDeletarCliente() {
        Long idCliente = 1L;
        Long idPedido = 1L;
        Cliente cliente = mockClienteBuilder.mockCliente(idCliente, "Teste", "123", "exemplo@gmail.com", "280100");
        Pedido pedido = mockPedidoBuilder.mockPedido(idPedido, "Suco de laranja", 6.50, cliente);

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedido));

        Long idDeletado = pedidoService.deletar(idPedido);

        verify(pedidoRepository).delete(pedido);

        assertEquals(idDeletado, idPedido);
    }


    @Test
    public void testEncontrarPeloIdComSucesso() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(new Pedido()));

        Pedido pedido = pedidoService.encontrarPeloId(1L);

        verify(pedidoRepository).findById(1L);
    }

    @Test
    public void testEncontrarPeloIdNaoEncontrado() {
        Long id = 1L;

        when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CadastroNaoEncontradoException.class, () -> pedidoService.encontrarPeloId(id));
    }

    @Test
    public void testEncontrarPeloIdRetornoDtoComSucesso() {
        Long idCliente = 1L;
        Long idPedido = 1L;
        Cliente cliente = mockClienteBuilder.mockCliente(idCliente, "Teste", "123", "exemplo@gmail.com", "280100");
        Pedido pedido = mockPedidoBuilder.mockPedido(idPedido, "Suco de laranja", 6.50, cliente);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        DtoRetornoPedidoCriado resposta = pedidoService.encontrarPeloIdRetornaDto(1L);

        verify(pedidoRepository).findById(1L);
        assertEquals(resposta.getId(), pedido.getId());
        assertEquals(resposta.getDescricao(), pedido.getDescricao());
        assertEquals(resposta.getValor(), pedido.getValor());
        assertEquals(resposta.getCliente().getNome(), cliente.getNome());
    }

    @Test
    public void testListarPedidosComSucesso() {
        Long idCliente = 1L;
        Long idPedido = 1L;
        Cliente cliente = mockClienteBuilder.mockCliente(idCliente, "Teste", "123", "exemplo@gmail.com", "280100");
        Pedido pedido = mockPedidoBuilder.mockPedido(idPedido, "Suco de laranja", 6.50, cliente);
        List<Pedido> listaPedidos = new ArrayList<>();
        listaPedidos.add(pedido);

        when(pedidoRepository.findAll()).thenReturn(listaPedidos);

        List<DtoRetornoPedidoCriado> listaRetorno = pedidoService.listarPedidos();

        verify(pedidoRepository).findAll();
        assertNotNull(listaRetorno);
        assertTrue(listaRetorno.size() >= 1);
    }

    @Test
    public void testListarPedidosSemPedidoCadastrado() {
        List<Pedido> listaPedidos = new ArrayList<>();

        when(pedidoRepository.findAll()).thenReturn(listaPedidos);

        assertThrows(CadastroNaoEncontradoException.class, () -> pedidoService.listarPedidos());
    }

}