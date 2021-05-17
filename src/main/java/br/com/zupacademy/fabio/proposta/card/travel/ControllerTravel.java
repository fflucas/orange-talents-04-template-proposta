package br.com.zupacademy.fabio.proposta.card.travel;

import br.com.zupacademy.fabio.proposta.card.Card;
import br.com.zupacademy.fabio.proposta.shared.TransactionExecutor;
import br.com.zupacademy.fabio.proposta.shared.UserIp;
import br.com.zupacademy.fabio.proposta.shared.config.error.ApiErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class ControllerTravel {

    private TransactionExecutor transactionExecutor;
    @Autowired
    public ControllerTravel(TransactionExecutor transactionExecutor) {
        this.transactionExecutor = transactionExecutor;
    }

    @PostMapping(value = "/v1/cards/{id_card}/travel")
    public ResponseEntity<Object> create(
            @PathVariable Long id_card,
            @RequestBody @Valid RequestTravelNotice requestTravelNotice,
            @RequestHeader(value = HttpHeaders.USER_AGENT) String userAgent,
            HttpServletRequest servletRequest
    ){
        // checa se o cartao existe
        Card card = transactionExecutor.find(Card.class, id_card);
        if(card == null){
            return ResponseEntity.notFound().build();
        }

        // coleta e checa os dados da requisicao
        String userIp = UserIp.getIpAddress(servletRequest);
        if(StringUtils.isEmpty(userIp) || StringUtils.isEmpty(userAgent)){
            throw new ApiErrorException(
                    HttpStatus.BAD_REQUEST,
                    "User agent ou user ip não puderam ser definidos pela requisição."
            );
        }

        // cria e salva o aviso de viagem
        TravelNotice travelNotice = requestTravelNotice.convertToTravel(userAgent, userIp, card);
        transactionExecutor.commitAndSave(travelNotice);
        card.setTravelList(travelNotice);
        transactionExecutor.mergeAndCommit(card);

        return ResponseEntity.ok().build();
    }
}
