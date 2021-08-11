package br.com.zupacademy.saulo.mercadolivre.pergunta.entidade;

import java.time.LocalDateTime;

public class PerguntaResponse {

    private final Pergunta pergunta;

    public PerguntaResponse(Pergunta pergunta) {
        this.pergunta = pergunta;
    }

    public Long getId(){
        return pergunta.getId();
    }

    public LocalDateTime getLocalDateTime(){
        return pergunta.getLocalDateTime();
    }

    public String pergunta() {
        return pergunta.getTitulo();
    }

    public String emailDeQuemPerguntou() {
        return pergunta.getUsuario().getEmail();
    }

    public String emailDoVendedor() {
        return pergunta.getProduto().getUsuario().getEmail();
    }
}