#!/usr/bin/env bash
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")"/../.. && pwd)"
DOCS_DIR="$ROOT_DIR/docs"
INDEX_FILE="$DOCS_DIR/INDEX.md"

header() {
  cat <<HDR
# Documentación - Índice

Este índice se genera automáticamente. No editar a mano.

HDR
}

list_dir() {
  local path="$1"
  local prefix="$2"
  find "$path" -maxdepth 1 -type f -name "*.md" -printf "%P\n" | sort | while read -r f; do
    [ "$f" = "INDEX.md" ] && continue
    echo "- ${prefix}[${f%.md}](${prefix}${f})"
  done
}

list_tree() {
  # Nivel raíz
  list_dir "$DOCS_DIR" ""
  # Subdirectorios
  find "$DOCS_DIR" -mindepth 1 -maxdepth 1 -type d | sort | while read -r d; do
    rel="${d#$DOCS_DIR/}"
    echo "\n## $rel"
    list_dir "$d" "$rel/"
  done
}

main() {
  mkdir -p "$DOCS_DIR"
  {
    header
    list_tree
  } > "$INDEX_FILE"
  echo "Generado: $INDEX_FILE"
}

main "$@"
