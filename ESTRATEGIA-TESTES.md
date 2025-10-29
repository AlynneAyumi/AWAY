# Estratégia de Testes - Sistema AWAY

## Visão Geral

O projeto AWAY adota uma estratégia de testes focada em garantir a qualidade e confiabilidade do código através de múltiplas camadas de validação. A estratégia combina testes unitários e de integração para cobrir desde a lógica de negócio até a interação entre componentes.

**Objetivos**:
- Validar a lógica de negócio nos serviços
- Garantir o funcionamento correto das APIs REST
- Prevenir regressões durante o desenvolvimento
- Manter cobertura mínima de 80%

---

## Ferramentas

**Frameworks Principais**:
- **JUnit 5**: Framework de testes
- **Mockito**: Criação de mocks (mockito-core e mockito-inline)
- **JaCoCo**: Análise de cobertura de código (versão 0.8.11)
- **H2 Database**: Banco de dados em memória para testes
- **Spring Boot Test**: Infraestrutura de teste do Spring Boot

---

## Arquitetura de Testes

O projeto segue a **Pirâmide de Testes**, priorizando testes unitários:

```
┌─────────────────────┐
│ Testes de           │  ← Camada fina
│ Integração          │  (Controllers)
├─────────────────────┤
│ Testes Unitários    │  ← Camada espessa
│ (Services)          │
└─────────────────────┘
```

**Camadas**:
1. **Service Layer**: Testes unitários isolados com mocks
2. **Controller Layer**: Testes de integração validando fluxos end-to-end

---

## Tipos de Testes

### 1. Testes Unitários
**Localização**: `src/test/java/com/example/away/service/`

Testam componentes isolados (Services) com dependências mockadas.

**Classes existentes**:
- `AssistidoServiceTest.java`
- `PessoaServiceTest.java`
- `EnderecoServiceTest.java` 
- `UsuarioServiceTest.java` 
- `TipoMonitoramentoServiceTest` 
- `TipoRegimeServiceTest`
- `TipoSituacaoServiceTest`



**Estrutura básica**:
```java
@ExtendWith(MockitoExtension.class)
class ServiceTest {
    @Mock
    private Repository repository;
    
    @InjectMocks
    private Service service;
}
```

### 2. Testes de Integração
**Localização**: `src/test/java/com/example/away/integration/`

Testam a interação entre Controller e Services, validando respostas HTTP.

**Classe existente**:
- `AssistidoIntegrationTest.java`
- `AuthControllerIntegrationTest.java`
- `ComparecimentoIntegrationTest.java`
- `EnderecoIntegrationTest.java`
- `MonitoramentoIntegrationTest.java`
- `PessoalIntegrationTest.java`
- `TestControllerIntegrationTest.java`
- `TipoRegimeIntegrationTest.java`
- `TipoSituacaoIntegrationTest.java`
- `UsuarioIntegrationTest.java`
- `VaraExecPenalIntegrationTest.java`

### 3. Teste de Contexto
**Localização**: `src/test/java/com/example/away/AwayApplicationTests.java`

Valida se o contexto Spring Boot carrega corretamente.

---

## Padrões e Convenções

### Padrão AAA (Arrange-Act-Assert)

```java
@Test
@DisplayName("TESTE UNITÁRIO > Deve retornar assistido quando ID existir")
void findById_QuandoEncontrado() {
    // Arrange (Configuração)
    when(repository.findById(1L)).thenReturn(Optional.of(assistido));
    
    // Act (Ação)
    Assistido resultado = service.findById(1L);
    
    // Assert (Verificação)
    assertNotNull(resultado);
    verify(repository).findById(1L);
}
```

### Nomenclatura

- **DisplayName**: `"TESTE UNITÁRIO > [Descrição]"` ou `"TESTE DE INTEGRAÇÃO - [Descrição]"`
- **Métodos**: `[método]_[condição]` (ex: `findById_QuandoEncontrado()`, `save_ComSucesso()`)

### Verificações Mockito

- `verify()`: Verifica chamadas de métodos
- `verify(..., times(n))`: Verifica número de chamadas
- `verify(..., never())`: Garante que método não foi chamado
- `ArgumentCaptor`: Captura argumentos para validação

---

## Cobertura de Código

**JaCoCo Configurado**:
- **Meta**: 80% de cobertura de linhas
- **Exclusões**: `AwayApplication.java`, `config/**`, `model/*Dto.class`

**Comandos**:
```bash
mvn clean test jacoco:report        # Executa testes e gera relatório
open target/site/jacoco/index.html  # Visualiza relatório
```

---

## Estrutura de Testes

```
src/test/java/com/example/away/
├── AwayApplicationTests.java          # Teste de contexto
├── integration/
│   └── AssistidoIntegrationTest.java  # Testes de integração
├── service/
│   ├── AssistidoServiceTest.java      # Testes unitários
│   ├── EnderecoServiceTest.java
│   └── UsuarioServiceTest.java
└── Resources/
    └── application-test.properties     # H2 Database config
```

**Configuração de Teste** (H2 em memória):
```properties
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

---

## Status dos Testes

### Testes Unitários
- **EnderecoServiceTest**: Completo (12 cenários)
- **UsuarioServiceTest**: Completo (11 cenários)
- **AssistidoServiceTest**: Parcial (3 cenários) 

### Testes de Integração
- **AssistidoIntegrationTest**: Completo (14 cenários)

---

## Executando os Testes

```bash
mvn test                                    # Todos os testes
mvn test -Dtest=AssistidoServiceTest       # Teste específico
mvn test -Dtest=*IntegrationTest           # Apenas integração
mvn test -Dtest=*ServiceTest               # Apenas unitários
mvn clean test jacoco:report               # Com relatório de cobertura
```

---


