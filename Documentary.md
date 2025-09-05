# Blueprint ERP Avícola Perú (2025)

Nota: Este documento ahora es un resumen ejecutivo. Las secciones detalladas fueron movidas a `docs/` para facilitar el mantenimiento y la navegación.

Guía de lectura rápida:

- Objetivos y visión: `docs/arquitectura/vision.md`
- Módulos funcionales (dominios): `docs/domains.md`
- Arquitectura técnica: `docs/arquitectura/tecnica.md`
- Integraciones Perú (SUNAT/SENASA/Bancos/GS1/EDI): `docs/integraciones_peru.md`
- Modelo de datos y dominio: `docs/datos/modelo_dominio.md`
- Analítica, IA y copilotos: `docs/ia_copilotos.md`
- UX y experiencia de usuario: `docs/ux.md`
- Seguridad y cumplimiento: `docs/seguridad_cumplimiento.md`
- Entregables por fase (MVP ➜ Fase 3): `docs/entregables.md`
- Costeo y TCO: `docs/costos_tco.md`
- QA y calidad: `docs/qa_calidad.md`
- Riesgos y mitigaciones: `docs/riesgos.md`
- Flujos de demo: `docs/flows_demo.md`
- Stack de referencia: `docs/stack.md`
- Roadmap de implantación: `docs/roadmap_implantacion.md`
- KPIs comité directivo: `docs/kpis.md`
- Monorepo y DDD (estructura del repo): `docs/monorepo.md`
- ADRs (decisiones de arquitectura): `docs/adr/`
- Índice completo y generado: `docs/INDEX.md`

**Rol:** Arquitecto de software senior, especialista en ERP avícola.
**Influencias:** mejores prácticas de EE. UU. (eficiencia operativa, trazabilidad, compliance) y China (automatización a escala, IoT masivo), adaptado al contexto peruano (SUNAT, SENASA, logística y conectividad locales).

---

## 1) Objetivos estratégicos

* **Trazabilidad extremo a extremo (granaja ➜ planta ➜ distribución ➜ retail)** en tiempo real.
* **Optimización de BIOSEGURIDAD** (alertas tempranas, control de accesos, rutas y limpieza).
* **Ciclo de vida completo del ave** (reproductoras, incubación, engorde, planta, subproductos).
* **Finanzas y fiscalidad Perú-ready** (Factura electrónica, SEE-SOL, OSE/PSE, libros electrónicos, detracciones, percepciones/retenciones, NIIF locales).
* **Eficiencia de alimento** (FCR/ICAs, curvas de crecimiento, formulación y compras just-in-time).
* **Analítica predictiva** (mortalidad, demanda, mermas, mantenimiento predictive).
* **Operación offline-first** para granjas y centros con conectividad limitada.

---

## 2) Módulos funcionales (dominios)

1. **Maestros corporativos**

   * Granjas, galpones, lotes, razas/estirpes, jerarquía organizativa, ubicaciones SIG.
   * Proveedores, clientes, bancos, rutas y tarifas.
2. **Reproductoras**

   * Manejo de macho/hembra, densidad, huevos fértiles/infértiles, postura, fertilidad.
3. **Incubación**

   * Programación de incubadoras/nacedoras, volteo, temperatura/humedad, nacimientos, descarte.
4. **Engorde**

   * Recepción de pollitos, **consumo de alimento/agua por día**, peso promedio, mortalidad, uniformidad, tratamientos.
5. **Nutrición & Alimento**

   * Formulación (matriz nutricional), compras de insumos, costos estándar vs reales, **reserva inteligente de stock**.
6. **Sanidad & Bioseguridad**

   * Vacunación, medicación, bitácoras de limpieza, **control de accesos y rutas** (zonificación caliente/fría), auditorías HACCP/BPM.
7. **Mantenimiento (EAM)**

   * Equipos, órdenes de trabajo, repuestos, mantenimiento preventivo/predictivo (IoT vibración/temperatura).
8. **Planta de beneficio**

   * Programación de faena, captura, transporte vivo, aturdido, eviscerado, **clasificación por calibres**, rendimiento canal/subproductos.
9. **Procesados y empaques**

   * Desposte, IQF, marinado, etiquetado **GS1-128/QR** con trazabilidad.
10. **Calidad e Inocuidad**

    * PCC, microbiología, temperatura, **liberación de lotes**.
11. **Logística**

    * WMS (ubicaciones, FEFO), TMS (rutas, GPS, temperatura de cadena de frío), cross-docking.
12. **Comercial & Precios**

    * Pedidos, promociones, contratos mayoristas, **canales modernos** y tradicionales, ecommerce B2B.
