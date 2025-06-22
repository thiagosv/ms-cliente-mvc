package br.com.thiagosv.cliente.repository;

import br.com.thiagosv.cliente.mapper.ClienteEventoMapper;
import br.com.thiagosv.cliente.model.ClienteModel;
import br.com.thiagosv.cliente.model.enums.TipoEventoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class EventoRepository {

    private final ApplicationEventPublisher eventPublisher;
    private final ClienteEventoMapper mapper = ClienteEventoMapper.INSTANCE;

    public void publicar(ClienteModel clienteModel, TipoEventoCliente evento) {
        eventPublisher.publishEvent(mapper.toEvent(clienteModel, evento));
    }
}