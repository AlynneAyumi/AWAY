INSERT INTO usuario (email, nome_user, senha, role, ativo, created_by, creation_date, tipo_acesso, key_cloak_id)
VALUES ('admin@away.com', 'admin', '$2a$12$0n.krbs5/zqdzKqKZVSF2utJZeir5oqQVFUO6BzOrZQqadp.YD7gW', 'ADMIN', true, 1, CURRENT_TIMESTAMP, 1, '')
ON CONFLICT (email) DO UPDATE SET role = 'ADMIN';

INSERT INTO usuario (email, nome_user, senha, role, ativo, created_by, creation_date, tipo_acesso, key_cloak_id)
VALUES ('funcionario@away.com', 'funcionario', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'FUNCIONARIO', true, 1, CURRENT_TIMESTAMP, 2, '')
ON CONFLICT (email) DO UPDATE SET role = 'FUNCIONARIO';

UPDATE usuario SET ativo = true WHERE ativo IS NULL;
UPDATE usuario SET role = 'ADMIN' WHERE role IN ('ADMINISTRADOR', 'ADMIN');
UPDATE usuario SET role = 'FUNCIONARIO' WHERE role IS NULL OR role = '' OR role NOT IN ('ADMIN', 'FUNCIONARIO');

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