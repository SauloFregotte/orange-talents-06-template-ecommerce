package br.com.zupacademy.saulo.mercadolivre.pagamento;

import br.com.zupacademy.saulo.mercadolivre.pagamento.entidade.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryCompraJPA extends JpaRepository<Compra, Long> {
}
