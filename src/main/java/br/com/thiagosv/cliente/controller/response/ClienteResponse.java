package br.com.thiagosv.cliente.controller.response;

import br.com.thiagosv.cliente.model.StatusCliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteResponse {

    private String id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private String numeroCelular;
    private StatusCliente status;

}