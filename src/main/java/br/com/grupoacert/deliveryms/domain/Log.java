package br.com.grupoacert.deliveryms.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    @Column(name = "id")
    private Long id;

    @Column(name = "mensagem_erro")
    private String msgErro;

    @Column(name = "data_erro")
    private LocalDateTime dataErro;

}
