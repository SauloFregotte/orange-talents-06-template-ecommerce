package br.com.zupacademy.saulo.mercadolivre.pagamento.transacao.entidade;

import br.com.zupacademy.saulo.mercadolivre.pagamento.compra.RepositoryCompraJPA;
import br.com.zupacademy.saulo.mercadolivre.pagamento.compra.entidade.Compra;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TransacaoRequestPagseguro {

    public TransacaoRequestPagseguro(final String idPagamento,
                                     final StatusPagseguro status) {
        this.idPagamento = idPagamento;
        this.statusPagseguro = status;
    }

    @NotBlank
    private String idPagamento;

    @NotNull
    private StatusPagseguro statusPagseguro;

    public Compra achaCompraAdicionaTransacao(final Long idCompra,
                                              final RepositoryCompraJPA repositoryCompraJPA) {
        return Compra
                .findByID(idCompra, repositoryCompraJPA)
                .adicionarTransacao(
                        new Transacao(statusPagseguro.normaliza(), idPagamento ),
                        repositoryCompraJPA
                );
    }
}
