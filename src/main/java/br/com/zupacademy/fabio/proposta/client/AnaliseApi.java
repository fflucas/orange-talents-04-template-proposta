package br.com.zupacademy.fabio.proposta.client;

import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "analise", url = "${analise.hostname}")
public interface AnaliseApi {
    @RequestMapping(method = RequestMethod.POST, value = "${analise.request}")
    ResponseEntity<Object> requestAnalysisForCard(RequestAnaliseApi financialAnalysis) throws FeignException;
}
