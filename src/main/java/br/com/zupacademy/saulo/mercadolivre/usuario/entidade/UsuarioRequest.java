package br.com.zupacademy.saulo.mercadolivre.usuario.entidade;

import br.com.zupacademy.saulo.mercadolivre.usuario.RepositoryUsuarioJPA;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UsuarioRequest {

    @Email(message = "Formato de email invalido!")
    private String email;
    @Size(min = 6, message = "Senha deve ter um mínimo de 6 caracteres!")
    private String senha;

    public UsuarioResponse cadastrar(RepositoryUsuarioJPA repositoryUsuarioJPA){
        return new UsuarioResponse(
                new Usuario(email, new BCryptPasswordEncoder().encode(senha))
                        .cadastrar(repositoryUsuarioJPA)
        );
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
