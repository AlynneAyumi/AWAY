# AWAY - Sistema Patronato Penitenciário (Backend)

Sistema de gestão para patronato penitenciário desenvolvido em Spring Boot.

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Hibernate**

## 📋 Funcionalidades

### Entidades Principais
- **Assistidos** - Gestão de pessoas em regime penitenciário
- **Usuários** - Sistema de autenticação e autorização
- **Comparecimentos** - Controle de presença
- **Documentos** - Gestão de documentação
- **Endereços** - Cadastro de endereços

### APIs Disponíveis
- `GET /assistido/findAll` - Listar todos os assistidos
- `POST /assistido/save` - Criar novo assistido
- `PUT /assistido/update/{id}` - Atualizar assistido
- `DELETE /assistido/delete/{id}` - Excluir assistido
- `GET /usuario/findAll` - Listar usuários
- `POST /auth/login` - Autenticação
- E muito mais...

## 🛠️ Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+
- PostgreSQL 12+

### Configuração do Banco
1. Crie um banco PostgreSQL chamado `away_db`
2. Configure as credenciais no `application.properties`

### Executando
```bash
# Instalar dependências
mvn clean install

# Executar aplicação
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📁 Estrutura do Projeto

```
src/main/java/com/example/away/
├── controller/     # Controllers REST
├── model/         # Entidades JPA
├── repository/    # Repositórios Spring Data
├── service/       # Lógica de negócio
├── dto/          # Data Transfer Objects
├── exception/    # Tratamento de exceções
└── config/       # Configurações
```

## 🔧 Configurações

### application.properties
```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/away_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## 📝 Dados de Teste

O sistema inclui dados de teste que são carregados automaticamente:
- Usuários: admin@away.com / admin123
- Assistidos de exemplo
- Endereços de teste

## 🚨 Tratamento de Exceções

Sistema implementa `GlobalExceptionHandler` com:
- Tratamento centralizado de exceções
- Respostas padronizadas
- Logs estruturados
- Códigos HTTP apropriados

## 📊 Status do Projeto

- ✅ Controllers refatorados
- ✅ GlobalExceptionHandler implementado
- ✅ CRUDs completos
- ✅ Endpoints personalizados
- ✅ Validações JPA
- ✅ Relacionamentos mapeados
- ✅ Tratamento de exceções centralizado

## 👥 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.