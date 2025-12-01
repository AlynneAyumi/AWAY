# Instruções para Configurar Backend na VM AWS

## Pré-requisitos

1. **Java 17+** instalado
2. **Maven 3.6+** instalado
3. **PostgreSQL** instalado e rodando na mesma VM
4. **Banco de dados `away_db`** criado

## Configuração do PostgreSQL

### 1. Verificar se PostgreSQL está rodando
```bash
sudo systemctl status postgresql
```

### 2. Se não estiver rodando, iniciar:
```bash
sudo systemctl start postgresql
sudo systemctl enable postgresql  # Para iniciar automaticamente
```

### 3. Criar o banco de dados:
```bash
sudo -u postgres psql
```

No prompt do PostgreSQL:
```sql
CREATE DATABASE away_db;
CREATE USER postgres WITH PASSWORD 'postgres';
GRANT ALL PRIVILEGES ON DATABASE away_db TO postgres;
\q
```

### 4. Configurar acesso (se necessário):
Editar `/etc/postgresql/*/main/pg_hba.conf` para permitir conexões locais:
```
local   all             all                                     peer
host    all             all             127.0.0.1/32            md5
host    all             all             ::1/128                 md5
```

Reiniciar PostgreSQL:
```bash
sudo systemctl restart postgresql
```

## Configuração do Backend

### 1. Navegar para o diretório do backend:
```bash
cd /caminho/para/AWAY
```

### 2. Configurar variáveis de ambiente (opcional - o script já faz isso):
Crie um arquivo `.env` na raiz do projeto AWAY com:
```env
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/away_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
JWT_SECRET=q0Ofty/GFEy7xkSp2iRJup9QuW7M7E4WoMAsnl4/SJY=
JWT_EXPIRATION=86400000
CORS_ALLOWED_ORIGINS=http://localhost:4200,http://127.0.0.1:4200
SERVER_PORT=8080
JPA_DDL_AUTO=update
JPA_SHOW_SQL=false
```

**OU** exporte as variáveis diretamente no shell antes de iniciar.

### 3. Compilar o projeto:
```bash
mvn clean package -DskipTests
```

### 4. Iniciar o backend:

**Opção A - Usando o script:**
```bash
./start-backend-vm.sh
```

**Opção B - Manualmente:**
```bash
# Exportar variáveis de ambiente
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/away_db
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=postgres
export JWT_SECRET=q0Ofty/GFEy7xkSp2iRJup9QuW7M7E4WoMAsnl4/SJY=
export JWT_EXPIRATION=86400000
export CORS_ALLOWED_ORIGINS=http://localhost:4200,http://127.0.0.1:4200
export SERVER_PORT=8080
export JPA_DDL_AUTO=update

# Iniciar
java -jar target/away-0.0.1-SNAPSHOT.jar
```

**Opção C - Em background:**
```bash
nohup java -jar target/away-0.0.1-SNAPSHOT.jar > backend.log 2>&1 &
echo $! > backend.pid
```

## Verificar se está funcionando

### 1. Verificar logs:
```bash
tail -f backend.log
```

### 2. Testar health check:
```bash
curl http://localhost:8080/actuator/health
```

### 3. Verificar se a porta está aberta:
```bash
netstat -tlnp | grep 8080
# ou
ss -tlnp | grep 8080
```

## Parar o backend

**Usando o script:**
```bash
./stop-backend.sh
```

**Manual:**
```bash
# Se usou o script e tem o arquivo backend.pid
kill $(cat backend.pid)

# Ou encontrar o processo
ps aux | grep away-0.0.1-SNAPSHOT.jar
kill <PID>
```

## Configuração do Firewall (se necessário)

Se o backend não estiver acessível externamente, abra a porta 8080:
```bash
# Ubuntu/Debian
sudo ufw allow 8080/tcp

# CentOS/RHEL
sudo firewall-cmd --permanent --add-port=8080/tcp
sudo firewall-cmd --reload
```

## Troubleshooting

### Backend não inicia
1. Verificar logs: `tail -f backend.log`
2. Verificar se a porta 8080 está livre: `netstat -tlnp | grep 8080`
3. Verificar se Java está instalado: `java -version`

### Erro de conexão com banco
1. Verificar se PostgreSQL está rodando: `sudo systemctl status postgresql`
2. Testar conexão: `psql -h localhost -U postgres -d away_db`
3. Verificar credenciais no `application.properties` ou variáveis de ambiente

### CORS errors no frontend
1. Verificar se `CORS_ALLOWED_ORIGINS` inclui o endereço do frontend
2. Verificar configuração em `CorsConfig.java`

## Integração com Frontend

O backend deve estar acessível na porta 8080. O frontend deve estar configurado para fazer requisições para:
- `http://localhost:8080` (se frontend na mesma VM)
- `http://<IP-DA-VM>:8080` (se frontend em outra máquina)

Verifique o arquivo `proxy.conf.json` no frontend para garantir que está apontando para o backend correto.

