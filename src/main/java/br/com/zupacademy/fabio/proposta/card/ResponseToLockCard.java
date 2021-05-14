package br.com.zupacademy.fabio.proposta.card;

public class ResponseToLockCard {
    private String resultado;

    public ResponseToLockCard() {
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public boolean isSuccess(){
        return resultado.equals("BLOQUEADO");
    }
}
