#!/usr/bin/env bash
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")"/../.. && pwd)"
HOOKS_DIR="$ROOT_DIR/.git/hooks"
PRE_COMMIT="$HOOKS_DIR/pre-commit"

if [ ! -d "$ROOT_DIR/.git" ]; then
  echo "[WARN] No es un repositorio Git. Inicializa con 'git init' y re-ejecuta este script." >&2
  exit 0
fi

mkdir -p "$HOOKS_DIR"
cat > "$PRE_COMMIT" << 'HEOF'
#!/usr/bin/env bash
set -euo pipefail
# Pre-commit: valida documentación e índice
if [ -x "tools/docs/check_docs.sh" ]; then
  bash tools/docs/check_docs.sh
else
  echo "[INFO] tools/docs/check_docs.sh no encontrado o sin permisos; se omite validación de docs."
fi
HEOF
chmod +x "$PRE_COMMIT"

echo "Hook pre-commit instalado en $PRE_COMMIT"
