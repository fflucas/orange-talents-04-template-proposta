package br.com.zupacademy.fabio.proposta.card;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
@RequestMapping("/v1/cards")
public class ControllerCard {

    @PersistenceContext
    private EntityManager manager;

    @GetMapping("/{id}")
    public ResponseEntity<Object> show(@PathVariable Long id){
        Card card = manager.find(Card.class, id);
        if(card == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(card);
    }
}
