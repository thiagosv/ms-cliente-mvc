package br.com.thiagosv.cliente.repository;

import br.com.thiagosv.cliente.model.ClienteModel;
import br.com.thiagosv.cliente.model.enums.StatusCliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ClienteRepository extends MongoRepository<ClienteModel, String> {

    @Query("{ 'status' : 'ATIVO' }")
    List<ClienteModel> findByStatusAtivo();

    boolean existsByEmailAndStatus(String email, StatusCliente status);
}