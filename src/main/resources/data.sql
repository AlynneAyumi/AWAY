-- Script de inicialização com dados de teste
-- Este script será executado automaticamente pelo Spring Boot
-- IMPORTANTE: As senhas devem ser BCrypt. Use o PasswordEncoder para gerar hashes válidos.
-- Exemplo: BCrypt de "admin123" = $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy

-- Inserir usuário de teste para login
-- Senha "admin123" criptografada com BCrypt
INSERT INTO usuario (email, nome_user, senha, role, ativo, created_by, creation_date, tipo_acesso) 
VALUES ('admin@away.com', 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGENTE', true, 1, CURRENT_TIMESTAMP, 1)
ON CONFLICT (email) DO NOTHING;

-- Senha "123456" criptografada com BCrypt
INSERT INTO usuario (email, nome_user, senha, role, ativo, created_by, creation_date, tipo_acesso) 
VALUES ('usuario@away.com', 'usuario', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.H/H9n0/7iJ8J3J8J3J8J3J8', 'AGENTE', true, 1, CURRENT_TIMESTAMP, 2)
ON CONFLICT (email) DO NOTHING;

-- Atualizar usuários existentes que podem ter o campo ativo como NULL
UPDATE usuario SET ativo = true WHERE ativo IS NULL;
UPDATE usuario SET role = 'AGENTE' WHERE role IS NULL OR role = '';

-- Inserir dados de teste para assistidos
INSERT INTO endereco (rua, cep, bairro, cidade, estado, numero, created_by, creation_date) 
VALUES ('Rua das Flores', '12345-678', 'Centro', 'São Paulo', 'SP', 123, 1, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

INSERT INTO endereco (rua, cep, bairro, cidade, estado, numero, created_by, creation_date) 
VALUES ('Avenida Paulista', '01234-567', 'Bela Vista', 'São Paulo', 'SP', 456, 1, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

INSERT INTO endereco (rua, cep, bairro, cidade, estado, numero, created_by, creation_date) 
VALUES ('Rua da Liberdade', '01510-000', 'Liberdade', 'São Paulo', 'SP', 789, 1, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

INSERT INTO pessoa (nome, segundo_nome, cpf, data_nascimento, telefone, id_endereco, created_by, creation_date) 
VALUES ('João', 'Silva Santos', '12345678901', '1985-05-15', '11999999999', 1, 1, CURRENT_TIMESTAMP)
ON CONFLICT (cpf) DO NOTHING;

INSERT INTO pessoa (nome, segundo_nome, cpf, data_nascimento, telefone, id_endereco, created_by, creation_date) 
VALUES ('Maria', 'Santos Costa', '98765432100', '1990-08-22', '11888888888', 2, 1, CURRENT_TIMESTAMP)
ON CONFLICT (cpf) DO NOTHING;

INSERT INTO pessoa (nome, segundo_nome, cpf, data_nascimento, telefone, id_endereco, created_by, creation_date) 
VALUES ('Pedro', 'Costa Lima', '11122233344', '1988-12-10', '11777777777', 3, 1, CURRENT_TIMESTAMP)
ON CONFLICT (cpf) DO NOTHING;

INSERT INTO assistido (num_auto, num_processo, observacao, id_pessoa, created_by, creation_date) 
VALUES ('AUTO001', 'PROC001', 'Assistido em regime aberto', 1, 1, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

INSERT INTO assistido (num_auto, num_processo, observacao, id_pessoa, created_by, creation_date) 
VALUES ('AUTO002', 'PROC002', 'Assistido em regime semiaberto', 2, 1, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

INSERT INTO assistido (num_auto, num_processo, observacao, id_pessoa, created_by, creation_date) 
VALUES ('AUTO003', 'PROC003', 'Assistido em regime fechado', 3, 1, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;
