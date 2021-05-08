package br.com.zupacademy.fabio.proposta.client.card;

import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "cards", url = "${cards.hostname}")
public interface RequestCard {
    @RequestMapping(method = RequestMethod.POST, value = "${cards.request}")
    ResponseEntity<Object> requestAnalysisForCard(RequestFinancialAnalysis financialAnalysis) throws FeignException;

    class RequestFinancialAnalysis{
        String documento;
        String nome;
        String idProposta;

        public RequestFinancialAnalysis(String documento, String nome, String idProposta) {
            this.documento = documento;
            this.nome = nome;
            this.idProposta = idProposta;
        }

        public String getDocumento() {
            return documento;
        }
        public String getNome() {
            return nome;
        }
        public String getIdProposta() {
            return idProposta;
        }
    }
}
