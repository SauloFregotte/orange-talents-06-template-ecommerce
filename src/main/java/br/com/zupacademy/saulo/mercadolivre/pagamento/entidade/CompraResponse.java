package br.com.zupacademy.saulo.mercadolivre.pagamento.entidade;

public class CompraResponse {

    private final Compra compra;

    public CompraResponse(Compra compra) {
        this.compra = compra;
    }

    public Compra getCompra() {
        return compra;
    }

    public String emailDeQuemComprou() {
        return compra.getBuyer().getEmail();
    }

    public String emailDoVendedor() {
        return compra.getVendedor();
    }
}
