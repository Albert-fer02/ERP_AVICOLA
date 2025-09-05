#!/usr/bin/env bash
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")"/../.. && pwd)"
DOCS_DIR="$ROOT_DIR/docs"

fail=0

# Reglas: cada .md debe tener un encabezado de nivel 1 al inicio
while IFS= read -r -d '' f; do
  first_line="$(head -n1 "$f" || true)"
  if [[ ! "$first_line" =~ ^#\  ]]; then
    echo "[ERROR] Falta encabezado nivel 1 en: $f"
    fail=1
  fi
  # Verificar extensión y ruta válida
  if [[ ! "$f" == *.md ]]; then
    echo "[ERROR] No es markdown: $f"; fail=1
  fi
  # Evitar espacios finales
  if grep -RIl $'\r' "$f" >/dev/null 2>&1; then
    echo "[WARN] Saltos de línea Windows en: $f"
  fi
done < <(find "$DOCS_DIR" -type f -name "*.md" -print0)

if [[ $fail -ne 0 ]]; then
  echo "Validación falló."; exit 1
fi

echo "Validación OK."
