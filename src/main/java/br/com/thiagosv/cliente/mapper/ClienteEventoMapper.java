package br.com.thiagosv.cliente.mapper;

import br.com.thiagosv.cliente.model.ClienteEvento;
import br.com.thiagosv.cliente.model.ClienteModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteEventoMapper {

    ClienteEventoMapper INSTANCE = Mappers.getMapper(ClienteEventoMapper.class);

    @Mapping(target = "timestamp", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "evento", source = "evento")
    ClienteEvento toEvent(ClienteModel cliente, ClienteEvento.TipoEventoCliente evento);

}