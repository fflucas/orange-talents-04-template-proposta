package br.com.zupacademy.fabio.proposta.card.wallet;

import br.com.zupacademy.fabio.proposta.card.Card;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Wallet {

    @Id
    private String id;
    private String name;
    @ManyToOne
    private Card card;

    @Deprecated
    public Wallet() {
    }

    public Wallet(String id, String nomeCarteira, Card card) {
        this.id = id;
        this.name = nomeCarteira;
        this.card = card;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
