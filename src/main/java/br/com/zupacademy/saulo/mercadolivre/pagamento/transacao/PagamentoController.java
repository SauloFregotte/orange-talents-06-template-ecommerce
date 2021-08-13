package br.com.zupacademy.saulo.mercadolivre.pagamento.transacao;

import br.com.zupacademy.saulo.mercadolivre.disparocompra.Disparo;
import br.com.zupacademy.saulo.mercadolivre.notafiscal.NotaFiscal;
import br.com.zupacademy.saulo.mercadolivre.pagamento.compra.RepositoryCompraJPA;
import br.com.zupacademy.saulo.mercadolivre.pagamento.compra.entidade.Compra;
import br.com.zupacademy.saulo.mercadolivre.pagamento.transacao.entidade.Transacao;
import br.com.zupacademy.saulo.mercadolivre.pagamento.transacao.entidade.TransacaoRequestPagseguro;
import br.com.zupacademy.saulo.mercadolivre.pagamento.transacao.entidade.TransacaoRequestPaypal;
import br.com.zupacademy.saulo.mercadolivre.ranking.Ranking;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
public class PagamentoController {

    private final RepositoryCompraJPA repositoryCompraJPA;

    private final NotaFiscal notaFiscal;

    private final Ranking ranking;

    PagamentoController(final RepositoryCompraJPA repositoryCompraJPA,
                        final NotaFiscal notaFiscal,
                        final Ranking ranking) {
        this.repositoryCompraJPA = repositoryCompraJPA;
        this.notaFiscal = notaFiscal;
        this.ranking = ranking;
    }

    @PostMapping(value = "/pag-seguro/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StatusTransacao cadastroPagamentoPagseguro(@RequestBody @Valid TransacaoRequestPagseguro pagamentoRequest,
                                           @PathVariable("id") Long idCompra) {
        final Compra compra = pagamentoRequest.achaCompraAdicionaTransacao(idCompra, repositoryCompraJPA);
        new Disparo(notaFiscal, ranking).disparar(compra);
        return new ArrayList<>(compra.getTransacoes()).get(0).getStatusTransacao();
    }

    @PostMapping(value = "/pay-pal/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StatusTransacao cadastroPagamentoPaypal(@RequestBody @Valid TransacaoRequestPaypal pagamentoRequest,
                                        @PathVariable("id") Long idCompra) {
        final Compra compra = pagamentoRequest.achaCompraAdicionaTransacao(idCompra, repositoryCompraJPA);
        new Disparo(notaFiscal, ranking).disparar(compra);
        return new ArrayList<>(compra.getTransacoes()).get(0).getStatusTransacao();
    }

}