13. **Finanzas & Contabilidad Perú**

    * CxC/CxP, tesorería, conciliaciones, **detracciones**, plan contable, **libros electrónicos**, **factura electrónica** (SEE-SOL/OSE), **guías de remisión**.
14. **RR. HH. & Planillas**

    * Asistencia biométrica, cuadrillas, incentivos por rendimiento, **CTS**, vacaciones, ESSALUD/AFP.
15. **Sostenibilidad**

    * Huella de carbono/agua, gestión de residuos, subproductos (harina de pluma, vísceras).
16. **Governanza & Auditoría**

    * Trazas, SoD, **GRC** (riesgos, controles).

---

## 3) Arquitectura técnica (2025)

**Estilo:** **modular monolith** + **microservicios tácticos** (IoT/streaming) ➜ simplicidad operativa + escalabilidad.

* **Frontend**:

  * **Web**: Next.js (React 18/Server Actions), Tailwind, shadcn/ui, i18n (es-PE).
  * **Móvil**: React Native (offline-first via SQLite/WatermelonDB), cámara para QR/RFID.
* **Backend**:

  * **Dominio**: Kotlin (Spring Boot 3.3) o .NET 9 para equipos Microsoft;
  * **Servicios de tiempo real/alto rendimiento**: **Rust** (Axum) para gateways IoT y CV.
  * **API**: GraphQL para consultas complejas + REST para integraciones; WebSockets para tiempo real.
* **Mensajería/Streaming**: **Redpanda** (compatible Kafka) para eventos (lotes, consumos, lecturas IoT).
* **IoT**: **MQTT** (EMQX) + **OPC-UA** para planta; **gemelo digital** de granja/planta.
* **Datos**:

  * OLTP: **PostgreSQL 16** (particionado por compañía/lote).
  * Series temporales: **TimescaleDB** (telemetría: temp/HR/consumos/pesos).
  * Analítica/BI: **ClickHouse** para agregados veloces + **Lakehouse** (Apache Iceberg en **MinIO/S3**).
  * ML sandbox: **Feature Store** (Feast) + **MLFlow**.
* **Identity & Seguridad**: **Keycloak** (OIDC/SAML), RBAC/ABAC, SoD, **FIDO2**.
* **Observabilidad**: OpenTelemetry + Prometheus + Grafana; tracing distribuido.
* **Infra**: Kubernetes (K3s en edge / AKS/EKS/GKE en cloud), **ArgoCD** GitOps, **Kyverno** políticas.
* **Costos Perú**: opción **on-prem/edge** híbrido para zonas con baja conectividad.

**Patrones clave**

* **Event Sourcing + Outbox Pattern** para evitar pérdida de eventos con SUNAT/IoT.
* **CQRS** para paneles operativos de planta y granja.
* **Saga/Choreography** para procesos largos (faena, logística).

---

## 4) Integraciones Perú (regulatorias y ecosistema)

* **SUNAT**: Factura electrónica (SEE-SOL u OSE), Boletas, Notas, **Guías de Remisión** (Transportista/Remitente), **Libros electrónicos** (PLE), consulta RUC.
* **SENASA**: certificaciones sanitarias, movimientos animales, inspecciones.
* **Bancos**: BCP, Interbank, BBVA (pagos masivos, conciliación MT940/ISO 20022).
* **Transportes**: GPS/API de flotas (Geotab, TrackLink), termógrafos.
* **GS1 Perú**: codificación GTIN, SSCC, **etiquetas GS1-128/QR**.
* **Ecommerce B2B/EDI**: marketplaces, retailers (EDI 850/856/810), integradores locales.

---

## 5) Datos y modelo de dominio (núcleo)

* **Aves**(id\_ave, id\_lote, estirpe, fecha\_nacimiento, estado, peso\_actual, localización)
* **Lote**(id\_lote, ciclo, granja, galpón, fecha\_ingreso, objetivo\_peso, FCR\_objetivo)
* **Consumo**(id\_lote, fecha, alimento\_kg, agua\_l, energía\_kwh)
* **Sanidad**(id\_lote, fecha, vacuna, dosis, tratamiento, responsable)
* **Mortalidad**(id\_lote, fecha, cantidad, causa)
* **Movimientos**(origen, destino, fecha, guía, transportista, temp\_min/max)
* **Faena**(id\_faena, lotes, peso\_vivo, rendimiento\_canal, mermas)
* **Producto**(SKU, presentación, calibre, lote\_origen, fecha\_venc, SSCC)
* **Calidad**(id\_muestreo, PCC, temp, micro, resultado, liberación)
* **Finanzas**(asiento, centro\_costo, lote, producto, detracción, IGV)

**Claves transversales**: *company\_id, site\_id, lot\_id, batch\_id, sscc, glp (guía)*.

---

## 6) Analítica, IA y copilotos

