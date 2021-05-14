package br.com.zupacademy.fabio.proposta.card;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ResponseCard {
    private String id;
    private Calendar emitidoEm;
    private String titular;
    private List<BloqueioResponse> bloqueios;

    public ResponseCard() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmitidoEm(Calendar emitidoEm) {
        this.emitidoEm = emitidoEm;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public void setBloqueios(List<BloqueioResponse> bloqueios) {
        this.bloqueios = bloqueios;
    }

    public boolean isLocked() {
        Optional<Boolean> first = bloqueios.stream().map(bloqueio -> {
            return bloqueio.getAtivo();
        }).findFirst();
        return first.isPresent();
    }
}
