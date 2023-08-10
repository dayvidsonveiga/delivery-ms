package br.com.grupoacert.deliveryms.service;

import br.com.grupoacert.deliveryms.domain.Cliente;
import br.com.grupoacert.deliveryms.dto.entrada.DtoCliente;
import br.com.grupoacert.deliveryms.dto.entrada.DtoClienteAtualizar;
import br.com.grupoacert.deliveryms.dto.entrada.DtoClienteCpf;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoClienteCriado;
import br.com.grupoacert.deliveryms.exception.CadastroDuplicadoException;
import br.com.grupoacert.deliveryms.exception.CadastroNaoEncontradoException;
import br.com.grupoacert.deliveryms.repository.ClienteRepository;
import br.com.grupoacert.deliveryms.ultil.MockClienteBuilder;
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
class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private LogService logService;

    private MockClienteBuilder mockClienteBuilder;

    @BeforeEach
    void setUp() {

        mockClienteBuilder = new MockClienteBuilder();

    }


    @Test
    public void testCriarClienteComSucesso() {
        DtoCliente dtoCliente = mockClienteBuilder.mockDtoCliente("João", "12345678901", "joao@example.com", "12345-678");
        Cliente cliente = mockClienteBuilder.mockCliente(1L, dtoCliente.getNome(), dtoCliente.getCpf(), dtoCliente.getEmail(), dtoCliente.getCep());

        when(clienteRepository.findByCpfEqualsIgnoreCase(dtoCliente.getCpf())).thenReturn(Optional.empty());
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        DtoRetornoClienteCriado retorno = clienteService.criar(dtoCliente);

        verify(clienteRepository).findByCpfEqualsIgnoreCase("12345678901");
        verify(clienteRepository).save(any(Cliente.class));

        assertNotNull(retorno);
        assertEquals(retorno.getNome(), dtoCliente.getNome());
        assertEquals(retorno.getCpf(), dtoCliente.getCpf());
        assertEquals(retorno.getEmail(), dtoCliente.getEmail());
        assertEquals(retorno.getCep(), dtoCliente.getCep());
    }

    @Test
    public void testCriarClienteComClienteJaCadastrado() {
        DtoCliente dtoCliente = mockClienteBuilder.mockDtoCliente("João", "12345678901", "joao@example.com", "12345-678");
        Cliente cliente = mockClienteBuilder.mockCliente(1L, dtoCliente.getNome(), dtoCliente.getCpf(), dtoCliente.getEmail(), dtoCliente.getCep());

        when(clienteRepository.findByCpfEqualsIgnoreCase(dtoCliente.getCpf())).thenReturn(Optional.ofNullable(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        assertThrows(CadastroDuplicadoException.class, () -> clienteService.criar(dtoCliente));
    }

    @Test
    public void testAtualizarCliente() {
        DtoClienteAtualizar dtoClienteAtualizar = mockClienteBuilder.mockDtoClienteAtualizar("Novo Nome", null, null, null);

        Cliente cliente = mockClienteBuilder.mockCliente(1L, "Teste", "123", "exemplo@gmail.com", "280100");

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        DtoRetornoClienteCriado retorno = clienteService.atualizar(1L, dtoClienteAtualizar);

        verify(clienteRepository).findById(1L);
        assertEquals(retorno.getNome(), dtoClienteAtualizar.getNome());
    }

    @Test
    public void testDeletarCliente() {
        Long id = 1L;
        Cliente cliente = mockClienteBuilder.mockCliente(id, "Teste", "123", "exemplo@gmail.com", "280100");

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        Long idDeletado = clienteService.deletar(1L);

        verify(clienteRepository).findById(id);
        verify(clienteRepository).delete(cliente);
        assertEquals(idDeletado, id);
    }

    @Test
    public void testEncontrarPeloCpfComSucesso() {
        DtoClienteCpf dtoClienteCpf = new DtoClienteCpf();
        dtoClienteCpf.setCpf("12345678901");

        when(clienteRepository.findByCpfEqualsIgnoreCase("12345678901")).thenReturn(Optional.of(new Cliente()));

        DtoRetornoClienteCriado retorno = clienteService.encontrarPeloCpf(dtoClienteCpf);

        verify(clienteRepository).findByCpfEqualsIgnoreCase("12345678901");
    }

    @Test
    public void testEncontrarPeloCpfNaoEncontrado() {
        DtoClienteCpf dtoClienteCpf = mockClienteBuilder.mockDtoClienteCpf("12345678901");

        when(clienteRepository.findByCpfEqualsIgnoreCase(dtoClienteCpf.getCpf())).thenReturn(Optional.empty());

        assertThrows(CadastroNaoEncontradoException.class, () -> clienteService.encontrarPeloCpf(dtoClienteCpf));
    }

    @Test
    public void testEncontrarPeloIdComSucesso() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(new Cliente()));

        Cliente clienteEncontrado = clienteService.encontrarPeloId(1L);

        verify(clienteRepository).findById(1L);
    }

    @Test
    public void testEncontrarPeloIdNaoEncontrado() {
        Long id = 1L;

        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CadastroNaoEncontradoException.class, () -> clienteService.encontrarPeloId(id));
    }

    @Test
    public void testListarClientesComSucesso() {
        Cliente cliente = mockClienteBuilder.mockCliente(1L, "Teste", "123", "exemplo@gmail.com", "280100");
        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes.add(cliente);
        when(clienteRepository.findAll()).thenReturn(listaClientes);

        List<DtoRetornoClienteCriado> listaRetorno = clienteService.listarClientes();

        verify(clienteRepository).findAll();
        assertNotNull(listaRetorno);
        assertTrue(listaRetorno.size() >= 1);
    }

    @Test
    public void testListarClientesSemClienteCadastrado() {
        List<Cliente> listaClientes = new ArrayList<>();

        when(clienteRepository.findAll()).thenReturn(listaClientes);

        assertThrows(CadastroNaoEncontradoException.class, () -> clienteService.listarClientes());
    }

}