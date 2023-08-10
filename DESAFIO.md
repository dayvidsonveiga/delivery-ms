# Requisitos Técnicos:

- **Código versionado em repositório Git**
- **Java 8**
- **Spring Boot 2**
- **Banco de Dados FireBird**
- **API RESTfull**
- **Maven**
- **OpenPIA 3.0 (Swagger)**
- **Arquivo README informando os passos necessários para execução da sua aplicação**

### Deve ser construído um sistema simplificado para o controle de delivery de um restaurante.
### Deverão ser construídas as APIs de forma a suportar as seguintes operações:

- Segurança
  -  Permitir o cadastro de usuários e login com autenticação via token JWT. Os
     métodos das APIs abaixo só poderão ser executados caso o usuário esteja logado.

- Cliente
    -  Permitir o cadastro, alteração, deleção e consulta de clientes.

- Pedido
    -  Permitir o cadastro, alteração, deleção e consulta de pedidos. Um pedido
       obrigatoriamente precisa ter um cliente e um cliente pode ter vários pedidos.
  
- Entrega
    -  Permitir o cadastro, alteração, deleção e consulta de entregas. Uma entrega
       obrigatoriamente necessita estar vinculada a um pedido. 
  

   O design das APIs e seus atributos é livre. Não existe a necessidade de existir um frontend para a
   aplicação. Basta especificar no arquivo README o endereço das APIs criadas.