# Case Velsis

Um projeto fullstack simples para gerenciar usuários com frontend em **Vue 3**, backend em **Spring Boot** e banco de dados **PostgreSQL**.  

O frontend é servido via **Nginx** após build e o backend via **Spring Boot**.

---

## Tecnologias

- **Frontend:** Node >= 20, Vue 3, Vite e Axios
- **Backend:** Java 21, Spring Boot 3, Spring Data JPA, PostgreSQL
- **Banco de dados:** PostgreSQL 15
- **Containerização:** Docker, Docker Compose
- **Outros:** Nginx (para servir frontend buildado)

---

## Funcionalidades

- Criar, ler, atualizar e deletar usuários
- Validação de campos
- Paginação de usuários
- Backend configurado com JPA/Hibernate

---
## Portas

- frontend: http://localhost:3000/
- backend: http://localhost:8080/
- postgres: http://localhost:5432/

---

## Pré-requisitos

- Docker
- Docker Compose
- Navegador moderno

---

## Observações
Por se tratar de um projeto pequeno, e também por buscar uma implementação mais vanilla, não foi utilizado nenhuma biblioteca de componentes como o Vuetify ou o PrimeVue, mas costumo utilizar bastante ambos.
Evitei também utilizar lombok para trazer uma implementação mais raíz do Java, por mais que tenha sido utilizado o Spring boot. Também não usei anotações de validação mais avançadas para mostrar a implementação manual de alguns processos de validação.

Espero que gostem do projeto!

## Como rodar

Clone o repositório:
- git clone git@github.com:gabrielferreiradasilva04/case-velsis-cadastro-simples.git ou https://github.com/gabrielferreiradasilva04/case-velsis-cadastro-simples.git
- cd crud-usuario-simples-vue

- docker-compose up --build
