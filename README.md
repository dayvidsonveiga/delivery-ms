
# Delivery-MS
Desafio desenvolvido para integrar o time de desenvolvimento da [Inovation IT](https://www.inovation.com.br/). Descrição do desafio e requisitos no arquivo **[DESAFIO.md](https://github.com/dayvidsonveiga/delivery-ms/blob/main/DESAFIO.md)**

# Tecnologias utilizadas

- **[Spring Boot](https://spring.io/projects/spring-boot)**
- **[Spring Security](https://spring.io/projects/spring-security)**
- **[Spring Data JPA](https://spring.io/projects/spring-data-jpa#overview)**
- **[Hibernate](https://hibernate.org/orm/)**
- **[Lombok](https://projectlombok.org/)**
- **[Docker](https://www.docker.com/)**
- **[FireBird](https://firebirdsql.org/)**

# Requisitos para executar o projeto
- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/)
- [JDK 8+](https://www.oracle.com/br/java/technologies/javase/jdk8-archive-downloads.html)

# Como executar o projeto
- Clone o projeto.
```bash
  git clone https://github.com/dayvidsonveiga/delivery-ms.git
```
- Abra um terminal na raiz do projeto e execute o comando abaixo para iniciar o banco de dados FireBird no docker.
```bash
  cd docker && docker-compose up -d
```
- Abra a IDE de sua preferência e importe o projeto clonado e aguarde o download de todas dependências do projeto

- Execute o arquivo com a classe main DeliveryMsApplication.java

- Acesse a interface dos recursos do backend através do swagger usando o endereço local http://localhost:8080
- Por padrão apenas as rotas do usuario estão permitidas sem autenticação, para acesso as demais rotas:
  - Crie um usuário através do endpoint http://localhost:8080/v1/usuario/criar
  
  ```bash
    curl -X 'POST' \
  'http://localhost:8080/v1/usuario/criar' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "email": "nome@gmail.com",
  "pass": "12345"}'
  ```
  - Gere o token de autenticação realizando o login com os dados criado através do endpoint http://localhost:8080/v1/usuario/login
  ```bash
    curl -X 'POST' \
  'http://localhost:8080/v1/usuario/login' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "email": "nome@gmail.com",
  "pass": "12345"}'
  ```
    - Após realizar a requisição ira retorna um token de acesso, copie todo o token: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJk....
    - E insira no botão de Authorize que fica na parte superior do Swagger
    - Agora você possui acesso a todas as rotas
  

# Rotas

## Usuario Controller
| Método | Path               | Descrição                            |
|--------|--------------------|--------------------------------------|
| GET    | /v1/usuario/listar | Listar todos os usuários cadastrados |
| POST   | /v1/usuario/login  | Logar no sistema                     |
| POST   | /v1/usuario/criar  | Criar novo usuário                   |


## Cliente Controller
| Método | Path                       | Descrição                            |
|--------|----------------------------|--------------------------------------|
| DELETE | /v1/cliente/deletar/{id}   | Deletar cliente pelo id              |
| GET    | /v1/cliente/listar         | Listar todos os clientes cadastrados |
| PATCH  | /v1/cliente/atualizar/{id} | Atualizar cliente pelo id            |
| POST   | /v1/cliente/encontrar-cpf  | Encontrar cliente pelo cpf           |
| POST   | /v1/cliente/criar          | Criar novo cliente                   |


## Pedido Controller
| Método | Path                         | Descrição                          |
|--------|------------------------------|------------------------------------|
| DELETE | /v1/pedido/deletar/{id}      | Deletar pedido pelo id             |
| GET    | /v1/pedido/listar            | Listar todos os pedido cadastrados |
| GET    | /v1/pedido/encontrar/{id}    | Encontrar pedido pelo id           |
| PATCH  | /v1/pedido/atualizar/{id}    | Atualizar pedido pelo id           |
| POST   | /v1/pedido/criar/{idCliente} | Criar novo pedido                  |


## Entrega Controller
| Método | Path                         | Descrição                            |
|--------|------------------------------|--------------------------------------|
| DELETE | /v1/Entrega/deletar/{id}     | Deletar Entrega pelo id              |
| GET    | /v1/Entrega/listar           | Listar todas as Entregas cadastradas |
| GET    | /v1/Entrega/encontrar/{id}   | Encontrar Entrega pelo id            |
| PATCH  | /v1/Entrega/atualizar/{id}   | Atualizar Entrega pelo id            |
| POST   | /v1/Entrega/criar/{idPedido} | Criar novo Entrega                   |


## Planos para as próximas versões do serviço

- Implementação do [Spring WebFlux](https://docs.spring.io/spring-framework/reference/web/webflux.html) a fim de tornar a api reativa, dessa forma obtendo mais performance para cenários de grandes cargas
- Testes de integração
