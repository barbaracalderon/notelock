# Notelock - API REST com Autenticação JWT

Notelock é uma API RESTful construída com Spring Boot que utiliza Tokens de Web JSON (JWT) para autenticação. Ela fornece endpoints para gerenciar recursos como Cadernos e Notas, sendo seus dados persistidos em banco de dados PostgreSQL. A principal intenção desta API é demonstrar os processos de autenticação e segurança com o uso de JWT.


## Autora

Sou a Barbara Calderon, desenvolvedora de software.
- [Github](https://www.github.com/barbaracalderon)
- [Linkedin](https://www.linkedin.com/in/barbaracalderondev)
- [Twitter](https://www.x.com/bederoni)

## Funcionalidades
- Autenticação usando JWT
- Operações CRUD para Cadernos
- Operações CRUD para Notas

## Tecnologias Utilizadas

- **Java JDK17**
- **Spring Boot**: Estrutura para desenvolvimento de aplicativos Java baseados em Spring.
- **Spring Boot Starter Data JPA**: Suporte ao uso do Spring Data JPA para persistência de dados.
- **Spring Boot Starter Security**: Integração do Spring Security para autenticação e autorização.
- **Spring Boot Starter Web**: Configurações para desenvolvimento de aplicativos web com Spring MVC.
- **Spring Boot DevTools**: Ferramentas de desenvolvimento para recarregamento automático e outras funcionalidades durante o desenvolvimento.
- **Spring Boot Starter Data JDBC**: Suporte ao uso do Spring Data JDBC.
- **PostgreSQL Driver JDBC**: Para conexão com o banco de dados PostgreSQL.
- **Jakarta Validation API**: API de validação para validação de objetos Java.
- **Java JWT**: Biblioteca para criação e verificação de tokens JWT (JSON Web Tokens).
- **Lombok**: Biblioteca que simplifica a criação de classes Java, reduzindo a quantidade de código boilerplate.
- **Hibernate**: framework de mapeamento objeto-relacional (ORM) do Java.


## Começando
Para executar este projeto localmente, siga estes passos:


### a) Clone o repositório

```
git clone git@github.com:barbaracalderon/notelock.git
```

### b) Configue o banco de dados

No momento de start da aplicação, as tabelas são criadas automaticamente no banco. Elas estão vazias. O "papel" no cadastro pode ser "ADMIN" ou "USUÁRIO". Verifique na tabela de endpoints as rotas para cada tipo de papel.

### c) Configure o arquivo application.properties

```properties
spring.application.name=notelock
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:5432/notelock
spring.datasource.username=[seu-usuario-aqui]
spring.datasource.password=[sua-senha-aqui]
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.flyway.baseline-on-migrate=true
spring.flyway.schemas=public
api.security.token.secret=${JWT_SECRET:my-secret-key}
```

### d) Execute a aplicação

Navegue até o diretório onde está o projeto e rode a aplicação com o comando:

```bash
./mvnw spring-boot:run
```

O servidor será iniciado em http://localhost:8080/

## Endpoints da API
| Método HTTP | Endpoint       | Descrição                          | Autorização   |
|-------------|----------------|------------------------------------|---------------|
| POST        | /login         | Logar na aplicação (retorno Token) | Todos         |
| POST        | /cadastro      | Criar um novo cadastro             | Todos         |
| GET         | /cadastro      | Obter todos os cadastros           | Todos         |
| DELETE      | /cadastro/{id} | Deletar um cadastro                | ADMIN         |
| GET         | /cadernos      | Obter todos os Cadernos            | ADMIN         |
| GET         | /cadernos/{id} | Obter Caderno por ID               | ADMIN/USUARIO |
| POST        | /cadernos      | Criar um novo Caderno              | ADMIN         |
| PUT         | /cadernos/{id} | Atualizar um Caderno               | ADMIN/USUARIO |
| DELETE      | /cadernos/{id} | Excluir um Caderno                 | ADMIN         |
| GET         | /notas         | Obter todas as Notas               | ADMIN         |
| GET         | /notas/{id}    | Obter Nota por ID                  | ADMIN/USUARIO |
| POST        | /notas         | Criar uma nova Nota                | ADMIN         |
| PUT         | /notas/{id}    | Atualizar uma Nota                 | ADMIN         |
| DELETE      | /notas/{id}    | Excluir uma Nota                   | ADMIN         |

## Autenticação

Para autenticar, envie uma solicitação POST para `/login` com um payload JSON contendo o nome de usuário e a senha. Se as credenciais estiverem corretas, a API responderá com um token JWT, que deve ser incluído no cabeçalho `Authorization` das solicitações subsequentes.


## Futuramente

Este projeto também pode se beneficiar das seguintes implementações para o futuro:

- Testes automatizados: implementação de testes unitários com JUnit e Mockito.
- Documentação Swagger OpenAPI: adoção da documentação padronizada oferecida pela OpenAPI.
- Dockerização: criação de arquivo _dockerfile_ para definir ambiente de execução e _docker-compose.yml_ para orquestração de contêineres, de modo a facilitar o empacotamento, distribuição e implantação desta aplicação em diferentes ambientes.
- Monitoramento: configuração de ferramentas de monitoramento como Grafana para acompanhar métricas de desempenho da aplicação.


## Considerações finais

Esta foi uma atividade desenvolvida individualmente por mim.

Abs,

Barbara Calderon.