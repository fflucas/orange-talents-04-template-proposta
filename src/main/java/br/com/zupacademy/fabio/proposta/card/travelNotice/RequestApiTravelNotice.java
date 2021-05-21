package br.com.zupacademy.fabio.proposta.card.travelNotice;

public class RequestApiTravelNotice {
    String destino;
    String validoAte;

    public RequestApiTravelNotice() {
    }

    public RequestApiTravelNotice(String destino, String validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public String getValidoAte() {
        return validoAte;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setValidoAte(String validoAte) {
        this.validoAte = validoAte;
    }
}
