package br.com.zupacademy.saulo.mercadolivre.config;

public class EmailFakeVendedor {

    public static void sendTo(final String paraQuem, final String conteudoMensagem, final String deQuem) {
        System.out.println("Para: " + paraQuem);
        System.out.println("Pergunta: " + conteudoMensagem);
        System.out.println("De: " + deQuem);
    }

}
