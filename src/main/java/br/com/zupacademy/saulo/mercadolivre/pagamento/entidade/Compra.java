package br.com.zupacademy.saulo.mercadolivre.pagamento.entidade;

import br.com.zupacademy.saulo.mercadolivre.pagamento.GatewayPagamento;
import br.com.zupacademy.saulo.mercadolivre.pagamento.RepositoryCompraJPA;
import br.com.zupacademy.saulo.mercadolivre.pagamento.StatusDaCompra;
import br.com.zupacademy.saulo.mercadolivre.produto.RepositoryProdutoJPA;
import br.com.zupacademy.saulo.mercadolivre.produto.entidade.Produto;
import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Compra {

    public Compra(final int quantidade, final Produto produto,
                  final Usuario buyer, final GatewayPagamento gatewayPagamento) {
        this.quantidade = quantidade;
        this.produto = produto;
        this.buyer = buyer;
        this.gatewayPagamento = gatewayPagamento;
    }

    public Compra() {
    }

    public Compra registrarCompra(final RepositoryProdutoJPA repositoryProdutoJPA,
                                  final RepositoryCompraJPA repositoryCompraJPA) {
        produto.abaterEstoque(repositoryProdutoJPA, quantidade);
        return repositoryCompraJPA.save(this);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private int quantidade;

    @NotNull
    @Enumerated(EnumType.STRING)
    private final StatusDaCompra statusDaCompra = StatusDaCompra.INICIADA;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GatewayPagamento gatewayPagamento;

    @NotNull
    @ManyToOne
    private Produto produto;

    @NotNull
    @ManyToOne
    private Usuario buyer;

    public Long getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public StatusDaCompra getStatusDaCompra() {
        return statusDaCompra;
    }

    public String getGatewayPagamento() {
        return gatewayPagamento.redirectPorTipoDePagamento(id);
    }

    public Long getProduto() {
        return produto.getId();
    }

    public String getNomeProduto(){
        return produto.getNome();
    }

    public Usuario getBuyer() {
        return buyer;
    }

    public String getVendedor() {
        return produto.getUsuario().getEmail();
    }
}
