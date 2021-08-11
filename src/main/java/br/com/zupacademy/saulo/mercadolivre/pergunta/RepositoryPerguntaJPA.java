package br.com.zupacademy.saulo.mercadolivre.pergunta;

import br.com.zupacademy.saulo.mercadolivre.pergunta.entidade.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryPerguntaJPA extends JpaRepository<Pergunta, Long> {
}
