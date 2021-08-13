package br.com.zupacademy.saulo.mercadolivre.disparocompra;

import br.com.zupacademy.saulo.mercadolivre.config.EmailFakeVendedor;
import br.com.zupacademy.saulo.mercadolivre.notafiscal.NotaFiscal;
import br.com.zupacademy.saulo.mercadolivre.pagamento.compra.entidade.Compra;
import br.com.zupacademy.saulo.mercadolivre.ranking.Ranking;

public class Disparo {

    private final NotaFiscal notaFiscal;

    private final Ranking ranking;

    public Disparo(final NotaFiscal notaFiscal, final Ranking ranking) {
        this.notaFiscal = notaFiscal;
        this.ranking = ranking;
    }

    public void disparar(final Compra compra){
        if( compra.transacaoConcluidaCadastrada() ){
            notaFiscal.processa( compra );
            ranking.processa( compra );
            EmailFakeVendedor.processa( compra );
        }else {
            EmailFakeVendedor.processaErro( compra );
        }
    }

}
