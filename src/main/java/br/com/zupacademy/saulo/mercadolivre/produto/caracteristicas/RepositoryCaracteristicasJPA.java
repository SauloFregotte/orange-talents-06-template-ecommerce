package br.com.zupacademy.saulo.mercadolivre.produto.caracteristicas;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryCaracteristicasJPA extends JpaRepository<Caracteristicas, Long> {

    Optional<Caracteristicas> findCaracteristicasByNome(String nome);

}
