package br.com.zupacademy.fabio.proposta.card.travel;

import br.com.zupacademy.fabio.proposta.card.Card;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

public class RequestTravelNotice {

    @NotBlank
    private String destiny;
    @NotNull
    @FutureOrPresent
    private Calendar end_in;

    public RequestTravelNotice() {
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public void setEnd_in(Calendar end_in) {
        this.end_in = end_in;
    }

    public TravelNotice convertToTravel(String userAgent, String userIp, Card card){
        return new TravelNotice(destiny, end_in, userAgent, userIp, card);
    }
}
