package br.com.zupacademy.fabio.proposta.card.wallet;

import javax.validation.constraints.NotBlank;

public class RequestWallet {
    @NotBlank
    private String carteira;

    public RequestWallet() {
    }

    public void setCarteira(String carteira) {
        this.carteira = carteira;
    }

    public String getCarteira() {
        return carteira;
    }
}
