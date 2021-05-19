package br.com.zupacademy.fabio.proposta.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryProposta extends JpaRepository<Proposta, Long> {
    Proposta findByCardId(Long id_card);
    Optional<Proposta> findByDocument(String document);
}
