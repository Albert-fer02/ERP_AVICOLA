#!/usr/bin/env bash
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")"/../.. && pwd)"
DOCS_DIR="$ROOT_DIR/docs"
TMP_DIR="$(mktemp -d)"
TMP_INDEX="$TMP_DIR/INDEX.md"

# Validate headers and basic formatting
"$ROOT_DIR/tools/docs/validate_docs.sh"

# Generate index to temp and compare with committed file
"$ROOT_DIR/tools/docs/generate_index.sh" >/dev/null
cp "$DOCS_DIR/INDEX.md" "$TMP_INDEX"
"$ROOT_DIR/tools/docs/generate_index.sh" >/dev/null

if ! diff -u "$TMP_INDEX" "$DOCS_DIR/INDEX.md" >/dev/null; then
  echo "[ERROR] docs/INDEX.md no está actualizado. Ejecuta tools/docs/generate_index.sh y commitea los cambios."
  echo "--- Diff ---"
  diff -u "$TMP_INDEX" "$DOCS_DIR/INDEX.md" || true
  exit 1
fi

echo "Docs OK."
