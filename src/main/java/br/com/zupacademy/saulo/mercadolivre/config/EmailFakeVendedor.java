package br.com.zupacademy.saulo.mercadolivre.config;

import br.com.zupacademy.saulo.mercadolivre.pagamento.compra.entidade.Compra;

public class EmailFakeVendedor {

    public static void sendTo(final String paraQuem, final String conteudoMensagem, final String deQuem) {
        System.out.println("Para: " + paraQuem);
        System.out.println("Pergunta: " + conteudoMensagem);
        System.out.println("De: " + deQuem);
    }

    public static void processa( final Compra compra ){
        System.out.println("Para: " + compra.getBuyer().getUsername());
        System.out.println("Conteudo: ");
        System.out.println(" # " + compra.getBuyer().getUsername());
        System.out.println(" # " + compra.getProdutoDetalhes().getNome());
        System.out.println(" # " + compra.getProdutoDetalhes().getValor());
        System.out.println("De: Mercado Livre");
        System.out.println("Compra realizadas com sucesso.");
    }

    public static void processaErro( final Compra compra ){
        System.out.println("Para: " + compra.getBuyer().getUsername());
        System.out.println("Conteudo: ");
        System.out.println(" # " + compra.getGatewayPagamento() );
        System.out.println("De: Mercado Livre");
        System.out.println("Erro ao realizar pagamento, tente novamente.");
    }

}
