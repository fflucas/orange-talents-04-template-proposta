package br.com.zupacademy.fabio.proposta.client.card;

import org.springframework.util.Assert;

public class ResponseAccount {

    private String id;

    @Deprecated
    public ResponseAccount() {
    }

    public ResponseAccount(String id) {
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
