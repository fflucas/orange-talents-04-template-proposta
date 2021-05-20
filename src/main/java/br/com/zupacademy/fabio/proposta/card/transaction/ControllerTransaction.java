package br.com.zupacademy.fabio.proposta.card.transaction;

import br.com.zupacademy.fabio.proposta.card.Card;
import br.com.zupacademy.fabio.proposta.shared.TransactionExecutor;
import br.com.zupacademy.fabio.proposta.shared.config.error.ApiErrorException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/cards/{id_card}/transactions")
public class ControllerTransaction {

    private final TransactionExecutor transactionExecutor;
    private final TransactionApi transactionApi;
    private final RepositoryTransaction repositoryTransaction;

    @Autowired
    public ControllerTransaction(TransactionExecutor transactionExecutor, TransactionApi transactionApi, RepositoryTransaction repositoryTransaction) {
        this.transactionExecutor = transactionExecutor;
        this.transactionApi = transactionApi;
        this.repositoryTransaction = repositoryTransaction;
    }

    @PostMapping
    public ResponseEntity<Object> create(
            @PathVariable Long id_card,
            @AuthenticationPrincipal Jwt jwt
    ){
        Card card = transactionExecutor.find(Card.class, id_card);
        if(card == null){
            return ResponseEntity.notFound().build();
        }

        String email = (String) jwt.getClaims().get("email");
        System.out.println(email);

        TransactionApi.RequestTransaction requestTransaction = new TransactionApi.RequestTransaction(card.getNumber(), email);
        try{
            transactionApi.startTransactions(requestTransaction);
        }catch (FeignException fe){
            fe.printStackTrace();
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Algo saiu errado com o servidor de transações");
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Object> stop(@PathVariable Long id_card){
        Card card = transactionExecutor.find(Card.class, id_card);
        if(card == null){
            return ResponseEntity.notFound().build();
        }

        try{
            transactionApi.stopTransactions(card.getNumber());
        }catch (FeignException fe){
            fe.printStackTrace();
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Algo saiu errado com o servidor de transações");
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Object> listTen(@PathVariable Long id_card){
        Card card = transactionExecutor.find(Card.class, id_card);
        if(card == null){
            return ResponseEntity.notFound().build();
        }

        List<Transaction> lastTransactions = repositoryTransaction.findTenByCardNumberOrderByMadeInDesc(card.getNumber());
        return ResponseEntity.ok(lastTransactions);
    }

}
