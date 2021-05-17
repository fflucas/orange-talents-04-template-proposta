package br.com.zupacademy.fabio.proposta.card.wallet;

import br.com.zupacademy.fabio.proposta.card.Card;
import br.com.zupacademy.fabio.proposta.card.CardApi;
import br.com.zupacademy.fabio.proposta.client.Proposta;
import br.com.zupacademy.fabio.proposta.client.RepositoryProposta;
import br.com.zupacademy.fabio.proposta.shared.TransactionExecutor;
import br.com.zupacademy.fabio.proposta.shared.config.error.ApiErrorException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class ControllerWallet {

    private TransactionExecutor transactionExecutor;
    private RepositoryProposta repositoryProposta;
    private CardApi cardApi;
    @Autowired
    public ControllerWallet(TransactionExecutor transactionExecutor, RepositoryProposta repositoryProposta, CardApi cardApi) {
        this.transactionExecutor = transactionExecutor;
        this.repositoryProposta = repositoryProposta;
        this.cardApi = cardApi;
    }

    @PostMapping(value = "/v1/cards/{id_card}/wallets")
    public ResponseEntity<Object> create(
            @PathVariable Long id_card,
            @RequestBody @Valid RequestWallet requestWallet,
            UriComponentsBuilder builder
    ){
        Card card = transactionExecutor.find(Card.class, id_card);
        if(card == null){
            return ResponseEntity.notFound().build();
        }

        String nomeCarteira = requestWallet.getCarteira();
        // checa se o cartão já tem associação com a carteira
//        if(card.hasWallet(nomeCarteira)){
//            return ResponseEntity.unprocessableEntity().build();
//        }


        Proposta proposta = repositoryProposta.findByCardId(id_card);
        CardApi.RequestAssociateWallet requestAssociateWallet = new CardApi.RequestAssociateWallet(
                proposta.getEmail(),
                nomeCarteira
        );

        ResponseAssociateWallet responseAssociateWallet;
        try {
            responseAssociateWallet = cardApi.associateWallet(
                    card.getNumber(),
                    requestAssociateWallet
            );
        }catch (FeignException fe){
            fe.printStackTrace();
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, "API de cartões não conseguiu associar carteira para o cartão");
        }

        Wallet wallet;
        if (responseAssociateWallet.isSucess()){
            wallet = new Wallet(responseAssociateWallet.getId(), nomeCarteira, card);
            transactionExecutor.commitAndSave(wallet);
            card.setWalletList(wallet);
            transactionExecutor.mergeAndCommit(card);
        }else {
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, "API de cartões respondeu não a associação da carteira");
        }

        URI uri = builder.path("/v1/wallets/{id}").buildAndExpand(wallet.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
