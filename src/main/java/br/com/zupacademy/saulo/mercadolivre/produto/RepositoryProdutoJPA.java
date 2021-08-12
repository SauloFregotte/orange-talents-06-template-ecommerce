package br.com.zupacademy.saulo.mercadolivre.produto;

import br.com.zupacademy.saulo.mercadolivre.produto.entidade.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryProdutoJPA extends JpaRepository<Produto, Long> {

    Optional<Produto> findFirstByNomeAndValorAndQuantidadeNoEstoqueAndDescricao(String nome, double valor, int quantidade, String descricao);
}
