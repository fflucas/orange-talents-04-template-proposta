package br.com.zupacademy.fabio.proposta.client;

import br.com.zupacademy.fabio.proposta.card.RequestApi;
import br.com.zupacademy.fabio.proposta.card.RequestCard;
import br.com.zupacademy.fabio.proposta.shared.TransactionExecutor;
import br.com.zupacademy.fabio.proposta.shared.config.error.ApiErrorException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/v1/propostas")
public class ControllerProposta {

    private TransactionExecutor transactionExecutor;
    private RepositoryProposta repositoryProposta;
    private RequestCard requestCard;
    private final Logger logger = LoggerFactory.getLogger(ControllerProposta.class);

    @Autowired
    public ControllerProposta(TransactionExecutor transactionExecutor, RepositoryProposta repositoryProposta, RequestCard requestCard) {
        this.transactionExecutor = transactionExecutor;
        this.repositoryProposta = repositoryProposta;
        this.requestCard = requestCard;
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody @Valid RequestProposta requestProposta, UriComponentsBuilder uriBuilder){
        Proposta proposta = requestProposta.convertToProposta();
        transactionExecutor.commitAndSave(proposta);

        RequestApi requestApi = new RequestApi(
                proposta.getDocument(),
                proposta.getName(),
                proposta.getId().toString()
        );
        try{
            ResponseEntity<Object> objectResponseEntity = requestCard.requestAnalysisForCard(requestApi);
            proposta.setStatus(
                    (objectResponseEntity.getStatusCode() == HttpStatus.CREATED) ? PropostaStatus.ELEGIVEL : PropostaStatus.NAO_ELEGIVEL
            );
        }catch (FeignException fe){
            if (fe.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()){
                proposta.setStatus(PropostaStatus.NAO_ELEGIVEL);
            }else{
                transactionExecutor.delete(proposta);
                logger.error("Proposta {} failed to receive status, rolling back", proposta.getId());
                throw new ApiErrorException(HttpStatus.INTERNAL_SERVER_ERROR, fe.getLocalizedMessage());
            }
        }catch (Exception e){
            transactionExecutor.delete(proposta);
        }
        transactionExecutor.mergeAndCommit(proposta);
        logger.info("Proposta {} received status {}", proposta.getId(), proposta.getStatus());

        URI uri = uriBuilder.path("/v1/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> show(@PathVariable Long id){
        Optional<Proposta> proposta = repositoryProposta.findById(id);
        if(proposta.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(proposta);
    }
}
