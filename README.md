# CRUD Cliente - Sistema de Gestão

## Visão Geral
>Este documento apresenta o modelo C4 nível 4 (código) para o sistema CRUD de Cliente, implementado em Java com Spring Boot, MongoDB e Apache Kafka para eventos de negócio.

## Contexto da Implementação

### Tecnologias Utilizadas
* Java 17 - Linguagem principal
* Spring Boot 3.5 - Framework de aplicação
* Apache Kafka - Message Broker para eventos
* MongoDB - Banco de dados NoSQL
* Maven - Gerenciamento de dependências

### Eventos de Negócio
* CLIENTE_CRIADO - Disparado na criação de novo cliente
* CLIENTE_ATUALIZADO - Disparado na atualização de dados do cliente
* CLIENTE_DELETADO - Disparado na exclusão do cliente