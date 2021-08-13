package br.com.zupacademy.saulo.mercadolivre.pagamento.compra.entidade;

import br.com.zupacademy.saulo.mercadolivre.pagamento.GatewayPagamento;
import br.com.zupacademy.saulo.mercadolivre.pagamento.compra.RepositoryCompraJPA;
import br.com.zupacademy.saulo.mercadolivre.produto.RepositoryProdutoJPA;
import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;

import javax.validation.constraints.Positive;

public class CompraRequest {

    public CompraRequest(final GatewayPagamento gatewayPagamento,
                         final int quantidade, final Long idProduto) {
        this.tipoDePagamento = gatewayPagamento;
        this.quantidade = quantidade;
        this.produto = idProduto;
    }

    private GatewayPagamento tipoDePagamento;
    @Positive(message = "A quantidade deve ser positiva!")
    private int quantidade;
    private Long produto;

    public CompraResponse buy(final RepositoryCompraJPA repositoryCompraJPA,
                              final RepositoryProdutoJPA repositoryProdutoJPA,
                              final Usuario userLogged) {

        return new CompraResponse(
                new Compra(
                        quantidade,
                        repositoryProdutoJPA.getById(produto),
                        userLogged,
                        tipoDePagamento
                ).registrarCompra(repositoryProdutoJPA, repositoryCompraJPA)
        );
    }

    public GatewayPagamento getTipoDePagamento() {
        return tipoDePagamento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Long getProduto() {
        return produto;
    }
}
