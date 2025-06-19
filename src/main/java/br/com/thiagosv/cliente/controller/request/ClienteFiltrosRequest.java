package br.com.thiagosv.cliente.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ClienteFiltrosRequest{

    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private String numeroCelular;
}
