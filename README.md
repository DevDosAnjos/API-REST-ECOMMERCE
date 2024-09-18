<h1 align="center"> 🏪E-COMMERCE🏪 </h1>


## 🛒Projeto

Este projeto implementa uma API REST para um sistema de e-commerce, com funcionalidades que incluem gerenciamento de usuários, categorias de produtos, produtos, pedidos e processos de compra.

<details>
<summary><b>Authentication</b></summary>
<img src="files/Images/Authentication.png" alt="Authentication">
</details>

<details>
<summary><b>User</b></summary>
<img src="files/Images/User.png" alt="User">
</details>

<details>
<summary><b>Category</b></summary>
<img src="files/Images/Category.png" alt="Category">
</details>

<details>
<summary><b>Product</b></summary>
<img src="files/Images/Product.png" alt="Product">
</details>

<details>
<summary><b>Order</b></summary>
<img src="files/Images/Order.png" alt="Order">
</details>

<details>
<summary><b>Purchase</b></summary>
<img src="files/Images/Purchase.png" alt="Purchase">
</details>


## 🔧Tecnologias

- **Linguagem**: Java
- **Gerenciamento de Dependências**: Maven
- **Framework**: Spring Boot (Web, Jpa, Security, Validation, DevTools e Testcontainers)
- **Segurança**: Token JWT (auth0)
- **Banco de Dados**: MySQL
- **Migrações de Banco de Dados**: Flyway
- **Testes**: JUnit 5, Mockito e Testcontainers
- **Documentação**: Swagger
- **Docker**: Imagem e Containers

## 💻 Utilização

Pré-requisito: Java 17 e Docker.

1. Clone o projeto 
2. Altere os dados do arquivo .env-example para o de sua melhor escolha 
3. No terminal, utilize o seguinte comando: docker compose up --build

A API pode ser utilizada pelas formas abaixo:
- **Acessando a Documentação da API localmente**: http://localhost:8080/swagger-ui/index.html#/
- **Importando o Arquivo JSON da Collection (Postman ou Insomnia)**: https://github.com/DevDosAnjos/API-REST-ECOMMERCE/blob/main/files/Collection/Ecommerce_collection.json

OBS: Para fazer os testes de integração da aplicação, é necessario ter o Docker rodando localmente.

## 👨‍💻Autor
Nathan dos Anjos Gonçalves

https://www.linkedin.com/in/nathan-dos-anjos-gon%C3%A7alves-58849424a/
