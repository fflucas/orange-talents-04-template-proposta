package br.com.zupacademy.fabio.proposta.card.transaction;

import java.math.BigDecimal;

public class EventTransaction {

    private String id;

    private BigDecimal valor;

    public EventTransaction() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "EventTransaction{" +
                "id='" + id + '\'' +
                ", valor=" + valor +
                '}';
    }
}
