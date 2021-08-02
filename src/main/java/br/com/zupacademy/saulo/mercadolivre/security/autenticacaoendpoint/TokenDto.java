package br.com.zupacademy.saulo.mercadolivre.security.autenticacaoendpoint;

public class TokenDto {

    public TokenDto(String token, String tipo) {
        this.token = token;
        this.tipo = tipo;
    }

    private String token;
    private String tipo;

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }
}
