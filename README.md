# ERP Avícola Perú

Sistema ERP especializado para la industria avícola en Perú, con enfoque en trazabilidad end-to-end, bioseguridad, finanzas locales (SUNAT/PLE), logística en frío e integración de analítica/ML.

## Características clave
- Trazabilidad granja ➜ planta ➜ distribución ➜ retail
- Bioseguridad (controles, auditorías, HACCP/BPM)
- Finanzas Perú-ready (SEE-SOL/OSE, PLE, detracciones)
- Operación offline-first en móvil (granja/planta)
- Analítica predictiva y ML (FCR, demanda, mermas)

## Estructura del repositorio (monorepo)
Consulta detalles en `docs/monorepo.md`. Vista general:

```
apps/        # Aplicaciones (backend/frontend)
libs/        # Librerías compartidas (domain, events, utils, security)
ml/          # Notebooks y serving de modelos
infra/       # IaC (k8s por entorno, terraform, ci_cd)
docs/        # Documentación y ADRs
tools/       # Scripts (docs, git, migraciones)
```

## Documentación
- Índice general (auto-generado): `docs/INDEX.md`
- Blueprint y visión: `Documentary.md`
- ADRs: `docs/adr/`

## Requisitos (referencia)
- Node.js 20+, pnpm/npm (frontend/tooling opcional)
- JDK 21 (backend Kotlin) o .NET 9 (alternativa)
- Rust toolchain (gateways/IoT opcional)
- Python 3.12 (ML)
- Docker + Kubernetes (despliegue), Terraform (IaC)

## Flujo de trabajo
- Convencional Commits (feat/fix/docs/refactor/chore/ci)
- PRs con validación de documentación en CI
- Contratos de eventos en `libs/events`

## Scripts útiles
- Generar índice de docs: `bash tools/docs/generate_index.sh`
- Validar docs: `bash tools/docs/validate_docs.sh`
- Check pre-commit (instalar hook): `bash tools/git/install_git_hooks.sh`

## Contribución
1. Crea una rama desde `main`
2. Asegura docs y ADRs actualizados cuando cambie arquitectura
3. Corre validaciones locales (pre-commit o scripts)

## Licencia
Pendiente de definir.

