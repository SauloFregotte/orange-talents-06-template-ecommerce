package br.com.zupacademy.saulo.mercadolivre.pagamento.transacao.entidade;

import br.com.zupacademy.saulo.mercadolivre.pagamento.transacao.StatusTransacao;

public enum StatusPagseguro {

        SUCESSO,
        ERRO;

        StatusTransacao normaliza() {
            if( this.equals( SUCESSO ) )
                return StatusTransacao.SUCESSO;
            return StatusTransacao.ERRO;
        }
}
