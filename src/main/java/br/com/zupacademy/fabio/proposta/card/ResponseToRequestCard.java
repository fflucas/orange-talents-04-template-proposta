package br.com.zupacademy.fabio.proposta.card;

import org.springframework.util.Assert;

public class ResponseToRequestCard {

    private String id;

    @Deprecated
    public ResponseToRequestCard() {
    }

    public ResponseToRequestCard(String id) {
        this.id = id;
        Assert.notNull(this.id, "Card number (id) can not be null");
    }

    public String getId() {
        return id;
    }

    public Card convertToCard(){
        return new Card(id);
    }
}
