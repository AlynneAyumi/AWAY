# Makefile para facilitar operações comuns

.PHONY: help build run test clean docker-build docker-run docker-compose-up docker-compose-down

help:
	@echo "Comandos disponíveis:"
	@echo "  make build          - Compila o projeto"
	@echo "  make run            - Executa a aplicação"
	@echo "  make test           - Executa testes"
	@echo "  make clean          - Limpa arquivos compilados"
	@echo "  make docker-build   - Constrói imagem Docker"
	@echo "  make docker-run     - Executa container Docker"
	@echo "  make docker-compose-up   - Inicia todos os serviços"
	@echo "  make docker-compose-down - Para todos os serviços"

build:
	mvn clean package -DskipTests

run:
	mvn spring-boot:run

test:
	mvn test

clean:
	mvn clean

docker-build:
	docker build -t away-backend:latest .

docker-run:
	docker run -p 8080:8080 --env-file .env away-backend:latest

docker-compose-up:
	cd .. && docker-compose up -d

docker-compose-down:
	cd .. && docker-compose down

