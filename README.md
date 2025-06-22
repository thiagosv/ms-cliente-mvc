# Sistema de Gestão de clientes

<!-- TOC -->
* [Sistema de Gestão de clientes](#sistema-de-gestão-de-clientes)
  * [Descrição do projeto](#descrição-do-projeto)
  * [Tecnologias Utilizadas](#tecnologias-utilizadas)
  * [Diagramas](#diagramas)
  * [Eventos de Negócio - cliente-eventos-v1](#eventos-de-negócio---cliente-eventos-v1)
    * [Exemplo de JSON](#exemplo-de-json)
  * [📁 Árvore de Diretórios](#-árvore-de-diretórios)
  * [📋 Descrição dos Pacotes](#-descrição-dos-pacotes)
    * [🏗️ **Camada Principal (src/main/java/br/com/thiagosv/cliente/)**](#-camada-principal-srcmainjavabrcomthiagosvcliente)
    * [🐳 **Infraestrutura**](#-infraestrutura)
<!-- TOC -->

## Descrição do projeto
Projeto criado como solução do desafio final do bootcamp de "Arquiteto de Software".
A ideia é ser um projeto de um CRUD simples de clientes, com arquitetura MVC.
No decorrer do README, entenderá as tecnologias utilizadas, documentos criados para definição da estrutura arquitetural do projeto, definição dos pacotes e demais informações que são necessárias para entendimento.

## Tecnologias Utilizadas
* Java 17 - Linguagem principal
* Spring Boot 3.5 - Framework de aplicação
* Apache Kafka - Message Broker para eventos
* MongoDB - Banco de dados NoSQL
* Maven - Gerenciamento de dependências

## Diagramas
* [Listar cliente](.docs/c4/code/ListarCliente.md)
* [Atualização de cliente](.docs/c4/code/AtualizacaoCliente.md)
* [Criação de cliente](.docs/c4/code/CriacaoCliente.md)
* [Deleção de cliente](.docs/c4/code/DelecaoCliente.md)

## Eventos de Negócio - cliente-eventos-v1
* CLIENTE_CRIADO - Disparado na criação de novo cliente
* CLIENTE_ATUALIZADO - Disparado na atualização de dados do cliente
* CLIENTE_DELETADO - Disparado na exclusão do cliente

### Exemplo de JSON
```
{
    "evento": "CLIENTE_CRIADO",
    "id": "68546b7431de3b531fdfc7e5"
    "email": "thiagosilvavieira97@gmail.com"
    "nome": "Thiago Vieira"
    "timestamp": "2025-06-20T02:18:00"
}
```

## 📁 Árvore de Diretórios
```
ms-usuario-mvc
├── 📁 src/main/
│   ├── 📁 java/br/com/thiagosv/cliente/
│   │   ├── 📁 config/
│   │   ├── 📁 controller/
│   │   │   ├── 📁 request/
│   │   │   └── 📁 response/
│   │   ├── 📁 exception/
│   │   ├── 📁 mapper/
│   │   ├── 📁 messaging/
│   │   ├── 📁 model/
│   │   ├── 📁 repository/
│   │   └── 📁 service/
│   └── 📁 resources/
│       └── application.yml
├── 📁 .docs/
│   └── 📁 c4/code/
├── .gitignore
├── pom.xml
└── README.md
```

## 📋 Descrição dos Pacotes

### 🏗️ **Camada Principal (src/main/java/br/com/thiagosv/cliente/)**

| Pacote        | Descrição                                                           |
|---------------|---------------------------------------------------------------------|
| `config/`     | Configurações da aplicação, utilizado para o OpenAPI.               |
| `controller/` | Controladores REST e tratamento de exceções                         |
| `exception/`  | Exceções customizadas, como DomainException                         |
| `mapper/`     | Conversores entre DTOs e Models                                     |
| `messaging/`  | Componentes para publicação de eventos (Kafka)                      |
| `model/`      | Documentos utilizados na persistência com o MongoDB                 |
| `repository/` | Acesso a dados, persistência, eventos (Spring Data, Spring Context) |
| `service/`    | Lógica de negócio da aplicação                                      |

### 🐳 **Infraestrutura**

| Diretório  | Descrição                                                     |
|------------|---------------------------------------------------------------|
| `.docs/`   | Documentação técnica e arquitetural, como diagrama sequencial |
