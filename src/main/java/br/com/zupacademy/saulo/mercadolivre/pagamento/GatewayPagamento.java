package br.com.zupacademy.saulo.mercadolivre.pagamento;

public enum GatewayPagamento {

    PAG_SEGURO{
        @Override
        public String redirectPorTipoDePagamento(final Long idDaCompra) {
            return "pagseguro.com?returnId=" + idDaCompra.toString() + "&redirectUrl=/pagseguro/" + idDaCompra;
        }
    },
    PAY_PAL{
        @Override
        public String redirectPorTipoDePagamento(final Long idDaCompra) {
            return "paypal.com?buyerId=" + idDaCompra.toString() + "&redirectUrl=/paypal/" + idDaCompra;
        }
    };

    public abstract String redirectPorTipoDePagamento(final Long idDaCompra);
}
