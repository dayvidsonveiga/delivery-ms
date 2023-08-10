package br.com.grupoacert.deliveryms.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "entregas")
public class Entrega {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome_entregador")
    private String nomeEntregador;

    @NotNull
    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entrega", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Pedido> pedidos = new HashSet<>();

}
