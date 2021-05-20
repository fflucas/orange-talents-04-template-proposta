package br.com.zupacademy.fabio.proposta.card.transaction;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "${transaction.hostname}")
public interface TransactionApi {

    @RequestMapping(method = RequestMethod.POST, value = "${transaction.request}")
    void startTransactions(RequestTransaction requestTransaction);

    class RequestTransaction {
        String id;
        String email;

        public RequestTransaction(String id, String email) {
            this.id = id;
            this.email = email;
        }

        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "${transaction.request}/{id_card}")
    void stopTransactions(@PathVariable(name = "id_card") String id_card);
}
