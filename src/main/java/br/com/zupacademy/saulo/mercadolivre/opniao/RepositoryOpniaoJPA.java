package br.com.zupacademy.saulo.mercadolivre.opniao;

import br.com.zupacademy.saulo.mercadolivre.opniao.entidade.Opniao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryOpniaoJPA extends JpaRepository<Opniao, Long> {
}
