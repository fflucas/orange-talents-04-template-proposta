package br.com.zupacademy.fabio.proposta.client.card;

import br.com.zupacademy.fabio.proposta.client.Proposta;
import br.com.zupacademy.fabio.proposta.shared.TransactionExecutor;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardsSchedule {

    private RequestAccount requestAccount;
    private TransactionExecutor transactionExecutor;

    @Autowired
    public CardsSchedule(RequestAccount requestAccount, TransactionExecutor transactionExecutor) {
        this.requestAccount = requestAccount;
        this.transactionExecutor = transactionExecutor;
    }

    @Scheduled(fixedDelay = 10000)
    public void checkForCards(){
        List<Long> id_propostas = transactionExecutor.findPropostaElegivelSemNumeroCartao();

        for( Long id_proposta : id_propostas){
            try{
                ResponseAccount responseAccount = requestAccount.requestAccount(id_proposta.toString());
                Card card = transactionExecutor.commitAndSave(responseAccount.convertToCard());
                Proposta proposta = transactionExecutor.find(Proposta.class, id_proposta);
                proposta.setCardNumber(card.getId());
                transactionExecutor.mergeAndCommit(proposta);
            }catch (FeignException fe){
                System.out.println(fe.getLocalizedMessage());
            }
        }
    }
}
