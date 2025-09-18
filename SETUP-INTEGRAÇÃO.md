# 🚀 Setup Completo - Backend + Frontend AWAY

## 📋 **Pré-requisitos**

### Backend:
- ☑️ **Java 17** ou superior
- ☑️ **Maven 3.6+**
- ☑️ **PostgreSQL** instalado e rodando

### Frontend:
- ☑️ **Node.js 18+**
- ☑️ **Angular CLI**

## 🗄️ **Configuração do Banco de Dados**

### 1. Criar Banco PostgreSQL
```sql
-- Conecte no PostgreSQL como superuser
CREATE DATABASE "AWAY";
CREATE USER away_user WITH PASSWORD 'away123';
GRANT ALL PRIVILEGES ON DATABASE "AWAY" TO away_user;
```

### 2. Configurar Credenciais
Edite: `src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/AWAY
spring.datasource.username=away_user
spring.datasource.password=away123
```

## 🔧 **Executar Backend**

### 1. Navegar para pasta do backend
```bash
cd /Users/analuisacantu/Desktop/frontend-away/backend-away
```

### 2. Instalar dependências e executar
```bash
# Limpar e instalar
mvn clean install

# Executar aplicação
mvn spring-boot:run
```

### 3. Verificar se está funcionando
- Backend rodando em: `http://localhost:8080`
- Teste: `curl http://localhost:8080/auth/health`
- Resposta esperada: `"Backend AWAY está funcionando!"`

## 🌐 **Executar Frontend**

### 1. Navegar para pasta do frontend
```bash
cd /Users/analuisacantu/Desktop/frontend-away/AppAWAY-3
```

### 2. Instalar dependências
```bash
npm install
```

### 3. Executar com proxy (recomendado)
```bash
npm run start:proxy
```

### 4. Acessar aplicação
- Frontend: `http://localhost:4200`

## 🔐 **Testar Login**

### Usuários de Teste Criados:
1. **Admin:**
   - Email: `admin@away.com`
   - Senha: `admin123`

2. **Usuário:**
   - Email: `usuario@away.com`
   - Senha: `123456`

## 📡 **Endpoints Disponíveis**

### Autenticação
- `POST /auth/login` - Fazer login
- `POST /auth/logout` - Fazer logout
- `GET /auth/health` - Verificar saúde da API

### Assistidos
- `GET /assistido/findAll` - Listar todos
- `GET /assistido/findById/{id}` - Buscar por ID
- `GET /assistido/numProcesso?numProcesso={num}` - Buscar por processo
- `POST /assistido/save` - Criar novo
- `PUT /assistido/update/{id}` - Atualizar
- `DELETE /assistido/delete/{id}` - Excluir

### Comparecimentos
- `GET /comparecimento/findAll` - Listar todos
- `GET /comparecimento/findById/{id}` - Buscar por ID
- `POST /comparecimento/save` - Criar novo
- `PUT /comparecimento/update/{id}` - Atualizar
- `DELETE /comparecimento/delete/{id}` - Excluir

### Usuários
- `GET /usuario/findAll` - Listar todos
- `GET /usuario/findById/{id}` - Buscar por ID
- `POST /usuario/save` - Criar novo
- `PUT /usuario/update/{id}` - Atualizar
- `DELETE /usuario/delete/{id}` - Excluir

## 🔧 **Melhorias Implementadas**

### Backend:
✅ **AuthController** - Autenticação completa
✅ **CORS configurado** - Permite requisições do frontend
✅ **DTOs de Login** - LoginRequest e LoginResponse
✅ **Método findByEmail** - Para autenticação
✅ **Endpoints corrigidos** - URLs padronizadas
✅ **Dados de teste** - Usuários para login

### Frontend:
✅ **Environment config** - URLs centralizadas
✅ **Interceptor atualizado** - Bearer token automático
✅ **Serviços atualizados** - Usando environment
✅ **Proxy configurado** - Evita problemas CORS
✅ **Mock removido** - Usando backend real

## 🚨 **Solução de Problemas**

### Backend não inicia
1. Verificar se PostgreSQL está rodando
2. Verificar credenciais do banco
3. Verificar se Java 17 está instalado

### Frontend não conecta
1. Verificar se backend está rodando na porta 8080
2. Usar `npm run start:proxy` em vez de `npm start`
3. Verificar console do browser para erros

### Erro 401 no login
1. Verificar se usuários de teste foram criados no banco
2. Usar credenciais: `admin@away.com` / `admin123`

### CORS Error
1. Verificar se CorsConfig foi aplicado
2. Reiniciar backend após mudanças
3. Usar proxy no frontend

## 🎯 **Próximos Passos**

### Segurança:
- [ ] Implementar JWT real
- [ ] Hash de senhas com BCrypt
- [ ] Validação de tokens

### Funcionalidades:
- [ ] Filtros avançados
- [ ] Paginação
- [ ] Upload de documentos
- [ ] Relatórios

---

**🎉 Integração Completa Realizada!**
*Frontend Angular + Backend Spring Boot funcionando juntos*
