package br.com.zupacademy.saulo.mercadolivre.pergunta;

public class EmailFakeVendedor {

    public static void sendTo(final String paraQuem, final String pergunta, final String deQuem){
        System.out.println("Para: " + paraQuem);
        System.out.println("Pergunta: " + pergunta);
        System.out.println("De: " + deQuem);
    }

}
