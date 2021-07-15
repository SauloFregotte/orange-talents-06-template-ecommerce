package br.com.zupacademy.saulo.mercadolivre.usuario;

import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryUsuarioJPA extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findFirstUsuarioByEmail(String email);

    Optional<Usuario> findByEmail(String email);
}
