package br.com.zupacademy.fabio.proposta.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryProposta extends JpaRepository<Proposta, Long> {
}