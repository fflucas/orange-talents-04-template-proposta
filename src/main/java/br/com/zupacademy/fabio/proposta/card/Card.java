package br.com.zupacademy.fabio.proposta.card;

import br.com.zupacademy.fabio.proposta.biometry.Biometry;
import br.com.zupacademy.fabio.proposta.card.lock.LockCard;
import br.com.zupacademy.fabio.proposta.card.travelNotice.TravelNotice;
import br.com.zupacademy.fabio.proposta.card.wallet.Wallet;

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
    private boolean locked;
    @OneToMany(mappedBy = "card")
    private List<TravelNotice> travelList = new ArrayList<>();
    @OneToMany
    private List<Wallet> walletList = new ArrayList<>();

    @Deprecated
    public Card() {
    }

    public Card(String number) {
        this.number = number;
        this.locked = false;
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

    public List<LockCard> getLockCards() {
        return lockCards;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }

    public List<TravelNotice> getTravelList() {
        return travelList;
    }

    public void setTravelList(TravelNotice travelNotice) {
        this.travelList.add(travelNotice);
    }

    public void setWalletList(Wallet wallet) {
        this.walletList.add(wallet);
    }

    public List<Wallet> getWalletList() {
        return walletList;
    }

    public boolean hasWallet(String walletName){
        return walletList.stream().map(wallet -> wallet.getName().equals(walletName)).findFirst().orElse(false);
    }
}
