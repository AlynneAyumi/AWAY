#!/bin/bash

# ==============================================================================
# SCRIPT DE DEPLOY AUTOMATIZADO PARA APLICAÇÃO AWAY (Java + PostgreSQL + Apache2)
# ==============================================================================

# --- VARIÁVEIS DE CONFIGURAÇÃO ---
GITHUB_REPO="https://github.com/AlynneAyumi/AWAY.git"
PROJECT_NAME="AWAY" # Nome do diretório criado pelo clone
DEPLOY_PATH="/home/ubuntu/$PROJECT_NAME"

# Arquivos e caminhos dentro do projeto clonado
JAR_FILE="backend/away-0.0.1-SNAPSHOT.jar" # AJUSTE ESTE CAMINHO CONFORME A ESTRUTURA DO SEU REPOSITÓRIO
FRONTEND_SOURCE_DIR="frontend/browser/." # AJUSTE ESTE CAMINHO
APACHE_CONF_SOURCE="config/000-default.conf" # ASSUMINDO QUE O ARQUIVO ESTÁ EM UMA PASTA 'config' NO REPOSITÓRIO

# Configurações do ambiente
FRONTEND_DEST_DIR="/var/www/html/"
APACHE_CONF_DEST="/etc/apache2/sites-available/000-default.conf"
POSTGRES_PASS="secret"
DB_USER="postgres"
DB_NAME="away" # OU O NOME DO SEU BANCO DE DADOS


echo "Iniciando o deploy da aplicação AWAY"

# --- 1. CONFIGURAÇÃO INICIAL E DOWNLOAD DE CÓDIGO ---
echo "Atualizando pacotes e instalando dependências"
sudo apt update -y
sudo apt upgrade -y
sudo apt install git openjdk-17-jre postgresql postgresql-contrib apache2 -y

# Clonagem do Repositório
echo "Clonando o repositório do GitHub: $GITHUB_REPO"
sudo rm -rf $DEPLOY_PATH # Remove a pasta anterior se existir
git clone $GITHUB_REPO $DEPLOY_PATH
cd $DEPLOY_PATH

# --- 2. CONFIGURAÇÃO DO POSTGRESQL ---

echo "Configurando a senha do usuário PostgreSQL e inserindo o registro inicial Admin"

# Altera a senha do usuário 'postgres'
sudo -u postgres psql << EOF
ALTER USER $DB_USER WITH PASSWORD '$POSTGRES_PASS';
EOF

# Insere o usuário ADMIN inicial
sudo -u postgres psql -d $DB_NAME << EOF
INSERT INTO usuario(
	id_usuario, email, nome_user, senha, ativo, nome, perfil, id_pessoa)
	VALUES (1, 'admin@patronato.com', 'ADMIN', '\$2a\$10\$7FA68yLzqFdjI0kn9jjmsOk.4WqReyfYSD9Oj7PeiMY1sGAyz0.F2', true, 'Away Admin', 'ADMIN', null);