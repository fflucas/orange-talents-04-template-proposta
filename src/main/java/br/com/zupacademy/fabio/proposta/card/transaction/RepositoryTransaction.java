package br.com.zupacademy.fabio.proposta.card.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryTransaction extends JpaRepository<Transaction, String> {
    // SELECT * FROM transaction t LEFT JOIN card c ON t.card_id = c.id WHERE c.number = "6224-8712-7454-8217" ORDER BY t.made_in DESC LIMIT 10;
    List<Transaction> findTenByCardNumberOrderByMadeInDesc(String number);
}
