package br.com.zupacademy.fabio.proposta.card.travelNotice;

import br.com.zupacademy.fabio.proposta.card.Card;
import br.com.zupacademy.fabio.proposta.card.CardApi;
import br.com.zupacademy.fabio.proposta.utils.TransactionExecutor;
import br.com.zupacademy.fabio.proposta.utils.UserIp;
import br.com.zupacademy.fabio.proposta.shared.error.ApiErrorException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;

@RestController
public class ControllerTravel {

    private TransactionExecutor transactionExecutor;
    private CardApi cardApi;
    @Autowired
    public ControllerTravel(TransactionExecutor transactionExecutor, CardApi cardApi) {
        this.transactionExecutor = transactionExecutor;
        this.cardApi = cardApi;
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

        try{
            // cria o aviso de viagem e comunica o sistema legado
            TravelNotice travelNotice = requestTravelNotice.convertToTravel(userAgent, userIp, card);
            RequestApiTravelNotice requestApiTravelNotice = new RequestApiTravelNotice(
                    travelNotice.getDestiny(),
                    new SimpleDateFormat("yyyy-MM-dd").format(travelNotice.getEnd_in().getTime())
            );
            ResponseEntity responseEntity = cardApi.travelNotice(card.getNumber(), requestApiTravelNotice);
            if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
                // salva tudo
                transactionExecutor.commitAndSave(travelNotice);
                card.setTravelList(travelNotice);
                transactionExecutor.mergeAndCommit(card);
            }else {
                throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Algo saiu errado e não foi possível fazer a requisição");
            }
        }catch (FeignException fe){
            System.out.println(fe.getLocalizedMessage());
            fe.printStackTrace();
            throw new ApiErrorException(
                    HttpStatus.BAD_REQUEST,
                    "O servidor de cartões não conseguiu realizar o aviso de viagem para o cartão"
            );
        }

        return ResponseEntity.ok().build();
    }
}
