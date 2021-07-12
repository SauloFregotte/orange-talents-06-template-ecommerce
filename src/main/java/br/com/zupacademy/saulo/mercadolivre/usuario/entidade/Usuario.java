package br.com.zupacademy.saulo.mercadolivre.usuario.entidade;

import br.com.zupacademy.saulo.mercadolivre.usuario.RepositoryUsuarioJPA;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.converter.json.GsonBuilderUtils;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Entity
public class Usuario {

    private static final String EMAIL_REGEX_ISO = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final java.util.regex.Pattern pattern = Pattern.compile(EMAIL_REGEX_ISO);

    public static boolean validarFormatoEmail(final String email){
        if(email.trim().equals("")) return false;
        return pattern.matcher(email).find();
    }

    public Usuario(final String email, final String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Usuario() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotEmpty
    @Email
    private String email;

    @NotBlank
    @NotEmpty
    @Size(min = 6)
    private String senha;

    @NotNull
    private LocalDateTime localDateTime = LocalDateTime.now();

    public Usuario cadastrar(RepositoryUsuarioJPA repositoryUsuarioJPA){
        System.out.println(senha);
        verifyIfEmailIsUnique(repositoryUsuarioJPA);
        return repositoryUsuarioJPA.save(this);
    }

    private void verifyIfEmailIsUnique(RepositoryUsuarioJPA repositoryUsuarioJPA){
        repositoryUsuarioJPA.findFirstUsuarioByEmail(email)
                .ifPresent(e->{throw new EntityExistsException("Não é possivel salvar usuarios com email duplicado!");});
    }

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
