package br.com.zupacademy.fabio.proposta.card.wallet;

public class ResponseAssociateWallet {
    private String resultado;
    private String id;

    public ResponseAssociateWallet() {
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSucess() {
        return resultado.equals("ASSOCIADA");
    }

    public String getId() {
        return id;
    }
}
