# Autoflex Inventory API

Backend da aplicação **Autoflex Inventory**, responsável pelo gerenciamento de:

- Produtos
- Matérias-primas
- Associação entre produtos e matérias-primas
- Cálculo de capacidade de produção
- Autenticação e autorização via JWT

---

## Documentação

Acesse o repositório do Frontend para uma documentação mais detalhada -> **[documentação frontend](https://github.com/Winiicius/autoflex-test-frontend/tree/main)** 

## 🏗️ Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security (JWT)
- Flyway (Database Migrations)
- PostgreSQL
- Docker

---

## 📦 Responsabilidades da API

A API é responsável por:

- Fornecer endpoints CRUD para **Products** e **Raw Materials**
- Gerenciar associação N:N entre produtos e matérias-primas
- Calcular dinamicamente a capacidade máxima de produção
- Aplicar controle de acesso baseado em perfil de usuário (ADMIN / USER)
- Garantir integridade de dados via regras de negócio

---

## 🔐 Autenticação

A autenticação é baseada em **JWT**.

Após login, o token deve ser enviado no header:

```
Authorization: Bearer <jwt-token>
```

---

## 📄 API Contract

Toda a documentação detalhada de endpoints, requests e responses está disponível em:

👉 **[API_CONTRACT.md](docs/api-contract.md)**

---

## 🗄️ Banco de Dados

- PostgreSQL
- Versionamento via Flyway
- Migrations localizadas em:
```
src/main/resources/db/migration
```
