package br.com.zupacademy.fabio.proposta.card;

import br.com.zupacademy.fabio.proposta.biometry.Biometry;
import br.com.zupacademy.fabio.proposta.card.lock.LockCard;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    @OneToMany(mappedBy = "card")
    private List<Biometry> biometries = new ArrayList<>();
    @OneToMany(mappedBy = "card")
    private List<LockCard> lockCards = new ArrayList<>();


    @Deprecated
    public Card() {
    }

    public Card(String number) {
        this.number = number;
    }

    public void setBiometry(Biometry biometry) {
        biometries.add(biometry);
    }

    public List<Biometry> getBiometries() {
        return biometries;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setLock(LockCard lockCard) {
        this.lockCards.add(lockCard);
    }

    public List<LockCard> getLocks() {
        return lockCards;
    }
}
