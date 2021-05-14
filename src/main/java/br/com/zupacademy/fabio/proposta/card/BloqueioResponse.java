package br.com.zupacademy.fabio.proposta.card;

import java.util.Calendar;

public class BloqueioResponse {
    private String id;
    private Calendar bloqueadoEm;
    private String sistemaResponsavel;
    private boolean ativo;

    public BloqueioResponse() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBloqueadoEm(Calendar bloqueadoEm) {
        this.bloqueadoEm = bloqueadoEm;
    }

    public void setSistemaResponsavel(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean getAtivo() {
        return ativo;
    }
}
