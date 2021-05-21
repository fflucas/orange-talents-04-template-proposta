package br.com.zupacademy.fabio.proposta.card.transaction;

import br.com.zupacademy.fabio.proposta.card.RepositoryCard;
import br.com.zupacademy.fabio.proposta.utils.TransactionExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ListenerTransaction {

    private RepositoryCard repositoryCard;
    private TransactionExecutor transactionExecutor;
    private Logger logger = LoggerFactory.getLogger(Transaction.class);

    @Autowired
    public ListenerTransaction(RepositoryCard repositoryCard, TransactionExecutor transactionExecutor) {
        this.repositoryCard = repositoryCard;
        this.transactionExecutor = transactionExecutor;
    }

    @KafkaListener(topics = "${spring.kafka.topic.transactions}")
    public void listener(ResponseEventTransaction eventoDeTransacao) {
        saveTransaction(eventoDeTransacao);
    }

    private void saveTransaction(ResponseEventTransaction eventTransaction){
        Transaction transaction = eventTransaction.convertToTransaction(repositoryCard);
        transactionExecutor.commitAndSave(transaction);
        logger.info("Transaction received for card {}", transaction.getCardId());
    }

}
