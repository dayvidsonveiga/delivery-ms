package br.com.grupoacert.deliveryms.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "entregas")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence_entrega")
    @SequenceGenerator(name = "id_sequence_entrega", sequenceName = "ID_SEQ_ENTREGA")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

}
