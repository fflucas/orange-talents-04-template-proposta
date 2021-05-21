package br.com.zupacademy.fabio.proposta.card.transaction;

import br.com.zupacademy.fabio.proposta.card.Card;
import br.com.zupacademy.fabio.proposta.card.RepositoryCard;
import br.com.zupacademy.fabio.proposta.shared.error.ApiErrorException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

public class ResponseEventTransaction {

    private String id;
    private BigDecimal valor;
    private ResponseEventTransactionEstabelecimento estabelecimento;
    private ResponseEventTransactionCartao cartao;
    private Date efetivadaEm;

    public ResponseEventTransaction() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setEstabelecimento(ResponseEventTransactionEstabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public void setCartao(ResponseEventTransactionCartao cartao) {
        this.cartao = cartao;
    }

    public void setEfetivadaEm(Date efetivadaEm) {
        this.efetivadaEm = efetivadaEm;
    }

    @Override
    public String toString() {
        return "ResponseEventTransaction{" +
                "id='" + id + '\'' +
                ", valor=" + valor +
                ", estabelecimento=" + estabelecimento.toString() +
                ", cartao=" + cartao.toString() +
                ", efetivadaEm=" + efetivadaEm.getTime() +
                '}';
    }

    public Transaction convertToTransaction(RepositoryCard repositoryCard) {
        Optional<Card> card = repositoryCard.findByNumber(cartao.id);
        if(card.isEmpty()){
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Cartão retornado pela transação não foi encontrado na API");
        }
        System.out.println(this.toString());
        return new Transaction(
                id,
                valor,
                estabelecimento.toString(),
                card.get(),
                cartao.email,
                efetivadaEm);
    }

    private static class ResponseEventTransactionEstabelecimento {
        private String nome, cidade, endereco;

        public ResponseEventTransactionEstabelecimento() {
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public void setCidade(String cidade) {
            this.cidade = cidade;
        }

        public void setEndereco(String endereco) {
            this.endereco = endereco;
        }

        @Override
        public String toString() {
            return "{" +
                    "nome='" + nome + '\'' +
                    ", cidade='" + cidade + '\'' +
                    ", endereco='" + endereco + '\'' +
                    '}';
        }
    }

    private static class ResponseEventTransactionCartao {
        private String id, email;

        public ResponseEventTransactionCartao() {
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "{" +
                    "id='" + id + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
}
