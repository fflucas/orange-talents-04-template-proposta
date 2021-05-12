package br.com.zupacademy.fabio.proposta.card;

import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "account", url = "${account.hostname}")
public interface RequestAccount {
    @RequestMapping(method = RequestMethod.GET, value = "${account.request}?idProposta={proposta_id}")
    ResponseAccount requestAccount(@PathVariable("proposta_id") String proposta_id) throws FeignException;
}
