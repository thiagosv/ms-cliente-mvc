# Sistema de Gestão de clientes

<!-- TOC -->
* [Sistema de Gestão de clientes](#sistema-de-gestão-de-clientes)
  * [Tecnologias Utilizadas](#tecnologias-utilizadas)
  * [Eventos de Negócio](#eventos-de-negócio)
  * [Diagramas](#diagramas)
  * [📁 Árvore de Diretórios](#-árvore-de-diretórios)
  * [📋 Descrição dos Pacotes](#-descrição-dos-pacotes)
    * [🏗️ **Camada Principal (src/main/java/br/com/thiagosv/cliente/)**](#-camada-principal-srcmainjavabrcomthiagosvcliente)
    * [🐳 **Infraestrutura**](#-infraestrutura)
<!-- TOC -->

## Tecnologias Utilizadas
* Java 17 - Linguagem principal
* Spring Boot 3.5 - Framework de aplicação
* Apache Kafka - Message Broker para eventos
* MongoDB - Banco de dados NoSQL
* Maven - Gerenciamento de dependências

## Eventos de Negócio
* CLIENTE_CRIADO - Disparado na criação de novo cliente
* CLIENTE_ATUALIZADO - Disparado na atualização de dados do cliente
* CLIENTE_DELETADO - Disparado na exclusão do cliente

## Diagramas
* [Listar cliente](.docs/c4/code/ListarCliente.md)
* [Atualização de cliente](.docs/c4/code/AtualizacaoCliente.md)
* [Criação de cliente](.docs/c4/code/CriacaoCliente.md)
* [Deleção de cliente](.docs/c4/code/DelecaoCliente.md)

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
│   └── c4/code/
├── .gitignore
├── pom.xml
└── README.md
```

## 📋 Descrição dos Pacotes

### 🏗️ **Camada Principal (src/main/java/br/com/thiagosv/cliente/)**

| Pacote        | Descrição                                                           |
|---------------|---------------------------------------------------------------------|
| `config/`     | Configurações do Spring (OpenApi)                                   |
| `controller/` | Controladores REST e tratamento de exceções                         |
| `exception/`  | Exceções customizadas da aplicação, como DomainException            |
| `mapper/`     | Conversores entre DTOs e Models                                     |
| `messaging/`  | Componentes para publicação de eventos (Kafka)                      |
| `model/`      | Documentos e enums utilizados na persistência com o MongoDB         |
| `repository/` | Acesso a dados, persistência, eventos (Spring Data, Spring Context) |
| `service/`    | Lógica de negócio da aplicação                                      |

### 🐳 **Infraestrutura**

| Diretório  | Descrição                                                     |
|------------|---------------------------------------------------------------|
| `.docs/`   | Documentação técnica e arquitetural, como diagrama sequencial |
