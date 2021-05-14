package br.com.zupacademy.fabio.proposta.card.lock;

import br.com.zupacademy.fabio.proposta.card.Card;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class LockCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdAt;
    private String clientIp;
    private String userAgent;
    @ManyToOne
    private Card card;

    @Deprecated
    public LockCard() {
    }

    public LockCard(String clientIp, String userAgent, Card card) {
        this.clientIp = clientIp;
        this.userAgent = userAgent;
        this.card = card;
    }

    public Long getId() {
        return id;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public String getClientIp() {
        return clientIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    @Override
    public String toString() {
        return "Lock{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", clientIp='" + clientIp + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", card=" + card +
                '}';
    }
}
