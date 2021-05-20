package br.com.zupacademy.fabio.proposta.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryCard extends JpaRepository<Card, Long> {
    Optional<Card> findByNumber(String number);
}
