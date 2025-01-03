# Documentação
O projeto foi criado para poder satisfazer a necessidade de uma serviço que recebe produtos de um serviço A, calcula a soma de produtos e expoem a um serviço B.

# Introdução

### Referência de Documentação
Para mais informações, considere as seguintes seções:

* [Documentação oficial do Apache Maven](https://maven.apache.org/guides/index.html)
* [Guia de Referência do Plugin Spring Boot Maven](https://docs.spring.io/spring-boot/3.4.1/maven-plugin)
* [Criar uma imagem OCI](https://docs.spring.io/spring-boot/3.4.1/maven-plugin/build-image.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.4.1/reference/using/devtools.html)
* [Suporte ao Docker Compose](https://docs.spring.io/spring-boot/3.4.1/reference/features/dev-services.html#features.dev-services.docker-compose)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.1/reference/web/servlet.html)

### Guias
Os seguintes guias ilustram como usar alguns recursos concretamente:

* [Construindo um Serviço Web RESTful](https://spring.io/guides/gs/rest-service/)
* [Servindo Conteúdo Web com Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Construindo serviços REST com Spring](https://spring.io/guides/tutorials/rest/)
* [Acessando dados com MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

### Suporte ao Docker Compose
Este projeto contém um arquivo Docker Compose chamado `compose.yaml`.
Neste arquivo, os seguintes serviços foram definidos:

* mysql: [`mysql:latest`](https://hub.docker.com/_/mysql)
* rabbitMQ: [`rabbitmq:latest`](https://hub.docker.com/_/rabbitmq)

Por favor, revise as tags das imagens usadas e defina-as para as mesmas que você está executando em produção.

### Substituições do Maven Parent

Devido ao design do Maven, elementos são herdados do POM pai para o POM do projeto.
Embora a maioria da herança seja adequada, ela também herda elementos indesejados como `<license>` e `<developers>` do pai.
Para evitar isso, o POM do projeto contém substituições vazias para esses elementos.
Se você mudar manualmente para um pai diferente e realmente quiser a herança, você precisará remover essas substituições.

# Descrição do Projeto

Este projeto é um gerenciador de produtos que utiliza Spring Boot para criar uma aplicação web RESTful. Ele inclui funcionalidades para gerenciar produtos, compradores e vendas. A aplicação também utiliza RabbitMQ para processamento de mensagens e MySQL como banco de dados.

# Estrutura do Projeto

- **Modelos**: Contém as classes de entidade que representam os dados do aplicativo.
- **Repositórios**: Contém interfaces que estendem JpaRepository para realizar operações CRUD.
- **Serviços**: Contém a lógica de negócios da aplicação.
- **Controladores**: Contém os endpoints REST que expõem a funcionalidade da aplicação.

# Configuração do Ambiente

1. **Banco de Dados**: Certifique-se de que o MySQL está instalado e em execução. Configure as credenciais do banco de dados no arquivo `application.properties`.
2. **RabbitMQ**: Certifique-se de que o RabbitMQ está instalado e em execução.
3. **Docker Compose**: Utilize o arquivo `compose.yaml` para configurar e iniciar os serviços necessários.

# Executando a Aplicação

1. Clone o repositório do projeto.
2. Navegue até o diretório do projeto.
3. Execute `mvn clean install` para construir o projeto.
4. Execute `mvn spring-boot:run` para iniciar a aplicação.

# Endpoints Principais

- **Produtos**:
  - `GET /products`: Lista todos os produtos.
  - `POST /products`: Adiciona um novo produto.
  - `PUT /products/{id}`: Atualiza um produto existente.
  - `DELETE /products/{id}`: Remove um produto.

- **Compradores**:
  - `GET /buyers`: Lista todos os compradores.
  - `POST /buyers`: Adiciona um novo comprador.
  - `PUT /buyers/{id}`: Atualiza um comprador existente.
  - `DELETE /buyers/{id}`: Remove um comprador.

- **Vendas**:
  - `GET /sales`: Lista todas as vendas.
  - `POST /sales`: Adiciona uma nova venda.
  - `PUT /sales/{id}`: Atualiza uma venda existente.
  - `PUT /sales/{saleId}/add-product`: Adiciona um produto a uma venda existente.
  - `DELETE /sales/{saleId}/remove-product`: Remove um produto a uma venda existente.
  - `PUT /sales/{id}`: Atualiza uma venda existente.
  - `DELETE /sales/{id}`: Remove uma venda.

# Postman

importe os arquivos order-env.postman_environment.json e Order,postman_collection no seu Postman