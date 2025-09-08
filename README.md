# 💰 Movimentação Financeira API

> API REST desenvolvida em Java para controle de movimentações financeiras (entradas e saídas).

Este projeto foi criado como desafio pessoal para **praticar desenvolvimento de APIs REST** em Java.  
A aplicação permite registrar movimentações financeiras (créditos e débitos), consultar saldos e listar transações.

---

## 📚 Sumário
- [Funcionalidades](#-funcionalidades)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Como Executar](#️-como-executar)
- [Endpoints da API](#-endpoints-da-api)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Testes](#-testes)
- [Futuras Melhorias](#-futuras-melhorias)
- [Autor](#-autor)

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
   ```

2. Compile e rode a aplicação:
   ```bash
   ./gradlew bootRun
   ```

3. A API estará disponível em:
   ```
   http://localhost:8080
   ```

---

## 📌 Endpoints da API

### 🔹 Listar movimentações
```
GET /movimentacoes
```
**Resposta:**
```json
[
  {
    "id": 1,
    "tipo": "CREDITO",
    "valor": 100.0
  },
  {
    "id": 2,
    "tipo": "DEBITO",
    "valor": 50.0
  }
]
```

### 🔹 Registrar crédito
```
POST /movimentacoes/credito
```
**Body:**
```json
{
  "valor": 200.0
}
```

### 🔹 Registrar débito
```
POST /movimentacoes/debito
```
**Body:**
```json
{
  "valor": 80.0
}
```

### 🔹 Consultar saldo
```
GET /movimentacoes/saldo
```
**Resposta:**
```json
{
  "saldo": 120.0
}
```

---

## 📂 Estrutura do Projeto

```
movimentacao-financeira/
├─ src/
│  ├─ main/java/com/celohelp/movimentacao  # Código-fonte principal
│  │   ├─ controller/                      # Endpoints REST
│  │   ├─ service/                         # Regras de negócio
│  │   └─ model/                           # Entidades (Movimentacao, etc.)
│  └─ test/java/...                        # Testes unitários
├─ build.gradle                            # Configuração do Gradle
├─ settings.gradle                         # Configuração do projeto
└─ README.md
```

---

## 🧪 Testes

Rodar os testes automatizados:
```bash
./gradlew test
```

---

## 📌 Futuras Melhorias

- Implementar paginação e filtros nas listagens
- Adicionar autenticação (Spring Security / JWT)
- Persistir dados em banco de dados relacional (MySQL ou PostgreSQL)
- Gerar relatórios em CSV/PDF

---

## 👨‍💻 Autor

Desenvolvido por [**Marcelo Henrique Pacobello**](https://www.linkedin.com/in/marcelo-henrique-pacobello/)  
📧 Contato: **marcelopacobello@gmail.com**
