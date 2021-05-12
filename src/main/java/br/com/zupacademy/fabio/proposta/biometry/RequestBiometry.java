package br.com.zupacademy.fabio.proposta.biometry;

import br.com.zupacademy.fabio.proposta.card.Card;
import br.com.zupacademy.fabio.proposta.shared.config.error.ApiErrorException;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotBlank;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RequestBiometry {
    @NotBlank
    private String fingerPrint;

    public RequestBiometry() {
    }

    public RequestBiometry(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public Biometry convertToBiometry(Card card) {
        byte[] decode = Base64.getDecoder().decode(fingerPrint.getBytes(StandardCharsets.UTF_8));
        String decodedFingerPrint = new String(decode);
        return new Biometry(decodedFingerPrint, card);
    }
}
