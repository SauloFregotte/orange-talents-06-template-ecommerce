package br.com.zupacademy.saulo.mercadolivre.pagamento.compra;

import br.com.zupacademy.saulo.mercadolivre.pagamento.compra.entidade.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryCompraJPA extends JpaRepository<Compra, Long> {
}
