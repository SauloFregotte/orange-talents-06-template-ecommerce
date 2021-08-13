package br.com.zupacademy.saulo.mercadolivre.pagamento.transacao;

public enum StatusTransacao {

    SUCESSO,
    ERRO;

    public static StatusTransacao obterStatus(final int statusPaypal) {
        if( statusPaypal == 1 )
            return SUCESSO;
        return ERRO;
    }

}
