package br.com.zupacademy.fabio.proposta.card;

import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "cards", url = "${cards.hostname}")
public interface RequestCard {
    @RequestMapping(method = RequestMethod.POST, value = "${cards.request}")
    ResponseEntity<Object> requestAnalysisForCard(RequestApi financialAnalysis) throws FeignException;
}
