#!/usr/bin/env bash
set -euo pipefail

BASE_URL="http://localhost:8090"
SUFIXO=$(date +%s)$RANDOM

echo "==> 1. Criando usuários A, B e C..."
RESP_A=$(curl -s -X POST "$BASE_URL/usuarios" -H "Content-Type: application/json" -d "{\"nome\": \"Usuario A\", \"email\": \"usuarioA_${SUFIXO}@trokr.com\"}")
ID_A=$(echo "$RESP_A" | jq -r '.id')

RESP_B=$(curl -s -X POST "$BASE_URL/usuarios" -H "Content-Type: application/json" -d "{\"nome\": \"Usuario B\", \"email\": \"usuarioB_${SUFIXO}@trokr.com\"}")
ID_B=$(echo "$RESP_B" | jq -r '.id')

RESP_C=$(curl -s -X POST "$BASE_URL/usuarios" -H "Content-Type: application/json" -d "{\"nome\": \"Usuario C\", \"email\": \"usuarioC_${SUFIXO}@trokr.com\"}")
ID_C=$(echo "$RESP_C" | jq -r '.id')
echo "    OK! (A=$ID_A | B=$ID_B | C=$ID_C)"

echo "==> 2. Criando itens..."
RESP_ITEM_A=$(curl -s -X POST "$BASE_URL/itens" -H "Content-Type: application/json" -d "{\"titulo\": \"Produto A\", \"descricao\": \"Prod\", \"usuarioId\": $ID_A, \"categoria\": \"PRODUTO\"}")
ITEM_A=$(echo "$RESP_ITEM_A" | jq -r '.id')

RESP_ITEM_B=$(curl -s -X POST "$BASE_URL/itens" -H "Content-Type: application/json" -d "{\"titulo\": \"Servico B\", \"descricao\": \"Serv\", \"usuarioId\": $ID_B, \"categoria\": \"SERVICO\"}")
ITEM_B=$(echo "$RESP_ITEM_B" | jq -r '.id')

RESP_ITEM_C=$(curl -s -X POST "$BASE_URL/itens" -H "Content-Type: application/json" -d "{\"titulo\": \"Exp C\", \"descricao\": \"Exp\", \"usuarioId\": $ID_C, \"categoria\": \"EXPERIENCIA\"}")
ITEM_C=$(echo "$RESP_ITEM_C" | jq -r '.id')
echo "    OK! (ItemA=$ITEM_A | ItemB=$ITEM_B | ItemC=$ITEM_C)"

echo "==> 3. Montando Negociação 1..."
RESP_RAIZ_1=$(curl -s -X POST "$BASE_URL/api/propostas" -H "Content-Type: application/json" -d "{\"descricao\": \"Proposta Raiz 1\", \"usuarioId\": $ID_A, \"itemId\": $ITEM_A}")
RAIZ_1=$(echo "$RESP_RAIZ_1" | jq -r '.id')

curl -s -X PATCH "$BASE_URL/api/propostas/$RAIZ_1/solicitar-homologacao" >/dev/null
curl -s -X PATCH "$BASE_URL/api/propostas/$RAIZ_1/aprovar-homologacao" >/dev/null

RESP_CONTRA_1=$(curl -s -X POST "$BASE_URL/api/propostas/$RAIZ_1/contraproposta" -H "Content-Type: application/json" -d "{\"descricao\": \"Contraproposta 1\", \"usuarioId\": $ID_B, \"itemId\": $ITEM_B, \"propostaAnteriorId\": $RAIZ_1}")
CONTRA_1=$(echo "$RESP_CONTRA_1" | jq -r '.id')

curl -s -X PATCH "$BASE_URL/api/propostas/$CONTRA_1/enviar-contraproposta" >/dev/null
curl -s -X PATCH "$BASE_URL/api/propostas/$CONTRA_1/aceitar-contraproposta" >/dev/null
echo "    Pronta: Proposta Base = $RAIZ_1"

RESP_RAIZ_2=$(curl -s -X POST "$BASE_URL/api/propostas" -H "Content-Type: application/json" -d "{\"descricao\": \"Proposta Raiz 2\", \"usuarioId\": $ID_B, \"itemId\": $ITEM_B}")
RAIZ_2=$(echo "$RESP_RAIZ_2" | jq -r '.id')

curl -s -X PATCH "$BASE_URL/api/propostas/$RAIZ_2/solicitar-homologacao" >/dev/null
curl -s -X PATCH "$BASE_URL/api/propostas/$RAIZ_2/aprovar-homologacao" >/dev/null

RESP_CONTRA_2=$(curl -s -X POST "$BASE_URL/api/propostas/$RAIZ_2/contraproposta" -H "Content-Type: application/json" -d "{\"descricao\": \"Contraproposta 2\", \"usuarioId\": $ID_C, \"itemId\": $ITEM_C, \"propostaAnteriorId\": $RAIZ_2}")
CONTRA_2=$(echo "$RESP_CONTRA_2" | jq -r '.id')

curl -s -X PATCH "$BASE_URL/api/propostas/$CONTRA_2/enviar-contraproposta" >/dev/null
curl -s -X PATCH "$BASE_URL/api/propostas/$CONTRA_2/aceitar-contraproposta" >/dev/null
echo "    Pronta: Proposta Base = $RAIZ_2"

echo ""
echo "================================================================="
echo "ID da Troca 1: $RAIZ_1"
echo "ID da Troca 2: $RAIZ_2"
echo "-> Corrida 1: ./corrida.sh $RAIZ_1"
echo "-> Corrida 2: ./corrida.sh $RAIZ_1 $RAIZ_2"
echo "================================================================="