#!/bin/bash

# Script para corrigir CORS no backend AWS usando autenticação por senha
# Usa expect para automação de SSH

set -e

VM_IP="98.83.35.183"
VM_USER="ubuntu"
VM_PASSWORD="Kuriyama!24"

echo "🔧 Iniciando correção de CORS na VM AWS..."
echo "📍 IP da VM: $VM_IP"
echo ""

# Criar script expect temporário
EXPECT_SCRIPT=$(mktemp)
cat > "$EXPECT_SCRIPT" << 'EXPECTEOF'
#!/usr/bin/expect -f

set timeout 30
set VM_IP [lindex $argv 0]
set VM_USER [lindex $argv 1]
set VM_PASSWORD [lindex $argv 2]

spawn ssh -o StrictHostKeyChecking=no $VM_USER@$VM_IP

expect {
    "password:" {
        send "$VM_PASSWORD\r"
        exp_continue
    }
    "Permission denied" {
        puts "❌ Erro: Autenticação negada. A VM pode estar configurada apenas para chave SSH."
        exit 1
    }
    "$ " {
        # Conectado com sucesso
    }
    "# " {
        # Conectado como root
    }
    timeout {
        puts "❌ Erro: Timeout ao conectar"
        exit 1
    }
}

# Aguardar prompt
expect {
    "$ " {}
    "# " {}
}

puts "✅ Conectado na VM"
puts ""

# Encontrar e parar o backend
send "echo '🛑 Parando o backend...'\r"
expect "$ "

send "BACKEND_PID=\$(ps aux | grep 'away-0.0.1-SNAPSHOT.jar' | grep -v grep | awk '{print \$2}')\r"
expect "$ "

send "if [ -n \"\$BACKEND_PID\" ]; then kill \$BACKEND_PID 2>/dev/null || true; sleep 2; if ps -p \$BACKEND_PID > /dev/null 2>&1; then kill -9 \$BACKEND_PID 2>/dev/null || true; fi; echo '✅ Backend parado'; else echo 'ℹ️  Backend não estava rodando'; fi\r"
expect "$ "

send "if [ -f backend.pid ]; then kill \$(cat backend.pid) 2>/dev/null || true; rm -f backend.pid; fi\r"
expect "$ "

puts ""
puts "📝 Procurando script de start do backend..."

send "START_SCRIPT=\"\"; if [ -f \"/home/ubuntu/start-backend.sh\" ]; then START_SCRIPT=\"/home/ubuntu/start-backend.sh\"; elif [ -f \"/home/ubuntu/AWAY/start-backend.sh\" ]; then START_SCRIPT=\"/home/ubuntu/AWAY/start-backend.sh\"; elif [ -f \"start-backend.sh\" ]; then START_SCRIPT=\"start-backend.sh\"; fi\r"
expect "$ "

send "if [ -n \"\$START_SCRIPT\" ]; then echo \"✅ Script encontrado: \$START_SCRIPT\"; cp \"\$START_SCRIPT\" \"\${START_SCRIPT}.backup.\$(date +%Y%m%d_%H%M%S)\"; sed -i 's|export CORS_ALLOWED_ORIGINS=.*|export CORS_ALLOWED_ORIGINS=\"http://98.83.35.183,http://98.83.35.183:80,http://localhost:4200,http://127.0.0.1:4200\"|g' \"\$START_SCRIPT\"; echo \"✅ Script atualizado\"; else echo \"⚠️  Script de start não encontrado\"; fi\r"
expect "$ "

puts ""
puts "🔍 Verificando localização do JAR..."

send "JAR_PATH=\"\"; if [ -f \"/home/ubuntu/AWAY/target/away-0.0.1-SNAPSHOT.jar\" ]; then JAR_PATH=\"/home/ubuntu/AWAY/target/away-0.0.1-SNAPSHOT.jar\"; cd /home/ubuntu/AWAY; elif [ -f \"/home/ubuntu/target/away-0.0.1-SNAPSHOT.jar\" ]; then JAR_PATH=\"/home/ubuntu/target/away-0.0.1-SNAPSHOT.jar\"; cd /home/ubuntu; elif [ -f \"target/away-0.0.1-SNAPSHOT.jar\" ]; then JAR_PATH=\"target/away-0.0.1-SNAPSHOT.jar\"; fi\r"
expect "$ "

send "if [ -z \"\$JAR_PATH\" ]; then echo '❌ JAR não encontrado!'; exit 1; else echo \"✅ JAR encontrado: \$JAR_PATH\"; fi\r"
expect "$ "

puts ""
puts "🚀 Iniciando backend com nova configuração de CORS..."

send "export SPRING_DATASOURCE_URL=\"jdbc:postgresql://localhost:5432/away_db\"\r"
expect "$ "

send "export SPRING_DATASOURCE_USERNAME=\"postgres\"\r"
expect "$ "

send "export SPRING_DATASOURCE_PASSWORD=\"postgres\"\r"
expect "$ "

send "export JWT_SECRET=\"q0Ofty/GFEy7xkSp2iRJup9QuW7M7E4WoMAsnl4/SJY=\"\r"
expect "$ "

send "export JWT_EXPIRATION=\"86400000\"\r"
expect "$ "

send "export CORS_ALLOWED_ORIGINS=\"http://98.83.35.183,http://98.83.35.183:80,http://localhost:4200,http://127.0.0.1:4200\"\r"
expect "$ "

send "export SERVER_PORT=\"8080\"\r"
expect "$ "

send "export JPA_DDL_AUTO=\"update\"\r"
expect "$ "

send "export JPA_SHOW_SQL=\"false\"\r"
expect "$ "

send "nohup java -Xmx1024m -Xms512m -jar \"\$JAR_PATH\" > backend.log 2>&1 &\r"
expect "$ "

send "echo \$! > backend.pid\r"
expect "$ "

send "echo \"✅ Backend iniciado (PID: \$(cat backend.pid))\"\r"
expect "$ "

puts ""
puts "⏳ Aguardando 5 segundos para o backend inicializar..."

send "sleep 5\r"
expect "$ "

puts ""
puts "🔍 Verificando se o backend está respondendo..."

send "if curl -s http://localhost:8080/auth/health > /dev/null; then echo '✅ Backend está respondendo!'; else echo '⚠️  Backend pode ainda estar inicializando...'; fi\r"
expect "$ "

puts ""
puts "📋 Resumo da configuração:"
send "echo \"   CORS_ALLOWED_ORIGINS: \$CORS_ALLOWED_ORIGINS\"\r"
expect "$ "

send "echo \"   Backend PID: \$(cat backend.pid 2>/dev/null || echo 'N/A')\"\r"
expect "$ "

puts ""
puts "✅ Correção concluída!"
puts ""
puts "💡 Para verificar os logs: tail -f backend.log"
puts ""
puts "💡 Para testar no navegador: http://98.83.35.183/dashboard"

send "exit\r"
expect eof
EXPECTEOF

chmod +x "$EXPECT_SCRIPT"

# Executar script expect
echo "🔐 Tentando conectar na VM..."
/usr/bin/expect -f "$EXPECT_SCRIPT" "$VM_IP" "$VM_USER" "$VM_PASSWORD"

# Limpar script temporário
rm -f "$EXPECT_SCRIPT"

echo ""
echo "✅ Script executado!"
echo ""
echo "🌐 Teste a aplicação em: http://98.83.35.183/dashboard"
echo ""

