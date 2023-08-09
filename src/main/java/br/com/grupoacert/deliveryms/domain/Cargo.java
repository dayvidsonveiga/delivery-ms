package br.com.grupoacert.deliveryms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "cargos")
public class Cargo implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_sequence_cargo")
    @SequenceGenerator(name = "id_sequence_cargo", sequenceName = "ID_SEQ_CARGO")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome_cargo")
    private String nomeCargo;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cargo", cascade = CascadeType.MERGE)
    private Set<Usuario> usuarios = new HashSet<>();

    public String getAuthority() {
        return nomeCargo;
    }

}
