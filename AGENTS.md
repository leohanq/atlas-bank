# AGENTS.md

Spring Boot 4.0.7 banking demo (Java 21, Maven). Hexagonal (ports & adapters) architecture, enforced by ArchUnit.

## Build / test (Windows)
- Use the wrapper: `.\mvnw.cmd` (not `mvn`, and not `mvnw`).
- Test: `.\mvnw.cmd test`
- Single test class: `.\mvnw.cmd test -Dtest=MoneyDomainTest`
- ArchUnit rules run as part of `test` (package `com.atlas.bank.archtest`). Code must satisfy them — do not disable.

## Architecture rules (enforced by tests — must not be violated)
- `domain/` = pure Java. NO Spring, NO `application`, NO `infrastructure` imports. Hand-wired as beans in `infrastructure/config/DomainBeanConfig.java`.
- `application/` uses `port.in`/`port.out` interfaces only — never imports `infrastructure`.
- `*Controller` must live in `infrastructure.adapter.in.rest`.
- `*UseCase` must live in `application.port.in` and be an interface; `application.port.out` must be all interfaces.
- Impl of ports go in `infrastructure.adapter.out`.

## Wiring quirks
- Most singletons (domain services, validators, fee strategies) are plain classes registered as `@Bean` in `DomainBeanConfig`, not `@Component`. New domain beans go there, not via annotations.
- Validators/fee strategies run as ordered chains via `@Order` beans.

## Persistence / runtime
- Runtime DB is **H2 in-memory** (`src/main/resources/application.yaml`), `ddl-auto: create-drop`, so data resets each boot. `postgres.yml` exists but the app does NOT use it.
- H2 console enabled at `/h2-console/**` (permitted, CSRF disabled).
- The "external" fraud check adapter is a **stub**: blocks only amounts > 10,000,000.

## Security (OAuth2 / Keycloak)
- App is an OAuth2 resource server. To run protected endpoints you need Keycloak:
  - `docker compose -f keycloak.yaml up -d` → Keycloak on `http://localhost:8181`, admin/admin, realm `atlas-bank`.
  - Roles come from JWT `realm_access.roles` → authorities `ROL_ADMIN`, `ROL_USER` (see `SecurityConfig`).

## MapStruct / lombok
- MapStruct 1.6.3 + Lombok with lombok-mapstruct-binding. If a mapper generates "cannot find getter" errors, check the compiler annotationProcessorPaths in `pom.xml`.