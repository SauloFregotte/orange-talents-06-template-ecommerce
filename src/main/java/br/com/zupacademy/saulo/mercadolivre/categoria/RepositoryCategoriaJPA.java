package br.com.zupacademy.saulo.mercadolivre.categoria;

import br.com.zupacademy.saulo.mercadolivre.categoria.entidade.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryCategoriaJPA extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findFirstCategoriaByNome(final String nome);
}
