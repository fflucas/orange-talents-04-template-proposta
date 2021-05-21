package br.com.zupacademy.fabio.proposta.card.travelNotice;

import br.com.zupacademy.fabio.proposta.card.Card;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class TravelNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String destiny;
    private Calendar end_in;
    private String userAgent;
    private String userIp;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar created_at;
    @ManyToOne
    private Card card;


    @Deprecated
    public TravelNotice() {
    }

    public TravelNotice(String destiny, Calendar end_in, String userAgent, String userIp, Card card) {
        this.destiny = destiny;
        this.end_in = end_in;
        this.userAgent = userAgent;
        this.userIp = userIp;
        this.card = card;
    }

    public Long getId() {
        return id;
    }

    public String getDestiny() {
        return destiny;
    }

    public Calendar getEnd_in() {
        return end_in;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getUserIp() {
        return userIp;
    }

    public Calendar getCreated_at() {
        return created_at;
    }
}
