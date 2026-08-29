# Changelog

## [1.2.0] - 2026-08-29

### Features
- add /api/v1 versioning to all routes
- route /api/platform/services/** to service-monitor
- add JavaDoc, health aliases, and component tests

### Bug Fixes
- route /api/v1/catalog/products and /api/v1/catalog/categories to correct controller paths
- use WebFilter instead of GlobalFilter for security headers (covers actuator endpoints)
- use beforeCommit to set X-Content-Type-Options on proxied responses
- restrict catalog public GET to exact paths only (remove /** wildcards)
- public catalog GET paths, security headers, and platform templates
- JSON 401 body, public paths, security headers, CORS
- remove dead /api/support/** gateway predicate
- add platform-config to public paths and update gateway routes
- deduplicate CORS headers from downstream services

### Documentation
- JavaDoc on public methods and REST API reference under doc/
- add complete project documentation

### CI/Build
- fail PRs and stage deploys when tests fail
- trigger first dev build
- use separate GCP project IDs for dev (digi-carts-dev) and prod (digi-carts)All notable changes to **api-gateway** are documented in this file.

The format follows [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project uses [Semantic Versioning](https://semver.org/spec/v2.0.0.html).
The version in this file matches `<version>` in `pom.xml`.

## [1.1.1] - 2026-08-19

### Added
- JavaDoc on all public methods (including constructors and DTO accessors)
- REST API reference at `doc/api.md` (generated from Spring mappings)
- Service overview restored at `doc/README.md`

## [1.1.0] - 2026-08-19

### Added
- `GET /health` and `GET /api/health` liveness JSON (`status` + `service`)
- JavaDoc on public types, `package-info.java`, and the Maven javadoc plugin
- JUnit 5 unit tests and Cucumber component features (Cucumber is excluded from the Maven Surefire unit-test run)
- GitHub Actions `pr-tests.yml`: pull requests to `stage`/`main` run `mvn -B test` and fail the check on failure
- Dev deploy (`deploy-dev.yml`) now runs the same unit tests and deploys only if they pass
- JWT filter treats `/health` and `/api/health` as public paths
