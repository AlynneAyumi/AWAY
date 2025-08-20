# AWAY
Projeto Integrador de Extensão do Curso de Análise e Desenvolvimento de Sistemas &
amp; Engenharia de Software. 

## Sobre o Projeto

O AWAY é um sistema de acompanhamento de pessoas em regime de liberdade assistida. 
## Tecnologias Utilizadas

- **Backend**: Java com Spring Boot
- **Banco de Dados**: PostgreSQL
- **Build Tool**: Maven
- **IDE Recomendada**: IntelliJ IDEA ou Eclipse

## Estrutura do Projeto

```
src/main/java/com/example/away/
├── controller/     # Controladores REST
├── model/         # Entidades do banco de dados
├── repository/    # Interfaces de acesso a dados
└── service/       # Lógica de negócio
```

## Como Executar

### Pré-requisitos
- Java 11 ou superior
- Maven 3.6+
- PostgreSQL instalado e rodando na porta 5432

### Passos para execução
1. Clone o repositório
2. Crie um banco de dados PostgreSQL chamado `AWAY`
3. Configure as credenciais do banco no arquivo `src/main/resources/application.properties`
4. Execute: `mvn spring-boot:run`
5. Acesse: `http://localhost:8080`

## Funcionalidades Principais

- Controle de comparecimentos
- Cadastro de endereços
- Tipos de monitoramento e regime
- Controle de usuários


## Licença

Este projeto está sob a licença Apache 2.0. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.


---
*Desenvolvido por alunos do curso de Análise e Desenvolvimento de Sistemas & Engenharia de Software da Uniamerica*
