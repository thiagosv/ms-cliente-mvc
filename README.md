# Sistema de Gestão de clientes

<!-- TOC -->
* [Sistema de Gestão de clientes](#sistema-de-gestão-de-clientes)
  * [📘 Descrição do projeto](#-descrição-do-projeto)
  * [🧑‍💻 Tecnologias Utilizadas](#-tecnologias-utilizadas)
  * [📃 Documentações](#-documentações)
    * [Swagger](#swagger)
    * [Diagramas](#diagramas)
      * [Decisão (Negócio)](#decisão-negócio)
      * [Sequêncial](#sequêncial)
  * [🧑‍💼Eventos de Negócio - cliente-eventos-v1](#eventos-de-negócio---cliente-eventos-v1)
  * [📁 Árvore de Diretórios/Pacotes](#-árvore-de-diretóriospacotes)
  * [🌍 Ambiente](#-ambiente)
      * [Necessário](#necessário)
<!-- TOC -->

## 📘 Descrição do projeto
Projeto criado como solução do desafio final do bootcamp de "Arquiteto de Software".
A ideia é ser um projeto de um CRUD simples de clientes, com arquitetura MVC.
No decorrer do README, entenderá as tecnologias utilizadas, documentos criados para definição da estrutura arquitetural do projeto, definição dos pacotes e demais informações que são necessárias para entendimento.

## 🧑‍💻 Tecnologias Utilizadas
* Java 17 - Linguagem principal
* Spring Boot 3.5 - Framework de aplicação
* Apache Kafka - Message Broker para eventos
* MongoDB - Banco de dados NoSQL
* Maven - Gerenciamento de dependências
* Swagger - Documentação da API

## 📃 Documentações
### Swagger
O projeto possui um swagger detalhado, disponível na rota `/swagger-ui/index.html#/`.
Além deste, se necessário, pode ser acessado o  [yaml do swagger](.docs/openapi.yaml) que possui o detalhamento completo das API disponibilizadas.

### Diagramas
#### Decisão (Negócio)
* [Atualização de cliente](.docs/decisao/AtualizacaoCliente.md)
* [Criação de cliente](.docs/decisao/CriacaoCliente.md)
* [Deleção de cliente](.docs/decisao/DelecaoCliente.md)

#### Sequêncial
* [Listar cliente](.docs/sequencia/ListarCliente.md)
* [Atualização de cliente](.docs/sequencia/AtualizacaoCliente.md)
* [Criação de cliente](.docs/sequencia/CriacaoCliente.md)
* [Deleção de cliente](.docs/sequencia/DelecaoCliente.md)

## 🧑‍💼Eventos de Negócio - cliente-eventos-v1
* [CLIENTE_CRIADO](.docs/kafka/cliente-eventos-v1/CriacaoCliente.json) - Disparado na criação de novo cliente
* [CLIENTE_ATUALIZADO](.docs/kafka/cliente-eventos-v1/AtualizacaoCliente.json) - Disparado na atualização de dados do cliente
* [CLIENTE_DELETADO](.docs/kafka/cliente-eventos-v1/DelecaoCliente.json) - Disparado na exclusão do cliente

## 📁 Árvore de Diretórios/Pacotes
```
ms-usuario-mvc
├── 📁 src/main/
│   ├── 📁 java/br/com/thiagosv/cliente/
│   │   ├── 📁 config/ - Configurações da aplicação, utilizado para o OpenAPI.
│   │   ├── 📁 controller/ - Controladores REST e tratamento de exceções
│   │   │   ├── 📁 request/ - Objetos utilizados para requisições de criação/alteração de clientes
│   │   │   └── 📁 response/ - Objetos utilizados para responder as requisições
│   │   ├── 📁 exception/ - Exceções customizadas, como DomainException
│   │   ├── 📁 mapper/ - Conversores entre DTOs e Models  
│   │   ├── 📁 messaging/ - Componentes para publicação de eventos (Kafka)
│   │   ├── 📁 model/ - Documentos utilizados na persistência com o MongoDB
│   │   ├── 📁 repository/ - Acesso a dados, persistência, eventos (Spring Data, Spring Context)
│   │   └── 📁 service/ - Lógica de negócio da aplicação
│   └── 📁 resources/
│       ├── application.yml
│       └── application-local.yml - utilizado para configuração local do projeto
├── 📁 .docs/ - Documentação técnica e arquitetural, como diagrama sequencial, decisão, etc.
│   ├── 📁 sequencial/ - Documentos de decisão de negócio
│   ├── 📁 decisao/ - Documentos de sequência
│   └── openapi.yaml - Documento de detalhamento da API
├── .dockerignore - Utilizado para o docker entender o que deve ser ignorado
├── .gitignore
├── docker-compose.yaml - Arquivo de build/run do ambiente do projeto
├── Dockerfile - Configuração de build do projeto
├── pom.xml - Dependências do projeto
└── README.md
```

## 🌍 Ambiente
O [docker-compose.yaml](docker-compose.yaml) posusi todas as informações e necessidades para rodar o projeto. Nele estão as dependências de tecnologias, tais como MongoDB e KAFKA.
Este arquivo está na raiz do projeto e facilita completamente o start do projeto.
>Para usa-lo, basta acessar a raiz do projeto e rodar: `docker-compose up -d --build`

O que será criado*:
* Aplicação: Porta padrão 8080
* MongoDB: Porta padrão 27017
* Kafka: Porta padrão sem auth 9092
* UI:
  * MongoDB UI: Porta 8082 (aplicação web para acesso ao MONGO)
  * Kafka UI: Porta 8081 (aplicação web para acesso ao KAFKA)

#### Necessário
Para isto, claro, é necessário ter instalado o [docker](https://www.docker.com/) na sua máquina.
