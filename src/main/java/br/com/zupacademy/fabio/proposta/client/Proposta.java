package br.com.zupacademy.fabio.proposta.client;

import br.com.zupacademy.fabio.proposta.card.Card;

import javax.persistence.*;
import java.math.BigDecimal;

enum PropostaStatus{NAO_ELEGIVEL, ELEGIVEL};

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String document;
    private String email;
    private String name;
    private String address;
    private BigDecimal salary;
    @Enumerated(EnumType.STRING)
    private PropostaStatus status;
    @OneToOne
    private Card card;

    public Proposta() {
    }

    public Proposta(String document, String email, String name, String address, BigDecimal salary) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public String getDocument() {
        return document;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public PropostaStatus getStatus() {
        return status;
    }

    public void setStatus(PropostaStatus status) {
        this.status = status;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
