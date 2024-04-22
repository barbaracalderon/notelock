package com.fmt.notelock.datasource.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "caderno")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class CadernoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Column(name = "id_cadastro")
    private Long idCadastro;

    @ManyToOne
    @JoinColumn(name = "id_cadastro", referencedColumnName = "id", insertable = false, updatable = false)
    private CadastroEntity cadastroEntity;

}
