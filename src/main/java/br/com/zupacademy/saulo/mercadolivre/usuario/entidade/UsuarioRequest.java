package br.com.zupacademy.saulo.mercadolivre.usuario.entidade;

import br.com.zupacademy.saulo.mercadolivre.usuario.RepositoryUsuarioJPA;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UsuarioRequest {

    @NotBlank
    @NotEmpty
    @Email(message = "Formato de email invalido!")
    private String email;

    @NotBlank
    @NotEmpty
    @Size(min = 6, message = "Senha deve ter um mínimo de 6 caracteres!")
    private String senha;

    public UsuarioResponse cadastrar(RepositoryUsuarioJPA repositoryUsuarioJPA){

        final String senhaEncoded = new BCryptPasswordEncoder().encode(senha);

        if(!Usuario.validarFormatoEmail(email)) throw new IllegalArgumentException();

        return new UsuarioResponse(
                new Usuario(email, senhaEncoded).cadastrar(repositoryUsuarioJPA)
        );
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
