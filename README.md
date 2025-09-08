# 💰 Movimentação Financeira API

> API REST desenvolvida em Java para controle de movimentações financeiras (entradas e saídas).

Este projeto foi criado como desafio pessoal para **praticar desenvolvimento de APIs REST** em Java.  
A aplicação permite registrar movimentações financeiras (créditos e débitos), consultar saldos e listar transações.

---

## 🚀 Funcionalidades

- ➕ **Registrar crédito**
- ➖ **Registrar débito**
- 📋 **Listar todas as movimentações**
- 💵 **Consultar saldo total**

---

## 🛠 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot** (framework principal da API REST)
- **Gradle** (gerenciamento de build e dependências)
- **Spring Data JPA** (persistência)
- **Banco de dados H2 (em memória)** para testes
- **JUnit 5** e **Mockito** (testes unitários)

---

## ▶️ Como Executar

### Pré-requisitos
- [Java 17+](https://adoptium.net/)
- [Gradle](https://gradle.org/) (ou usar o wrapper incluso)

### Passos
1. Clone o repositório:
   ```bash
   git clone https://github.com/CeloHelp/movimentacao-financeira.git
   cd movimentacao-financeira
