package br.com.zupacademy.saulo.mercadolivre.usuario.entidade;

import br.com.zupacademy.saulo.mercadolivre.security.PerfilDeAcesso;
import br.com.zupacademy.saulo.mercadolivre.usuario.RepositoryUsuarioJPA;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Entity
public class Usuario implements UserDetails {

    /**
     * Validação de formato de email precisa existir aqui já que o @Email
     * não compreende todos os formatos inválidos.
     * **/
    private static final String EMAIL_REGEX_ISO = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final java.util.regex.Pattern pattern = Pattern.compile(EMAIL_REGEX_ISO);

    private static boolean validarFormatoEmail(final String email){
        return pattern.matcher(email).find();
    }

    public Usuario(final String email, final String senha) {
        if(!validarFormatoEmail(email)) throw new IllegalArgumentException("Email não tem formato valido!");
        this.email = email;
        this.senha = senha;
    }

    public Usuario() {
    }

    @ManyToMany(fetch = FetchType.EAGER)
    private List<PerfilDeAcesso> perfis = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotEmpty
    private String email;

    @NotBlank
    @NotEmpty
    private String senha;

    @NotNull
    private LocalDateTime localDateTime = LocalDateTime.now();

    public Usuario cadastrar(RepositoryUsuarioJPA repositoryUsuarioJPA){
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perfis;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
