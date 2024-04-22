package com.fmt.notelock.datasource.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nota")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class NotaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String conteudo;

    @Column(name = "id_cadastro")
    private Long idCadastro;

    @ManyToOne
    @JoinColumn(name = "id_cadastro", referencedColumnName = "id", insertable = false, updatable = false)
    private CadastroEntity cadastroEntity;

    @Column(name = "id_caderno")
    private Long idCaderno;

    @ManyToOne
    @JoinColumn(name = "id_caderno", referencedColumnName = "id", insertable = false, updatable = false)
    private CadernoEntity CadernoEntity;

}
