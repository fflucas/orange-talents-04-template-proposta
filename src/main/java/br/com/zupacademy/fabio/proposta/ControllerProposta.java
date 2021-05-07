package br.com.zupacademy.fabio.proposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class ControllerProposta {

    private RepositoryProposta repositoryProposta;
    @Autowired
    public ControllerProposta(RepositoryProposta repositoryProposta) {
        this.repositoryProposta = repositoryProposta;
    }

    @PostMapping("/v1/proposta")
    public ResponseEntity<?> create(@RequestBody @Valid RequestProposta requestProposta, UriComponentsBuilder uriBuilder){
        Proposta proposta = requestProposta.convertToProposta();
        repositoryProposta.save(proposta);
        URI uri = uriBuilder.path("/v1/proposta/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
