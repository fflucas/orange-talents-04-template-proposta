package br.com.zupacademy.fabio.proposta.card.wallet;

public class RequestAssociateWallet {
    String email;
    String carteira;

    public RequestAssociateWallet(String email, String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }
}
