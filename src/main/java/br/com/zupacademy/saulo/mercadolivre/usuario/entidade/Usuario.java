package br.com.zupacademy.saulo.mercadolivre.usuario.entidade;

import br.com.zupacademy.saulo.mercadolivre.security.PerfilDeAcesso;
import br.com.zupacademy.saulo.mercadolivre.usuario.RepositoryUsuarioJPA;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @SuppressWarnings("RegExpRedundantEscape")
    private static final String EMAIL_REGEX_ISO = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final java.util.regex.Pattern pattern = Pattern.compile(EMAIL_REGEX_ISO);

    private static boolean validarFormatoEmail(final String email){
        Objects.requireNonNull(email, "Email não pode ser nulo!");
        return pattern.matcher(email).find();
    }

    public Usuario(final String email, final String senha) {
        if(senha.equals("")) throw new IllegalArgumentException("Senha não pode ser vazia!");
        Objects.requireNonNull(senha, "Senha não pode ser nula!");
        if(!validarFormatoEmail(email)) throw new IllegalArgumentException("Email não tem formato valido!");
        this.email = email;
        this.senha = senha;
    }

    public Usuario() {
    }

    @ManyToMany(fetch = FetchType.EAGER)
    private final List<PerfilDeAcesso> perfis = new ArrayList<>();

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
    private final LocalDateTime localDateTime = LocalDateTime.now();

    public Usuario cadastrar(RepositoryUsuarioJPA repositoryUsuarioJPA){
        verifyIfEmailIsUnique(repositoryUsuarioJPA);
        return repositoryUsuarioJPA.save(this);
    }

    private void verifyIfEmailIsUnique(RepositoryUsuarioJPA repositoryUsuarioJPA){
        repositoryUsuarioJPA.findFirstUsuarioByEmail(email)
                .ifPresent(e->{throw new EntityExistsException("Não é possivel salvar usuarios com email duplicado!");});
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(email, usuario.email) && Objects.equals(senha, usuario.senha) && Objects.equals(localDateTime, usuario.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, senha, localDateTime);
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
