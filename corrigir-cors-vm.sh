#!/bin/bash

# Script para corrigir CORS no backend AWS
# Este script conecta na VM e atualiza a configuração de CORS

set -e

# Configurações
VM_IP="98.83.35.183"
VM_USER="ubuntu"
SSH_KEY="${1:-away-key.pem}"  # Aceita o caminho da chave como argumento ou usa away-key.pem por padrão

echo "🔧 Iniciando correção de CORS na VM AWS..."
echo "📍 IP da VM: $VM_IP"
echo ""

# Verificar se a chave SSH existe
if [ ! -f "$SSH_KEY" ]; then
    echo "❌ Erro: Chave SSH não encontrada: $SSH_KEY"
    echo "💡 Use: ./corrigir-cors-vm.sh /caminho/para/sua-chave.pem"
    exit 1
fi

# Tornar a chave executável (se necessário)
chmod 400 "$SSH_KEY" 2>/dev/null || true

echo "🔐 Conectando na VM..."
echo ""

# Comando para executar na VM
ssh -i "$SSH_KEY" -o StrictHostKeyChecking=no "$VM_USER@$VM_IP" << 'ENDSSH'
    echo "✅ Conectado na VM"
    echo ""
    
    # Encontrar e parar o backend
    echo "🛑 Parando o backend..."
    BACKEND_PID=$(ps aux | grep "away-0.0.1-SNAPSHOT.jar" | grep -v grep | awk '{print $2}')
    
    if [ -n "$BACKEND_PID" ]; then
        echo "   Processo encontrado: PID $BACKEND_PID"
        kill $BACKEND_PID 2>/dev/null || true
        sleep 2
        
        # Verificar se parou
        if ps -p $BACKEND_PID > /dev/null 2>&1; then
            echo "   ⚠️  Processo ainda rodando, forçando parada..."
            kill -9 $BACKEND_PID 2>/dev/null || true
        fi
        echo "   ✅ Backend parado"
    else
        echo "   ℹ️  Backend não estava rodando"
    fi
    
    # Verificar se existe arquivo backend.pid
    if [ -f backend.pid ]; then
        kill $(cat backend.pid) 2>/dev/null || true
        rm -f backend.pid
    fi
    
    echo ""
    echo "📝 Procurando script de start do backend..."
    
    # Procurar script de start
    START_SCRIPT=""
    if [ -f "/home/ubuntu/start-backend.sh" ]; then
        START_SCRIPT="/home/ubuntu/start-backend.sh"
    elif [ -f "/home/ubuntu/AWAY/start-backend.sh" ]; then
        START_SCRIPT="/home/ubuntu/AWAY/start-backend.sh"
    elif [ -f "start-backend.sh" ]; then
        START_SCRIPT="start-backend.sh"
    fi
    
    if [ -n "$START_SCRIPT" ]; then
        echo "   ✅ Script encontrado: $START_SCRIPT"
        echo "   📝 Atualizando CORS no script..."
        
        # Backup do script original
        cp "$START_SCRIPT" "${START_SCRIPT}.backup.$(date +%Y%m%d_%H%M%S)"
        
        # Atualizar CORS no script
        sed -i 's|export CORS_ALLOWED_ORIGINS=.*|export CORS_ALLOWED_ORIGINS="http://98.83.35.183,http://98.83.35.183:80,http://localhost:4200,http://127.0.0.1:4200"|g' "$START_SCRIPT"
        
        echo "   ✅ Script atualizado"
    else
        echo "   ⚠️  Script de start não encontrado"
        echo "   ℹ️  Você precisará exportar a variável manualmente antes de iniciar"
    fi
    
    echo ""
    echo "🔍 Verificando localização do JAR..."
    
    # Procurar o JAR
    JAR_PATH=""
    if [ -f "/home/ubuntu/AWAY/target/away-0.0.1-SNAPSHOT.jar" ]; then
        JAR_PATH="/home/ubuntu/AWAY/target/away-0.0.1-SNAPSHOT.jar"
        cd /home/ubuntu/AWAY
    elif [ -f "/home/ubuntu/target/away-0.0.1-SNAPSHOT.jar" ]; then
        JAR_PATH="/home/ubuntu/target/away-0.0.1-SNAPSHOT.jar"
        cd /home/ubuntu
    elif [ -f "target/away-0.0.1-SNAPSHOT.jar" ]; then
        JAR_PATH="target/away-0.0.1-SNAPSHOT.jar"
    fi
    
    if [ -z "$JAR_PATH" ]; then
        echo "   ❌ JAR não encontrado!"
        echo "   💡 Você precisará compilar o projeto primeiro:"
        echo "      cd /home/ubuntu/AWAY && mvn clean package -DskipTests"
        exit 1
    fi
    
    echo "   ✅ JAR encontrado: $JAR_PATH"
    echo ""
    echo "🚀 Iniciando backend com nova configuração de CORS..."
    
    # Exportar variáveis de ambiente
    export SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/away_db"
    export SPRING_DATASOURCE_USERNAME="postgres"
    export SPRING_DATASOURCE_PASSWORD="postgres"
    export JWT_SECRET="q0Ofty/GFEy7xkSp2iRJup9QuW7M7E4WoMAsnl4/SJY="
    export JWT_EXPIRATION="86400000"
    export CORS_ALLOWED_ORIGINS="http://98.83.35.183,http://98.83.35.183:80,http://localhost:4200,http://127.0.0.1:4200"
    export SERVER_PORT="8080"
    export JPA_DDL_AUTO="update"
    export JPA_SHOW_SQL="false"
    
    # Iniciar backend em background
    nohup java -Xmx1024m -Xms512m -jar "$JAR_PATH" > backend.log 2>&1 &
    echo $! > backend.pid
    
    echo "   ✅ Backend iniciado (PID: $(cat backend.pid))"
    echo ""
    echo "⏳ Aguardando 5 segundos para o backend inicializar..."
    sleep 5
    
    echo ""
    echo "🔍 Verificando se o backend está respondendo..."
    
    # Testar health check
    if curl -s http://localhost:8080/auth/health > /dev/null; then
        echo "   ✅ Backend está respondendo!"
    else
        echo "   ⚠️  Backend pode ainda estar inicializando..."
        echo "   💡 Verifique os logs: tail -f backend.log"
    fi
    
    echo ""
    echo "📋 Resumo da configuração:"
    echo "   CORS_ALLOWED_ORIGINS: $CORS_ALLOWED_ORIGINS"
    echo "   Backend PID: $(cat backend.pid 2>/dev/null || echo 'N/A')"
    echo "   Logs: backend.log"
    echo ""
    echo "✅ Correção concluída!"
    echo ""
    echo "💡 Para verificar os logs:"
    echo "   tail -f backend.log"
    echo ""
    echo "💡 Para testar no navegador:"
    echo "   http://98.83.35.183/dashboard"
ENDSSH

echo ""
echo "✅ Script executado com sucesso!"
echo ""
echo "🌐 Teste a aplicação em: http://98.83.35.183/dashboard"
echo ""

