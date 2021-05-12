package br.com.zupacademy.fabio.proposta.card;

import br.com.zupacademy.fabio.proposta.client.Proposta;
import br.com.zupacademy.fabio.proposta.shared.TransactionExecutor;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardsSchedule {

    private RequestAccount requestAccount;
    private TransactionExecutor transactionExecutor;
    private final Logger logger = LoggerFactory.getLogger(CardsSchedule.class);

    @Autowired
    public CardsSchedule(RequestAccount requestAccount, TransactionExecutor transactionExecutor) {
        this.requestAccount = requestAccount;
        this.transactionExecutor = transactionExecutor;
    }

    @Scheduled(fixedDelay = 10000)
    public void checkForCards(){
        List<Long> id_propostas = transactionExecutor.findPropostaElegivelSemNumeroCartao();
        if(id_propostas.size()>0){
            logger.info("{} propostas waiting for card numbers", id_propostas.size());
        }

        for( Long id_proposta : id_propostas){
            try{
                ResponseAccount responseAccount = requestAccount.requestAccount(id_proposta.toString());
                Card card = transactionExecutor.commitAndSave(responseAccount.convertToCard());
                Proposta proposta = transactionExecutor.find(Proposta.class, id_proposta);
                proposta.setCardNumber(card.getNumber());
                transactionExecutor.mergeAndCommit(proposta);
                logger.info("Proposta {} received card {}", proposta.getId(), proposta.getCardNumber());
            }catch (FeignException fe){
                logger.info("Proposta {} has not yet received the card number", id_proposta);
                System.out.println(fe.getLocalizedMessage());
            }
        }
    }
}
