-- Script de inicialização com dados de teste
-- Este script será executado automaticamente pelo Spring Boot

-- Inserir usuário de teste para login
INSERT INTO usuario (email, nome_user, senha, created_by, creation_date, tipo_acesso) 
VALUES ('admin@away.com', 'admin', 'admin123', 1, CURRENT_TIMESTAMP, 1)
ON CONFLICT (email) DO NOTHING;

INSERT INTO usuario (email, nome_user, senha, created_by, creation_date, tipo_acesso) 
VALUES ('usuario@away.com', 'usuario', '123456', 1, CURRENT_TIMESTAMP, 2)
ON CONFLICT (email) DO NOTHING;
