#!/usr/bin/env bash
set -u
if [ $# -lt 1 ]; then echo "uso: $0 <idA> [idB]"; exit 1; fi
A=$1
B=${2:-$1}
BASE="http://localhost:8090/api/propostas"

curl -s -o /dev/null -w "req 1: %{http_code}\n" -X PATCH "$BASE/$A/finalizar-acordo" &
curl -s -o /dev/null -w "req 2: %{http_code}\n" -X PATCH "$BASE/$B/finalizar-acordo" &
wait