package br.com.zupacademy.fabio.proposta.biometry;

import br.com.zupacademy.fabio.proposta.card.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class ControllerBiometry {

    @PersistenceContext
    private EntityManager manager;
    private Logger logger = LoggerFactory.getLogger(ControllerBiometry.class);

    @PostMapping("/v1/cards/{id}/biometry")
    @Transactional
    public ResponseEntity<Object> create(@PathVariable Long id, @RequestBody @Valid RequestBiometry requestBiometry, UriComponentsBuilder builder){
        Card card = manager.find(Card.class, id);
        if (card == null){
            return ResponseEntity.notFound().build();
        }
        Biometry biometry = requestBiometry.convertToBiometry(card);
        manager.persist(biometry);
        card.setBiometry(biometry);
        logger.info("Biometry {} associated to card {}", biometry.getId(), card.getId());
        URI uri = builder.path("/v1/biometry/{id}").buildAndExpand(biometry.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
