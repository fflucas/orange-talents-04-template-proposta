package br.com.zupacademy.fabio.proposta.auth;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.validation.constraints.NotBlank;

public class RequestLogin {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public RequestLogin() {
    }

    public RequestLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MultiValueMap<String, String> convertToMap(){
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("grant_type", "password");
        valueMap.add("username", username);
        valueMap.add("password", password);
        valueMap.add("client_id", "minha-aplicacao");
        valueMap.add("client_secret", "teste");
        return valueMap;
    }


}
