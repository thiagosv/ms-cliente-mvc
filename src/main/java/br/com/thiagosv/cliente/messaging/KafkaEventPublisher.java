package br.com.thiagosv.cliente.messaging;

import br.com.thiagosv.cliente.model.ClienteEvento;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventPublisher {

    private final KafkaTemplate<String, ClienteEvento> kafkaTemplate;

    @Value("${app.kafka.topic.cliente-eventos:cliente-eventos-v1}")
    private String topico;

    @EventListener
    public void publicar(ClienteEvento evento) {
        try {
            kafkaTemplate.send(topico, evento.id(), evento);
        } catch (Exception e) {
            log.error("Erro ao publicar evento {} para o tópico {}: {}", evento.evento(), topico, e.getMessage(), e);
        }
    }
}