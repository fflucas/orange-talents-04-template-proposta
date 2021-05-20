package br.com.zupacademy.fabio.proposta.card.transaction;

import br.com.zupacademy.fabio.proposta.card.Card;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    private String id;
    private BigDecimal value;
    private String establishment;
    @ManyToOne
    private Card card;
    private String userEmail;
    @Temporal(TemporalType.TIMESTAMP)
    private Date madeIn;

    @Deprecated
    public Transaction() {
    }

    public Transaction(String id, BigDecimal value, String establishment, Card card, String userEmail, Date madeIn) {
        this.id = id;
        this.value = value;
        this.establishment = establishment;
        this.card = card;
        this.userEmail = userEmail;
        this.madeIn = madeIn;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getEstablishment() {
        return establishment;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Date getMadeIn() {
        return madeIn;
    }

    public Long getCardId() {
        return card.getId();
    }
}
