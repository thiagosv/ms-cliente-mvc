package br.com.thiagosv.cliente.controller.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClientePageableResponse {

    private List<ClienteResponse> content;
    private Integer totalElements;
    private Integer totalPages;

}