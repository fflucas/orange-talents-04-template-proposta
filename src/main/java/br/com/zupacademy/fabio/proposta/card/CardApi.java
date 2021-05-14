package br.com.zupacademy.fabio.proposta.card;

import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
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
    ResponseToLockCard lockCard(@PathVariable("id") String id, RequestLockCard card);

    class RequestLockCard{
        static String sistemaResponsavel = "minha-aplicacao";

        public RequestLockCard() {
        }

        public String getSistemaResponsavel() {
            return sistemaResponsavel;
        }
    }
}
