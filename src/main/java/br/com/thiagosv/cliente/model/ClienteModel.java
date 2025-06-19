package br.com.thiagosv.cliente.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clientes")
public final class ClienteModel {

    @Id
    private String id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private String numeroCelular;
    private StatusCliente status;
    @CreatedDate
    private LocalDateTime dataCadastro;
    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    public void inativar(){
        this.status = StatusCliente.INATIVO;
    }

    public boolean isAtivo(){
        return this.status == StatusCliente.ATIVO;
    }

    @Component
    public static class ClienteModelListener extends AbstractMongoEventListener<ClienteModel> {

        @Override
        public void onBeforeConvert(BeforeConvertEvent<ClienteModel> event) {
            ClienteModel cliente = event.getSource();

            if (cliente.getId() == null && cliente.getStatus() == null)
                cliente.setStatus(StatusCliente.ATIVO);
        }
    }
}