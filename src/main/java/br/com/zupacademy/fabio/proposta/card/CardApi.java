package br.com.zupacademy.fabio.proposta.card;

import br.com.zupacademy.fabio.proposta.card.lock.RequestLockCard;
import br.com.zupacademy.fabio.proposta.card.travelNotice.RequestApiTravelNotice;
import br.com.zupacademy.fabio.proposta.card.wallet.RequestAssociateWallet;
import br.com.zupacademy.fabio.proposta.card.wallet.ResponseAssociateWallet;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "cards", url = "${cards.hostname}")
public interface CardApi {
    @RequestMapping(method = RequestMethod.GET, value = "${cards.request}?idProposta={proposta_id}")
    ResponseToRequestCard requestCard(@PathVariable("proposta_id") String proposta_id) throws FeignException;

    @RequestMapping(method = RequestMethod.GET, value = "${cards.request}/{id}")
    ResponseCard detailCard(@PathVariable("id") String id);

    @RequestMapping(method = RequestMethod.POST, value = "${cards.request}/{id}/bloqueios")
    ResponseEntity<?> lockCard(@PathVariable("id") String id, RequestLockCard card);

    @RequestMapping(method = RequestMethod.POST, value = "${cards.request}/{id}/avisos")
    ResponseEntity<?> travelNotice(@PathVariable("id") String id, RequestApiTravelNotice requestTravelNotice);

    @RequestMapping(method = RequestMethod.POST, value = "${cards.request}/{id}/carteiras")
    ResponseAssociateWallet associateWallet(@PathVariable("id") String id, RequestAssociateWallet requestAssociateWallet);

}
