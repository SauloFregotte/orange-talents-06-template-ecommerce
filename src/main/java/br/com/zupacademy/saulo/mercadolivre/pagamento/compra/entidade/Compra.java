package br.com.zupacademy.saulo.mercadolivre.pagamento.compra.entidade;

import br.com.zupacademy.saulo.mercadolivre.config.EntityException;
import br.com.zupacademy.saulo.mercadolivre.pagamento.GatewayPagamento;
import br.com.zupacademy.saulo.mercadolivre.pagamento.compra.RepositoryCompraJPA;
import br.com.zupacademy.saulo.mercadolivre.pagamento.compra.StatusDaCompra;
import br.com.zupacademy.saulo.mercadolivre.pagamento.transacao.entidade.Transacao;
import br.com.zupacademy.saulo.mercadolivre.produto.RepositoryProdutoJPA;
import br.com.zupacademy.saulo.mercadolivre.produto.entidade.Produto;
import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Compra {

    public static Compra findByID(Long idCompra, RepositoryCompraJPA repositoryCompraJPA) {
        return repositoryCompraJPA.findById( idCompra )
                .orElseThrow(() -> new EntityException(
                        String.format("Compra { %d } nao encontrada.", idCompra)
                ));
    }

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

    public Compra adicionarTransacao(final Transacao transacao, final RepositoryCompraJPA repositoryCompraJPA) {
        if( transacaoCadastrada( transacao ) ) throw new EntityException("Transacao ja foi processada");
        if( transacaoConcluidaCadastrada() ) throw new EntityException("Transacao ja foi concluida com sucesso");
        transacoes.add( transacao );
        return repositoryCompraJPA.save( this );
    }

    public boolean transacaoConcluidaCadastrada(){
        return !transacoes
                .stream()
                .filter(Transacao::concluidaComSucesso)
                .collect(Collectors.toSet())
                .isEmpty();
    }

    private boolean transacaoCadastrada( final Transacao transacao ){
        return !transacoes
                .stream()
                .filter( t -> t.transacaoIgual( transacao ) )
                .collect(Collectors.toSet())
                .isEmpty();

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

    @NotNull
    @OneToMany( cascade = CascadeType.MERGE )
    private final Set<Transacao> transacoes = new HashSet<>();

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

    public Produto getProdutoDetalhes() {
        return produto;
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

    public Set<Transacao> getTransacoes() {
        return transacoes;
    }
}
