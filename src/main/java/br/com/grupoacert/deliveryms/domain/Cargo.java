package br.com.grupoacert.deliveryms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
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
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
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
