# 💳 Desafio Técnico - Sistema de Propostas de Crédito (Backend)

Este repositório contém a implementação de uma API RESTful para um sistema de propostas de crédito, desenvolvida como parte de um desafio técnico.

---

## ✨ Funcionalidades Implementadas

-   [x] **Criação de Propostas:** Endpoint para submeter novas propostas de crédito, com validação de dados (CPF, valor, parcelas) e geração automática de parcelas.
-   [x] **Busca de Proposta por ID:** Endpoint para consultar os detalhes de uma proposta específica, incluindo a lista de suas parcelas.
-   [x] **Listagem Paginada de Propostas:** Endpoint para listar todas as propostas de forma paginada.
-   [x] **Pagamento de Parcela:** Endpoint para marcar uma parcela específica como "Paga".

---

## 🛠️ Tecnologias Utilizadas

-   **Java 17** e **Spring Boot 3**
-   **Maven** para gerenciamento de dependências
-   **Spring Data JPA / Hibernate** para persistência de dados
-   **PostgreSQL** como banco de dados
-   **Docker** e **Docker Compose** para containerização e ambiente de desenvolvimento
-   **Springdoc OpenAPI (Swagger)** para documentação da API
-   **Git** e **GitHub** para versionamento de código
-   **Render** para deploy contínuo e hospedagem da aplicação e do banco de dados

---

## 🚀 Acesso à Aplicação

A aplicação está implantada na nuvem e pode ser acessada através dos seguintes links:

* **URL Base da API:** `https://proposta-credito-app.onrender.com` 
* **Documentação Interativa (Swagger UI):** `https://proposta-credito-app.onrender.com/swagger-ui.html` 
Obs.: Lembre-se que a aplicação provavelmente estará "hibernando" ao tentar realizar a primeira requisição, ou seja, a primeira requisição deve demorar até 3 minutos para retornar.

---

## 🐳 Como Rodar o Projeto Localmente

**Pré-requisitos:**
* [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado e em execução.
* [Git](https://git-scm.com/) instalado.

**Passos:**
1.  Clone o repositório:
    ```bash
    git clone [https://github.com/AugustoEngel010/proposta-credito.git](https://github.com/AugustoEngel010/proposta-credito.git)
    cd proposta-credito
    ```

2.  Suba os contêineres da aplicação e do banco de dados:
    ```bash
    docker-compose up --build
    ```
A aplicação estará acessível em `http://localhost:8080`.

---

## 📄 Endpoints da API

Abaixo estão alguns exemplos de uso dos endpoints. Para uma documentação completa e interativa, acesse o **Swagger UI**.

### Criar Proposta
-   **`POST /propostas`**

**Request Body:**
```json
{
  "cpf": "81166066037",
  "valorSolicitado": 1500.00,
  "quantidadeParcelas": 12,
  "dataSolicitacao": "2025-09-28"
}
```
**Success Response (201 Created):**
```json
{
  "idProposta": 1
}
```
### Buscar Proposta por ID
-   **`GET /propostas/{id}`**

**Exemplo de URL:** `.../propostas/1`

**Success Response (200 OK):**
```json
{
    "id": 1,
    "cpf": "81166066037",
    "valorSolicitado": 1500.00,
    // ...demais dados
    "parcelas": [ /* ... */ ]
}
```
### Listar Propostas
-   **`GET /propostas`**

**Exemplo de URL com paginação:** `.../propostas?page=0&size=5&sort=valorSolicitado,desc`

### Pagar Parcela
-   **`POST /propostas/{idProposta}/parcelas/{numeroParcela}/pagar`**

**Exemplo de URL:** `.../propostas/1/parcelas/3/pagar`

**Success Response (200 OK):**
```json
{
    "numeroParcela": 3,
    "valor": 125.00,
    "dataVencimento": "2025-12-28",
    "status": "Paga"
}
```
