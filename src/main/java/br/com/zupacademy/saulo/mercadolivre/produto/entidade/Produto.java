package br.com.zupacademy.saulo.mercadolivre.produto.entidade;

import br.com.zupacademy.saulo.mercadolivre.categoria.entidade.Categoria;
import br.com.zupacademy.saulo.mercadolivre.config.EntityException;
import br.com.zupacademy.saulo.mercadolivre.config.QuantidadeNaoPodeSerAbatidaException;
import br.com.zupacademy.saulo.mercadolivre.opniao.entidade.Opniao;
import br.com.zupacademy.saulo.mercadolivre.pergunta.entidade.Pergunta;
import br.com.zupacademy.saulo.mercadolivre.produto.RepositoryProdutoJPA;
import br.com.zupacademy.saulo.mercadolivre.produto.caracteristicas.Caracteristicas;
import br.com.zupacademy.saulo.mercadolivre.produto.imagens.Imagem;
import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Produto {

    public static Builder builder() {
        return new Builder();
    }

    public static Produto obterInformacoes(Long id, RepositoryProdutoJPA repositoryProdutoJPA) {
        return repositoryProdutoJPA.findById(id)
                .orElseThrow(()->{throw new EntityException("O produto não existe!");});
    }

    public Produto(Builder builder) {
        this.nome = builder.nome;
        this.valor = builder.valor;
        this.quantidadeNoEstoque = builder.quantidade;
        this.descricao = builder.descricao;
        this.categoria = builder.categoria;
        //Não bom, um rato, para se construir, precisa de um milagre divino (metodo de construcao)
        // para ter uma (a construcao da sua) cauda
        this.caracteristicas = builder.caracteristicas
                .stream()
                .map(
                        carac ->
                                new Caracteristicas(carac.getNome(), carac.getDescricao(), this))
                .collect(Collectors.toList());
        this.usuario = builder.userLogged;
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
    private int quantidadeNoEstoque;

    @NotBlank
    private String descricao;

    @NotNull
    private final LocalDateTime localDateTime = LocalDateTime.now();

    @ManyToOne
    private Usuario usuario;

    @NotNull
    @OneToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @NotNull
    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private List<Caracteristicas> caracteristicas;

    @NotNull
    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private final Set<Imagem> listaImagens = new HashSet<>();

    @NotNull
    @OneToMany(mappedBy = "produto")
    private List<Opniao> listaOpnioes;

    @NotNull
    @OneToMany(mappedBy = "produto")
    private List<Pergunta> listaPerguntas;

    public Produto cadastrar(final RepositoryProdutoJPA repositoryProdutoJPA) {
        /*Este If é necessario para o caso de vc estiver trazendo
          Entidades do banco e quiser altera-las,caso contrario teriamos
          uma verificação para updates*/
        if (this.id == null)
            verifyIfTheInsertingProdutoIsExactlyTheSameAsOneAlreadyInserted(repositoryProdutoJPA);
        verifyMinimunCaracteristicas(caracteristicas);
        return repositoryProdutoJPA.save(this);
    }

    private void verifyMinimunCaracteristicas(List<Caracteristicas> caracteristicas) {
        if (caracteristicas.size() < 3)
            throw new EntityException("Não existem características suficientes " +
                    "para este produto (min de 3)!");
    }

    private void verifyIfTheInsertingProdutoIsExactlyTheSameAsOneAlreadyInserted(final RepositoryProdutoJPA repositoryProdutoJPA) {
        repositoryProdutoJPA
                .findFirstByNomeAndValorAndQuantidadeNoEstoqueAndDescricao(
                        this.nome,
                        this.valor,
                        this.quantidadeNoEstoque,
                        this.descricao
                )
                .ifPresent(
                        e -> {
                            throw new EntityException(
                                    "Tentativa de inserir um produto exatemente igual " +
                                            "(nome, valor, quantidade, descrição) " +
                                            "a um já inserido anteriormente!"
                            );
                        }
                );

    }

    public void associarImagens(List<String> listaImagens) {
        this.listaImagens
                .addAll(
                        listaImagens
                                .stream()
                                .map(
                                        imagemLinkString -> new Imagem(imagemLinkString, this)
                                )
                                .collect(Collectors.toSet())
                );
    }

    public Double calcularMedia() {
        final Optional<Double> reduce = listaOpnioes
                .stream()
                .map(Opniao::getNota)
                .reduce(Double::sum);
        if( reduce.isEmpty() ) return 0.0;
        return reduce.get() /listaOpnioes.size();
    }

    public Integer totalNotas() {
        return listaOpnioes.size();
    }

    public void abaterEstoque(final RepositoryProdutoJPA repositoryProdutoJPA, final int quantidade){
        if( !(quantidade <= quantidadeNoEstoque) )
            throw new QuantidadeNaoPodeSerAbatidaException(
                            "A quantidade { " + quantidade + " } " +
                                    "do produto { " + nome + " } não pode ser abatida.");
        quantidadeNoEstoque -= quantidade;
        cadastrar(repositoryProdutoJPA);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Double.compare(produto.valor, valor) == 0 &&
                quantidadeNoEstoque == produto.quantidadeNoEstoque &&
                Objects.equals(id, produto.id) &&
                Objects.equals(nome, produto.nome) &&
                Objects.equals(descricao, produto.descricao) &&
                Objects.equals(localDateTime, produto.localDateTime) &&
                Objects.equals(usuario, produto.usuario) &&
                Objects.equals(listaImagens, produto.listaImagens) &&
                Objects.equals(categoria, produto.categoria) &&
                Objects.equals(caracteristicas, produto.caracteristicas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                nome,
                valor,
                quantidadeNoEstoque,
                descricao,
                localDateTime,
                usuario,
                listaImagens,
                categoria,
                caracteristicas);
    }

    public static class Builder {

        private transient String nome;
        private transient double valor;
        private transient int quantidade;
        private transient String descricao;
        private transient Categoria categoria;
        private transient List<Caracteristicas> caracteristicas;
        private transient Usuario userLogged;

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

        public Builder comUsuario(Usuario userLogged) {
            this.userLogged = userLogged;
            return this;
        }

        public Produto build() {
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

    public int getQuantidadeNoEstoque() {
        return quantidadeNoEstoque;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public Set<Imagem> getListaImagens() {
        return listaImagens;
    }

    public List<Opniao> getListaOpnioes() {
        return listaOpnioes;
    }

    public List<Pergunta> getListaPerguntas() {
        return listaPerguntas;
    }
}
