package br.com.thiagosv.cliente.repository;

import br.com.thiagosv.cliente.model.ClienteModel;
import br.com.thiagosv.cliente.model.enums.StatusCliente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClienteRepository extends MongoRepository<ClienteModel, String> {

    boolean existsByEmailAndStatus(String email, StatusCliente status);
}