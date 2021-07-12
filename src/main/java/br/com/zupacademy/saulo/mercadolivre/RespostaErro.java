package br.com.zupacademy.saulo.mercadolivre;

public class RespostaErro {

    private String mensagem;
    private int status;

    public RespostaErro(String mensagem, int status) {
        this.mensagem = mensagem;
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public int getStatus() {
        return status;
    }


}
