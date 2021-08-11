package br.com.zupacademy.saulo.mercadolivre.opniao.entidade;

import br.com.zupacademy.saulo.mercadolivre.opniao.RepositoryOpniaoJPA;
import br.com.zupacademy.saulo.mercadolivre.produto.entidade.Produto;
import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

@Entity
public class Opniao {

    public Opniao(Double nota, String titulo, String descricao, Produto produto, Usuario usuario) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
        this.produto = produto;
        this.usuario = usuario;
    }

    public Opniao() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(1)
    @Max(5)
    private Double nota;

    @NotBlank
    @NotEmpty
    private String titulo;

    @NotBlank
    @NotEmpty
    @Size(max = 500)
    private String descricao;

    @NotNull
    @ManyToOne
    private Produto produto;

    @NotNull
    @ManyToOne
    private Usuario usuario;

    public Opniao cadastrar(RepositoryOpniaoJPA repositoryOpniaoJPA) {
        return repositoryOpniaoJPA.save(this);
    }

    public Long getId() {
        return id;
    }

    public double getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getProduto() {
        return produto.getId();
    }

    public Long getUsuario() {
        return usuario.getId();
    }
}
