# Atlas Bank

Banking API built with Spring Boot 4 following **Hexagonal Architecture** (Ports & Adapters). Designed as a reference project for clean architecture, domain-driven design patterns, and AI-assisted banking operations.

## Tech Stack

- **Java 21** / **Spring Boot 4.0.7**
- **Spring AI 2.0** (OpenAI-compatible, configured for Ollama + qwen3:8b)
- **Spring Security** with OAuth2 / JWT (Keycloak)
- **Spring Data JPA** with H2 (in-memory)
- **MapStruct** + **Lombok**
- **ArchUnit** for architecture enforcement tests

## Architecture

```
src/main/java/com/atlas/bank/
├── application/
│   ├── command/          # Use case input models
│   ├── port/
│   │   ├── in/           # Driving ports (use cases)
│   │   └── out/          # Driven ports (repository interfaces)
│   ├── service/          # Use case implementations
│   └── validation/       # Fraud validation
├── domain/
│   ├── event/            # Domain events
│   ├── exception/        # Domain exceptions
│   ├── model/            # Entities and value objects
│   ├── service/          # Domain services
│   └── strategy/         # Fee calculation strategies
└── infrastructure/
    ├── adapter/
    │   ├── in/
    │   │   ├── ai/       # AI chat controller (Spring AI)
    │   │   └── rest/     # REST controllers
    │   └── out/
    │       └── persistence/  # JPA repositories and mappers
    └── config/           # Security, AI, bean configuration
```

## API Endpoints

### Accounts

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/accounts` | Create account |
| GET | `/api/v1/accounts` | List all accounts |
| GET | `/api/v1/accounts/{id}` | Get account by ID |
| PATCH | `/api/v1/accounts/{id}/close` | Close account (ADMIN only) |
| GET | `/api/v1/accounts/{id}/dashboard` | Account dashboard |

### Transactions

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/transactions/transfer` | Transfer money between accounts |
| GET | `/api/v1/transactions/{id}/transactions` | Get transactions by account |

### AI Chat

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/ai/chat` | Natural language banking assistant |

## Running

### Prerequisites

- Java 21+
- Maven 3.9+
- Keycloak running on `localhost:8181` (realm: `atlas-bank`)
- Ollama running on `localhost:11434` with `qwen3:8b` (for AI features)

### Start

```bash
./mvnw spring-boot:run
```

The H2 console is available at `/h2-console` (JDBC URL: `jdbc:h2:mem:atlasbank`, user: `sa`).

## Testing

```bash
./mvnw test
```

Tests include:
- **Domain unit tests** — Account, Money, Email value objects
- **Architecture tests** (ArchUnit) — hexagonal layer enforcement, naming conventions, no cyclic dependencies, security isolation

## Key Design Patterns

- **Hexagonal Architecture** — strict separation between domain, application, and infrastructure
- **Command pattern** — `TransferMoneyCommand`, `CreateAccountCommand`, `CloseAccountCommand`
- **State pattern** — transaction lifecycle (Pending, Validated, Executed, Rejected, Reversed)
- **Strategy pattern** — fee calculation per account type
- **Domain Events** — `TransactionExecutedEvent`, `AccountClosedEvent`
- **Fraud validation** — pluggable fraud check via driven port