**KPIs críticos**

* **FCR/ICA**, **mortalidad diaria (%)**, uniformidad (%), **rendimiento canal (%)**, mermas (%), costo por kg vivo, temperatura/humedad fuera de rango (minutos).

**Modelos**

* Pronóstico de **demanda multicanal** (retail/foodservice).
* **FCR predictor** por lote (features: genética, clima, alimento, densidad, historial).
* **Detección temprana de enfermedades** (anomalías en consumo/peso/temperatura).
* **Optimización de rutas** (VRP con ventanas de tiempo y control de frío).
* **Visión computacional** para **clasificación de canal/calibres** y **conteo de aves** en captura.

**Copiloto Avícola**

* Asistente LLM con RAG sobre SOPs, HACCP, normativa SENASA, políticas internas.
* **Agentes** para cierre contable, conciliaciones, generación de órdenes de compra.

---

## 7) Experiencia de usuario (EE. UU./China ➜ Perú)

* **Panel Operativo 1-clic** por rol (Granjero, Veterinario, Jefe de Planta, Logística, Contador).
* **Modo Guantes** (UI táctil grande) en planta; **modo oscuro** nocturno en granja.
* **Flujos escáner**: QR/RFID para todos los movimientos.
* **Alertas multimodal**: SMS/WhatsApp/Push cuando KPIs cruzan umbrales.
* **Flujo sin conexión**: captura local ➜ sync eventual (conflictos resueltos por *last-writer-wins* + reglas).

---

## 8) Seguridad, calidad y cumplimiento

* **SoD** (segregación de funciones), **bóveda de secretos** (Vault), cifrado en tránsito (mTLS) y en reposo (KMS).
* **Backups 3-2-1** (incluye MinIO/S3 y cinta fría si es necesario).
* **Auditoría completa** (quién, qué, cuándo, dónde) + **hash encadenado** para inmutabilidad.
* **BPM/HACCP** digitalizados con evidencias (fotos, firmas, timestamps).

---

## 9) Entregables clave (MVP ➜ Fase 3)

**MVP (0–3 meses)**

* Engorde (lotes, consumos, mortalidad, pesos), panel de KPIs, móvil offline, facturación electrónica básica y guías, WMS simple.

**Fase 1 (3–6 meses)**

* Incubación, sanidad, mantenimiento preventivo, integraciones SUNAT completas, TMS con GPS y cadena de frío, BI en ClickHouse.

**Fase 2 (6–9 meses)**

* Planta de beneficio, visión computacional para clasificación básica, EAM avanzado, RR. HH./planillas, ecommerce B2B.

**Fase 3 (9–12 meses)**

* Optimización de rutas, predicción FCR/mortalidad, EDI con retailers, sostenibilidad, agentes contables.

---

## 10) Costeo y TCO (lineamientos)

* **Licenciamiento**: open source primero (reducción de OPEX), add-ons premium (CV/optimización).
* **Infra híbrida**: core cloud + edge (granjas/planta) con K3s;
* **Observabilidad/automatización** reduce downtime y costos ocultos.

---

## 11) Pruebas y calidad

* **QA continuo**: contract testing (PACT), pruebas hardware-in-the-loop para IoT.
* **Canary releases** y *feature flags* (ConfigCat/Unleash).
* **KPIs de confiabilidad**: Apdex, MTTR, tasa de errores de sincronización offline.

---

## 12) Riesgos y mitigaciones

* **Conectividad rural**: edge cache + compresión + sincronización diferida.
* **Cambio cultural**: capacitaciones y UI simplificada, incentivos por captura oportuna.
* **Regulatorio**: backlogs dedicados a SUNAT/SENASA; sandbox de pruebas.

---

## 13) Demo de flujos clave (resumen)

1. **Recepción de pollitos ➜ alta de lote ➜ checklist bioseguridad ➜ programación de visitas veterinarias.**
2. **Captura de datos diarios** (móvil offline): alimento/agua/peso ➜ sincroniza ➜ alertas si FCR proyectado empeora.
3. **Despacho a planta**: guía electrónica, control de temperatura del camión, arribo, faena, clasificación y etiquetado con SSCC.
4. **Venta B2B**: pedido ➜ asignación FEFO ➜ factura electrónica ➜ tracking GPS y temperatura hasta entrega.

---

## 14) Stack de referencia (componentes)

* **Frontend**: Next.js, React Native, Zustand/Redux Toolkit, TanStack Query.
* **Backend**: Spring Boot/Kotlin + Axum/Rust (IoT), GraphQL (Apollo), REST (Fastify adapter opcional).
* **Data**: PostgreSQL, TimescaleDB, ClickHouse, MinIO + Apache Iceberg, DuckDB para análisis ad-hoc.
* **ML**: Python 3.12, PyTorch/LightGBM, MLFlow, Feast.
* **DevEx**: Nx/Turborepo, Vitest/Jest, Playwright, OpenAPI/GraphQL Codegen.
* **Ops**: K8s, ArgoCD, Kyverno, Prometheus, Grafana, Loki, Tempo, Vault.

