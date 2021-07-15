package br.com.zupacademy.saulo.mercadolivre.security;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PerfilDeAcesso implements GrantedAuthority {

    PerfilDeAcesso(final String nome){
        this.nome = nome;
    }

    public PerfilDeAcesso() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String getAuthority() {
        return nome;
    }
}
