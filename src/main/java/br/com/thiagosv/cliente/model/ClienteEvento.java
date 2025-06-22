package br.com.thiagosv.cliente.model;

import br.com.thiagosv.cliente.model.enums.TipoEventoCliente;

import java.time.LocalDateTime;

public record ClienteEvento(
    TipoEventoCliente evento,
    String id,
    String email,
    String nome,
    LocalDateTime timestamp
){}