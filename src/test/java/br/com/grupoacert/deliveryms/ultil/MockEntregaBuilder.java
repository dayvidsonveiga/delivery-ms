package br.com.grupoacert.deliveryms.ultil;

import br.com.grupoacert.deliveryms.domain.Entrega;
import br.com.grupoacert.deliveryms.domain.Pedido;
import br.com.grupoacert.deliveryms.dto.entrada.DtoEntrega;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoEntregaCriado;
import br.com.grupoacert.deliveryms.dto.retorno.DtoRetornoPedidoCriado;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class MockEntregaBuilder {

    public DtoEntrega mockDtoEntrega(String nome) {
        DtoEntrega dtoEntrega = new DtoEntrega();
        dtoEntrega.setNomeEntregador(nome);
        return dtoEntrega;
    }

    public DtoRetornoEntregaCriado mockDtoRetornoEntregaCriado(Long id, LocalDateTime dataEntrega,
                                                               List<DtoRetornoPedidoCriado> pedidos) {

        return DtoRetornoEntregaCriado.builder()
                .id(id)
                .dataEntrega(dataEntrega)
                .pedidoCriados(pedidos)
                .build();
    }

    public Entrega mockEntrega(Long id, LocalDateTime dataEntrega, Set<Pedido> pedidos) {
        return Entrega.builder()
                .id(id)
                .dataEntrega(dataEntrega)
                .pedidos(pedidos)
                .build();
    }

}
