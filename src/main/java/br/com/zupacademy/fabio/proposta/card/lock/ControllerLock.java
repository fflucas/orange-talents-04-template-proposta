package br.com.zupacademy.fabio.proposta.card.lock;

import br.com.zupacademy.fabio.proposta.card.Card;
import br.com.zupacademy.fabio.proposta.card.CardApi;
import br.com.zupacademy.fabio.proposta.card.ResponseCard;
import br.com.zupacademy.fabio.proposta.shared.TransactionExecutor;
import br.com.zupacademy.fabio.proposta.shared.UserIp;
import br.com.zupacademy.fabio.proposta.shared.config.error.ApiErrorException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
public class ControllerLock {

    private TransactionExecutor transactionExecutor;
    private CardApi cardApi;
    private Logger logger = LoggerFactory.getLogger(ControllerLock.class);
    @Autowired
    public ControllerLock(TransactionExecutor transactionExecutor, CardApi cardApi) {
        this.transactionExecutor = transactionExecutor;
        this.cardApi = cardApi;
    }

    @PostMapping("/v1/cards/{id_card}/locks")
    public ResponseEntity<Object> lockCard(
            @RequestHeader(value = HttpHeaders.USER_AGENT) String userAgent,
            @PathVariable Long id_card,
            HttpServletRequest servletRequest,
            UriComponentsBuilder builder
    ){
        Card card = transactionExecutor.find(Card.class, id_card);
        if(card == null){
            return ResponseEntity.notFound().build();
        }

        // checa se já existe bloqueio para o cartao
        // checa na api externa
        try{
            ResponseCard responseCard = cardApi.detailCard(card.getNumber());
            if(responseCard.isLocked()){
                // nao pode bloquear um cartao que ja esta bloqueado
                return ResponseEntity.unprocessableEntity().build();
            }
        }catch (FeignException fe){
            throw new ApiErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Problemas ao comunicar com o servidor de cartões."
            );
        }

        String userIp = UserIp.getIpAddress(servletRequest);
        if(StringUtils.isEmpty(userIp) || StringUtils.isEmpty(userAgent)){
            throw new ApiErrorException(
                    HttpStatus.BAD_REQUEST,
                    "User agent ou user ip não puderam ser definidos pela requisição."
            );
        }

        // solicita bloqueio na api externa
        try {
            ResponseEntity<?> responseToLockCard = cardApi.lockCard(card.getNumber(), new CardApi.RequestLockCard());
            if (responseToLockCard.getStatusCode().equals(HttpStatus.OK)){
                LockCard lockCard = new LockCard(userIp, userAgent, card);
                transactionExecutor.commitAndSave(lockCard);
                card.setLock(lockCard);
                card.setLocked(true);
                transactionExecutor.mergeAndCommit(card);
                logger.info("Card {} was successfully locked", card.getId());
                URI uri = builder.path("/v1/locks/{id}").buildAndExpand(lockCard.getId()).toUri();
                return ResponseEntity.created(uri).build();
            }
        }catch (FeignException fe){
            throw new ApiErrorException(
                    HttpStatus.BAD_REQUEST,
                    "O servidor de cartões não conseguiu bloquear o cartão"
            );
        }
        return ResponseEntity.badRequest().build();
    }
}
