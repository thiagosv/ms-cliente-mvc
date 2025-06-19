package br.com.thiagosv.cliente.mapper;

import br.com.thiagosv.cliente.controller.request.CriarClienteRequest;
import br.com.thiagosv.cliente.controller.response.ClienteResponse;
import br.com.thiagosv.cliente.model.ClienteModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    ClienteModel model(CriarClienteRequest request);
    ClienteResponse response(ClienteModel clienteModel);
}