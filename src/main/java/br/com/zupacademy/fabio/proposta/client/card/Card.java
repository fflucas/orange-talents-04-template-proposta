package br.com.zupacademy.fabio.proposta.client.card;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Card {
    @Id
    private String id;

    @Deprecated
    public Card() {
    }

    public Card(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
