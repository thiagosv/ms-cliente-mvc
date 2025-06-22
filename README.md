# Sistema de Gestão de clientes

<!-- TOC -->
* [Sistema de Gestão de clientes](#sistema-de-gestão-de-clientes)
  * [📘 Descrição do projeto](#-descrição-do-projeto)
  * [🧑‍💻 Tecnologias Utilizadas](#-tecnologias-utilizadas)
  * [📃 Documentações](#-documentações)
    * [Diagramas](#diagramas)
      * [Decisão (Negócio)](#decisão-negócio)
      * [Sequêncial](#sequêncial)
    * [Swagger](#swagger)
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

A escolha do java se dá principalmente por experiência na linguagem, além da robustez e garantia empresarial de segurança e suporte já conhecidas.
O framework Spring foi escolhido como base por se tratar de um dos mais utilizados no mercado, além da facilidade e curva de aprendizado baixas. O ecossistema spring garante segurança e facilidade na integração de outras tecnologias, como o MongoDB e Kafka, escolhidos para complementar o projeto de gerenciamento de clientes.


## 📃 Documentações
Foram necessários alguns documentos principais para garantir as funcionalidades propostas pelo desafio. As que foram mais utilizadas na construção são os diagramas de Decisão e sequência.
Diagramas de decisão foram utilizados para a escrita das regras de negócio que foram desenhadas para a solução.
Diagramas de sequência foram utilizados para o desenho dos casos de uso. Determinando a correta sequência de eventos entre objetos internos e externos do projeto, tais como regras de negócio, validadores, banco de dados e eventos. Apontando os momentos em que cada um deveria ser chamado.

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

### Swagger
Foi criado um arquivo com todos os detalhes das apis que devem ser implementadas no serviço, podendo ser acessado através do [yaml do swagger](.docs/openapi.yaml).
Além disso, foi utilizado o openapi para integração do endpoint de documentação do swagger junto ao projeto, podendo ser acessado através do endpoint `/swagger-ui/index.html#/`.

## 🧑‍💼Eventos de Negócio - cliente-eventos-v1
Como adição ao desafio, foi adicionada a integração de eventos de negócio, para uma comunicação assíncrona utilizando eventos. Foram determinados, inicialmente, os eventos para criação, deleção e edição de clientes.
Os eventos devem ser publicados assim que as operações são COMMITADAS no banco de dados e poderão ser consumidas por quem for necessário, quando for necessário.

* [CLIENTE_CRIADO](.docs/kafka/cliente-eventos-v1/CriacaoCliente.json) - Disparado na criação de novo cliente
* [CLIENTE_ATUALIZADO](.docs/kafka/cliente-eventos-v1/AtualizacaoCliente.json) - Disparado na atualização de dados do cliente
* [CLIENTE_DELETADO](.docs/kafka/cliente-eventos-v1/DelecaoCliente.json) - Disparado na exclusão do cliente

## 📁 Árvore de Diretórios/Pacotes
A árvore de diretórios determina a organização de um projeto, facilitando o desenvolvimento e futura manutenção do serviço. Abaixo está a estrutura proposta para o projeto em questão.
```
ms-cliente-mvc
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
Como final, foi realizada a criação do [docker-compose.yaml](docker-compose.yaml) para subida do ambiente completo do projeto, incluíndo suas dependências tecnológicas, tais como MONGODB e KAFKA.
O docker-compose.yaml possui todas as informações e necessidades para rodar o projeto. Este arquivo está na raiz do projeto e facilita completamente o start do projeto.

>Para usar, basta acessar a raiz do projeto e rodar: `docker-compose up -d --build`

O que será criado*:
* Aplicação: Porta padrão 8080
* MongoDB: Porta padrão 27017
* Kafka: Porta padrão sem auth 9092
* UI:
  * MongoDB UI: Porta 8082 (aplicação web para acesso ao MONGO)
  * Kafka UI: Porta 8081 (aplicação web para acesso ao KAFKA)

#### Necessário
Para isto, claro, é necessário ter instalado o [docker](https://www.docker.com/) na sua máquina.
