package br.com.zupacademy.fabio.proposta.client;

import br.com.zupacademy.fabio.proposta.card.RequestApi;
import br.com.zupacademy.fabio.proposta.card.RequestCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryProposta extends JpaRepository<Proposta, Long> {
    @Query("SELECT document, name, id FROM Proposta WHERE cardNumber IS NULL AND status = 'ELEGIVEL'")
    List<RequestApi> findPropostaElegivelSemNumeroCartao();
}
