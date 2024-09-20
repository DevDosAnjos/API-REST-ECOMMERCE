<h1 align="center"> 🏪E-COMMERCE🏪 </h1>


## 🛒Projeto

Este projeto implementa uma API REST para um sistema de e-commerce, com funcionalidades que incluem gerenciamento de usuários, categorias de produtos, produtos, pedidos e processos de compra.

O Fluxo de Operações seria:

1. Authenticação dos Usuários e Admins.
2. Users podendo realizar algumas funcionalidades, com o intuito final de realizar uma compra.
3. Admins tendo controle total da API.


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
- **Docker**

## 💻 Utilização

! Pré-requisitos: Java 17 e Docker !

Para utilizar a aplicação:

1. Inicie o seu docker
2. Clone o Projeto 
3. Abra o Projeto na sua IDE
4. No terminal dentro da IDE, utilize o seguinte comando: docker compose up 



A API pode ser utilizada pelas formas abaixo:
- **Acessando a Documentação da API localmente**: http://localhost:8080/swagger-ui/index.html#/
- **Importando o Arquivo JSON da Collection (Postman ou Insomnia)**: https://github.com/DevDosAnjos/API-REST-ECOMMERCE/blob/main/files/Collection/Ecommerce_collection.json

Inserts Padrões:

<details>
<summary><b>User</b></summary>
<img src="files/Images/InsertsUser.png" alt="User">
</details>

<details>
<summary><b>Category</b></summary>
<img src="files/Images/InsertsCategory.png" alt="Category">
</details>

<details>
<summary><b>Product</b></summary>
<img src="files/Images/InsertsProduct1.png" alt="Product">
<img src="files/Images/InsertsProduct2.png" alt="Product">
</details>

## 👨‍💻Autor
Nathan dos Anjos Gonçalves

https://www.linkedin.com/in/nathan-dos-anjos-gon%C3%A7alves-58849424a/
