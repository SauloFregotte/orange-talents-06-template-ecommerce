package br.com.zupacademy.saulo.mercadolivre.pergunta.entidade;

import br.com.zupacademy.saulo.mercadolivre.pergunta.RepositoryPerguntaJPA;
import br.com.zupacademy.saulo.mercadolivre.produto.entidade.Produto;
import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Pergunta {

    public Pergunta(final String titulo, final Produto produto, final Usuario usuario) {
        this.titulo = titulo;
        this.produto = produto;
        this.usuario = usuario;
    }

    public Pergunta() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    @NotNull
    private final LocalDateTime localDateTime = LocalDateTime.now();

    @NotNull
    @ManyToOne
    private Produto produto;

    @NotNull
    @ManyToOne
    private Usuario usuario;

    public Pergunta cadastrar(RepositoryPerguntaJPA repositoryPerguntaJPA) {
        return repositoryPerguntaJPA.save(this);
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public Produto getProduto() {
        return produto;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
