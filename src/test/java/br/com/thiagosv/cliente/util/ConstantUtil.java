package br.com.thiagosv.cliente.util;

import br.com.thiagosv.cliente.model.enums.StatusCliente;

import java.time.LocalDate;

public class ConstantUtil {

    public static final String EMAIL_VALIDO = "user@email.com";
    public static final String OUTRO_EMAIL_VALIDO = "outro@email.com";

    public static final String NUMERO_CELULAR = "51999999999";
    public static final String NUMERO_CELULAR_ATUALIZADO = "51999999990";

    public static final String ID_CLIENTE = "123";
    public static final String ID_CLIENTE_INEXISTENTE = "999";

    public static final int CLIENTE_NOME_TAMANHO_MINIMO = 2;
    public static final int CLIENTE_NOME_TAMANHO_MAXIMO = 100;
    public static final String NOME_CLIENTE = "Cliente Teste";
    public static final String OUTRO_NOME_CLIENTE = "Cliente Outro";
    public static final String NOME_CLIENTE_ATUALIZADO = "Cliente Atualizado";
    public static final String NOME_CLIENTE_MINIMO = "AB";
    public static final String NOME_CLIENTE_MAXIMO = "A".repeat(CLIENTE_NOME_TAMANHO_MAXIMO);

    public static final StatusCliente CLIENTE_STATUS = StatusCliente.ATIVO;


    public static final LocalDate DATA_NASCIMENTO = LocalDate.of(1997, 9, 1);

    public static final String ERRO_CAMPO_NOME = "nome";
    public static final String ERRO_MENSAGEM_NOME = "Nome é obrigatório";
    public static final String ERRO_CAMPO_EMAIL = "email";
    public static final String ERRO_MENSAGEM_EMAIL = "Email inválido";
    public static final String ERRO_DOMAIN_MESSAGE = "Erro de domínio teste";
    public static final String ERRO_INTERNO_MESSAGE = "Ocorreu um erro interno no servidor";
    public static final String ERRO_NOT_FOUND = "Cliente nao encontrado";

    public static final int PAGINA = 0;
    public static final int TAMANHO = 10;
    public static final Long TOTAL_CLIENTES = 10L;



}
