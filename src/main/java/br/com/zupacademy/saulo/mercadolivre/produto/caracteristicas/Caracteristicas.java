package br.com.zupacademy.saulo.mercadolivre.produto.caracteristicas;

import br.com.zupacademy.saulo.mercadolivre.produto.entidade.Produto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Caracteristicas {

    public Caracteristicas(String nome, String descricao, Produto produto) {
        this.nome = nome;
        this.descricao = descricao;
        this.produto = produto;
    }

    public Caracteristicas() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    @NotNull
    @ManyToOne
    private Produto produto;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

}
