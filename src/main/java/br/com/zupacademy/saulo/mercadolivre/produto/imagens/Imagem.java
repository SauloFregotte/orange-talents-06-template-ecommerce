package br.com.zupacademy.saulo.mercadolivre.produto.imagens;

import br.com.zupacademy.saulo.mercadolivre.produto.entidade.Produto;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;

@Entity
public class Imagem {

    public Imagem(String linkImagem, Produto produto) {
        this.linkImagem = linkImagem;
        this.produto = produto;
    }

    public Imagem() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @URL
    private String linkImagem;

    @ManyToOne
    private Produto produto;

    public Long getId() {
        return id;
    }

    public String getLinkImagem() {
        return linkImagem;
    }

    public Long getProduto() {
        return produto.getId();
    }
}
