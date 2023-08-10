package br.com.grupoacert.deliveryms.ultil;

import br.com.grupoacert.deliveryms.domain.Cliente;
import br.com.grupoacert.deliveryms.domain.Pedido;
import br.com.grupoacert.deliveryms.dto.entrada.DtoPedido;
import br.com.grupoacert.deliveryms.dto.entrada.DtoPedidoAtualizar;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoClienteCriado;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoPedidoCriado;

public class MockPedidoBuilder {

    public DtoPedido mockDtoPedido(String descricao, Double valor) {
        DtoPedido dtoPedido = new DtoPedido();
        dtoPedido.setDescricao(descricao);
        dtoPedido.setValor(valor);
        return dtoPedido;
    }

    public DtoPedidoAtualizar mockDtoPedidoAtualizar(String descricao, Double valor) {
        DtoPedidoAtualizar dtoPedidoAtualizar = new DtoPedidoAtualizar();
        dtoPedidoAtualizar.setDescricao(descricao);
        dtoPedidoAtualizar.setValor(valor);
        return dtoPedidoAtualizar;
    }

    public DtoRetornoPedidoCriado mockDtoRetornoPedidoCriado(Long id, String descricao, Double valor, DtoRetornoClienteCriado cliente) {
        return DtoRetornoPedidoCriado.builder()
                .id(id)
                .descricao(descricao)
                .valor(valor)
                .cliente(cliente)
                .build();
    }

    public Pedido mockPedido(Long id, String descricao, Double valor, Cliente cliente) {
        return Pedido.builder()
                .id(id)
                .descricao(descricao)
                .valor(valor)
                .cliente(cliente)
                .build();
    }

}