---

## 15) Roadmap de implantación por empresa (perfiles)

* **Integración con contabilidad existente** (SAP/Oracle/soft local) vía conectores.
* **Piloto en 1 granja + 1 planta**, luego escalado por regiones.
* **Playbook de despliegue** con checklists (SENASA, SUNAT, TI, formación usuarios).

---

## 16) KPIs para comité directivo

* Costo/kg vivo, margen por canal, puntualidad de entregas %, % lotes liberados sin observación, roturas de cadena de frío, OEE planta, días de inventario, ciclo de caja, cumplimiento PLE, tasa de incidentes bioseguridad.

---

## 17) Próximos pasos sugeridos

1. Seleccionar **2–3 procesos MVP** (Engorde + Factura/Guías + WMS básico).
2. Definir **sistemas legados** a integrar (conta, nómina, bancos).
3. Elegir **esquema de despliegue** (cloud vs híbrido) y presupuesto de edge.
4. Levantar **datos maestros** y catalogarlos.
5. Arrancar **PoC de IoT** (sensores ambiente + pesaje + termógrafos).

> Este blueprint está listo para convertirse en backlog de épicas/historias y en un diagrama C4 detallado (Contexto, Contenedores, Componentes, Código) con decisiones ADR.

---

## 18) Organización del repositorio (monorepo) y DDD

Para evitar duplicación y favorecer la modularidad, se reorganiza el repositorio en un monorepo con bounded contexts explícitos y librerías compartidas.

Arquitectura propuesta del repo:

```text
ERP_Avicola
├── apps                    # Aplicaciones ejecutables
│   ├── backend
│   │   ├── api_gateway     # Gateway unificado
│   │   ├── core_farm       # Granjas, lotes, producción
│   │   ├── core_inventory  # Inventarios, WMS
│   │   ├── core_sales      # Ventas, pedidos, clientes
│   │   ├── core_trace      # Trazabilidad, SENASA
│   │   └── core_finance    # Finanzas, SUNAT, PLE
│   └── frontend
│       ├── web_app
│       └── mobile_app
│
├── libs                    # Librerías compartidas
│   ├── domain              # Modelos y lógica común (DDD)
│   ├── events              # Esquemas/contratos de eventos (Kafka/Redpanda)
│   ├── utils               # Utilidades y helpers
│   └── security            # Autenticación, roles, Keycloak SDK
│
├── ml                      # Ciencia de datos + modelos productivos
│   ├── notebooks           # Experimentos
│   └── serving             # Modelos desplegables como microservicios
│
├── infra                   # Infraestructura como código
│   ├── ci_cd               # Workflows, GitOps (ArgoCD)
│   ├── k8s
│   │   ├── dev
│   │   ├── staging
│   │   └── prod
│   └── terraform
│
├── docs                    # Documentación
│   ├── arquitectura
│   ├── requerimientos
│   └── adr                 # Architecture Decision Records
│
└── tools                   # Scripts de automatización
    └── migration_tools
```

Directrices clave:

- **Shared/common**: mover lógica y modelos reutilizables (usuario, auth, eventos) a `libs/` para eliminar duplicación.
- **DDD y bounded contexts**: `core_farm`, `core_inventory`, `core_sales`, `core_trace`, `core_finance` encapsulan dominios; evitan dependencias cíclicas y permiten escalamiento por contexto.
- **ML productivo**: `ml/serving` expone modelos como microservicios (REST/GRPC), consumidos por `apps/backend` y orquestados en `infra/k8s`.
- **Infra por entorno**: manifiestos separados por `dev`, `staging`, `prod` en `infra/k8s/` y módulos/variables por entorno en `infra/terraform`.
- **ADR**: decisiones arquitectónicas versionadas en `docs/adr` con plantilla base `0001-template.md`.
- **Monorepo tooling**: usar **Nx/Turborepo** para JS/TS (frontend, gateways), y **Bazel** o Nx + Docker para stacks mixtos (Kotlin, Rust, Python). Soportar caché, graph de dependencias, y tareas por proyecto.

Implicancias operativas:

- Pipelines por contexto y librerías (build/test/lint/release) con cacheo incremental.
- Versionado semántico por paquete/contexto cuando aplique; contratos en `libs/events` con control de compatibilidad.
- Política de dependencias: `apps/*` pueden depender de `libs/*`; `libs/*` no dependen de `apps/*`.
