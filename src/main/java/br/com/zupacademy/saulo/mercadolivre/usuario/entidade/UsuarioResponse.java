package br.com.zupacademy.saulo.mercadolivre.usuario.entidade;

import java.time.LocalDateTime;

public class UsuarioResponse {

    UsuarioResponse(Usuario usuario){
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.localDateTime = usuario.getLocalDateTime();
    }

    private Long id;
    private String email;
    private  String senha;
    private LocalDateTime localDateTime;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
