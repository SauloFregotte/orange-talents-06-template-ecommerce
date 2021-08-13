package br.com.zupacademy.saulo.mercadolivre.pagamento.transacao.entidade;

import br.com.zupacademy.saulo.mercadolivre.pagamento.compra.RepositoryCompraJPA;
import br.com.zupacademy.saulo.mercadolivre.pagamento.compra.entidade.Compra;
import br.com.zupacademy.saulo.mercadolivre.pagamento.transacao.StatusTransacao;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class TransacaoRequestPaypal {

    public TransacaoRequestPaypal(final String idPagamento,
                                  final int status) {
        this.idPagamento = idPagamento;
        this.statusPaypal = status;
    }
    @NotBlank
    private String idPagamento;

    @Min(0)
    @Max(1)
    private int statusPaypal;

    public Compra achaCompraAdicionaTransacao(final Long idCompra,
                                              final RepositoryCompraJPA repositoryCompraJPA) {
        return Compra
                .findByID(idCompra, repositoryCompraJPA)
                .adicionarTransacao(
                        new Transacao(StatusTransacao.obterStatus(statusPaypal), idPagamento ),
                        repositoryCompraJPA
                );
    }

}
