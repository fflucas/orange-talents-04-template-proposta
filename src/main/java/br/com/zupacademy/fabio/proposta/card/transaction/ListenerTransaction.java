package br.com.zupacademy.fabio.proposta.card.transaction;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ListenerTransaction {

    @KafkaListener(topics = "${spring.kafka.topic.transactions}")
    public void listener(EventTransaction eventoDeTransacao) {
        System.out.println(eventoDeTransacao.toString());
    }

}
