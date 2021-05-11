package br.com.zupacademy.fabio.proposta.client.card;

public class RequestApi {
    private String documento;
    private String nome;
    private String idProposta;

    public RequestApi(String documento, String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }
    public String getNome() {
        return nome;
    }
    public String getIdProposta() {
            return idProposta;
        }
}
