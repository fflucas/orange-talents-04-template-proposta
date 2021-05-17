package br.com.zupacademy.fabio.proposta.card;

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
    ResponseEntity lockCard(@PathVariable("id") String id, RequestLockCard card);

    class RequestLockCard{
        static String sistemaResponsavel = "minha-aplicacao";

        public RequestLockCard() {
        }

        public String getSistemaResponsavel() {
            return sistemaResponsavel;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "${cards.request}/{id}/avisos")
    ResponseEntity travelNotice(@PathVariable("id") String id, RequestApiTravelNotice requestTravelNotice);

    class RequestApiTravelNotice{
        String destino;
        String validoAte;

        public RequestApiTravelNotice() {
        }

        public RequestApiTravelNotice(String destino, String validoAte) {
            this.destino = destino;
            this.validoAte = validoAte;
        }

        public String getDestino() {
            return destino;
        }

        public String getValidoAte() {
            return validoAte;
        }

        public void setDestino(String destino) {
            this.destino = destino;
        }

        public void setValidoAte(String validoAte) {
            this.validoAte = validoAte;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "${cards.request}/{id}/carteiras")
    ResponseAssociateWallet associateWallet(@PathVariable("id") String id, RequestAssociateWallet requestAssociateWallet);

    class RequestAssociateWallet{
        String email;
        String carteira;

        public RequestAssociateWallet(String email, String carteira) {
            this.email = email;
            this.carteira = carteira;
        }

        public String getEmail() {
            return email;
        }

        public String getCarteira() {
            return carteira;
        }
    }
}
