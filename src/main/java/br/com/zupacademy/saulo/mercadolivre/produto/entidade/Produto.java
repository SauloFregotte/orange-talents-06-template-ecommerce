package br.com.zupacademy.saulo.mercadolivre.produto.entidade;

import br.com.zupacademy.saulo.mercadolivre.EntityException;
import br.com.zupacademy.saulo.mercadolivre.categoria.entidade.Categoria;
import br.com.zupacademy.saulo.mercadolivre.produto.RepositoryProdutoJPA;
import br.com.zupacademy.saulo.mercadolivre.produto.caracteristicas.Caracteristicas;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Produto {

    public static Builder builder(){
        return new Builder();
    }

    public Produto(Builder builder) {
        this.nome = builder.nome;
        this.valor = builder.valor;
        this.quantidade = builder.quantidade;
        this.descricao = builder.descricao;
        this.categoria = builder.categoria;
        //Não bom, um rato, para se construir, precisa de um milagre divino (metodo de construcao)
        // para ter uma (a construcao da sua) cauda
        this.caracteristicas = builder.caracteristicas
                .stream()
                .map(
                        carac ->
                                new Caracteristicas(carac.getNome(),carac.getDescricao(),this))
                .collect(Collectors.toList());
    }

    public Produto() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    private double valor;

    @NotNull
    private int quantidade;

    @NotBlank
    private String descricao;

    @NotNull
    private final LocalDateTime localDateTime = LocalDateTime.now();

    @NotNull
    @OneToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private List<Caracteristicas> caracteristicas;

    public Produto cadastrar(final RepositoryProdutoJPA repositoryProdutoJPA){
        verifyMinimunCaracteristicas(caracteristicas);
        return repositoryProdutoJPA.save(this);
    }

    private void verifyMinimunCaracteristicas(List<Caracteristicas> caracteristicas){
        if(caracteristicas.size()< 3)
            throw new EntityException("Não existem caracteristicas suficientes para este produto (min de 3)!");
    }

    public static class Builder{

        private transient String nome;
        private transient double valor;
        private transient int quantidade;
        private transient String descricao;
        private transient Categoria categoria;
        private transient List<Caracteristicas> caracteristicas;

        public Builder comNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder comValor(double valor) {
            this.valor = valor;
            return this;
        }

        public Builder comQuantidade(int quantidade) {
            this.quantidade = quantidade;
            return this;
        }

        public Builder comDescricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder comCategoria(Categoria categoria) {
            this.categoria = categoria;
            return this;
        }

        public Builder comCaracteristicas(List<Caracteristicas> caracteristicas) {
            this.caracteristicas = caracteristicas;
            return this;
        }

        public Produto build(){
            return new Produto(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public List<Caracteristicas> getCaracteristicas() {
        return caracteristicas;
    }
}
