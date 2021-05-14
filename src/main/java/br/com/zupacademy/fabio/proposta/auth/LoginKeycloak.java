package br.com.zupacademy.fabio.proposta.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "login", url = "${login.keycloak.request.token.uri}")
public interface LoginKeycloak {

    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    ResponseLogin requestToken(MultiValueMap<String, String> map);

}
