package br.com.zupacademy.fabio.proposta.biometry;

import br.com.zupacademy.fabio.proposta.card.Card;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class Biometry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fingerPrint;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar created_at;
    @ManyToOne
    private Card card;

    @Deprecated
    public Biometry() {
    }

    public Biometry(String decodedFingerPrint, Card card) {
        this.fingerPrint = decodedFingerPrint;
        this.card = card;
    }

    public Long getId() {
        return id;
    }

    public Calendar getCreated_at() {
        return created_at;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }
}
