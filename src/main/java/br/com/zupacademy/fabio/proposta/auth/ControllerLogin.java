package br.com.zupacademy.fabio.proposta.auth;

import br.com.zupacademy.fabio.proposta.shared.config.error.ApiErrorException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
public class ControllerLogin {

    private LoginKeycloak loginKeycloak;
    @Autowired
    public ControllerLogin(LoginKeycloak loginKeycloak) {
        this.loginKeycloak = loginKeycloak;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseLogin> login (@RequestBody @Valid RequestLogin requestLogin){
        MultiValueMap<String, String> multiValueMap = requestLogin.convertToMap();
        ResponseLogin tokenResponse;
        try{
            tokenResponse = loginKeycloak.requestToken(multiValueMap);
        }catch (FeignException fe){
            throw new ApiErrorException(HttpStatus.UNAUTHORIZED, "Bad credentials");
        }
        return ResponseEntity.ok(tokenResponse);
    }
}
