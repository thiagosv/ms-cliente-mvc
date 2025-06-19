package br.com.thiagosv.cliente.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public final class ClienteEvento {
    private final TipoEventoCliente evento;
    private final String id;
    private final String email;
    private final String nome;
    private final LocalDateTime timestamp;
    
    public enum TipoEventoCliente {
        CLIENTE_CRIADO,
        CLIENTE_ATUALIZADO,
        CLIENTE_DELETADO
    }
}