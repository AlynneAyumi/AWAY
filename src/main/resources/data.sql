-- Endereco
CREATE TABLE IF NOT EXISTS endereco (
    id_endereco      BIGSERIAL PRIMARY KEY,
    cep              VARCHAR(255),
    bairro           VARCHAR(255),
    cidade           VARCHAR(255),
    estado           VARCHAR(255),
    rua              VARCHAR(255),
    numero           VARCHAR(255),
    complemento      VARCHAR(255),
    created_by       INTEGER,
    creation_date    TIMESTAMP,
    last_updated_by  INTEGER,
    last_update_date TIMESTAMP
);

-- Pessoa
CREATE TABLE IF NOT EXISTS pessoa (
    id_pessoa        BIGSERIAL PRIMARY KEY,
    cpf              VARCHAR(255) UNIQUE,
    nome             VARCHAR(255),
    segundo_nome     VARCHAR(255),
    data_nascimento  TIMESTAMP,
    telefone         VARCHAR(255),
    email            VARCHAR(255),
    id_endereco      BIGINT REFERENCES endereco(id_endereco),
    created_by       INTEGER,
    creation_date    TIMESTAMP,
    last_updated_by  INTEGER,
    last_update_date TIMESTAMP
);

-- Usuario
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario       BIGSERIAL PRIMARY KEY,
    email            VARCHAR(255) UNIQUE NOT NULL,
    nome_user        VARCHAR(255) NOT NULL,
    nome             VARCHAR(255) DEFAULT '',
    senha            VARCHAR(255) NOT NULL,
    role             VARCHAR(255) NOT NULL,
    key_cloak_id     VARCHAR(255) NOT NULL,
    ativo            BOOLEAN DEFAULT TRUE,
    tipo_acesso      INTEGER,
    id_pessoa        BIGINT REFERENCES pessoa(id_pessoa),
    created_by       INTEGER,
    creation_date    TIMESTAMP,
    last_updated_by  INTEGER,
    last_update_date TIMESTAMP
);

-- TipoMonitoramento
CREATE TABLE IF NOT EXISTS tipo_monitoramento (
    id_tipo_monitoramento BIGSERIAL PRIMARY KEY,
    descricao             VARCHAR(255),
    flag_ativo            BOOLEAN,
    created_by            INTEGER,
    creation_date         TIMESTAMP,
    last_updated_by       INTEGER,
    last_update_date      TIMESTAMP
);

-- TipoRegime
CREATE TABLE IF NOT EXISTS tipo_regime (
    id_tipo_regime   BIGSERIAL PRIMARY KEY,
    descricao        VARCHAR(255),
    flag_ativo       BOOLEAN,
    created_by       INTEGER,
    creation_date    TIMESTAMP,
    last_updated_by  INTEGER,
    last_update_date TIMESTAMP
);

-- TipoSituacao
CREATE TABLE IF NOT EXISTS tipo_situacao (
    id_tipo_situacao BIGSERIAL PRIMARY KEY,
    descricao        VARCHAR(255),
    flag_ativo       BOOLEAN,
    created_by       INTEGER,
    creation_date    TIMESTAMP,
    last_updated_by  INTEGER,
    last_update_date TIMESTAMP
);

-- VaraExecPenal
CREATE TABLE IF NOT EXISTS vara_exec_penal (
    id_vara_exec_penal BIGSERIAL PRIMARY KEY,
    nome               VARCHAR(255),
    descricao          VARCHAR(255),
    created_by         INTEGER,
    creation_date      TIMESTAMP,
    last_updated_by    INTEGER,
    last_update_date   TIMESTAMP
);

-- Assistido
CREATE TABLE IF NOT EXISTS assistido (
    id_assistido           BIGSERIAL PRIMARY KEY,
    num_auto               VARCHAR(255) UNIQUE,
    num_processo           VARCHAR(255),
    observacao             VARCHAR(255),
    status_comparecimento  VARCHAR(255),
    ultimo_comparecimento  DATE,
    id_pessoa              BIGINT REFERENCES pessoa(id_pessoa),
    id_tipo_monitoramento  BIGINT REFERENCES tipo_monitoramento(id_tipo_monitoramento),
    id_tipo_regime         BIGINT REFERENCES tipo_regime(id_tipo_regime),
    id_tipo_situacao       BIGINT REFERENCES tipo_situacao(id_tipo_situacao),
    id_vara_exec_penal     BIGINT REFERENCES vara_exec_penal(id_vara_exec_penal),
    created_by             INTEGER,
    creation_date          TIMESTAMP,
    last_updated_by        INTEGER,
    last_update_date       TIMESTAMP
);

-- Comparecimento
CREATE TABLE IF NOT EXISTS comparecimento (
    id_comparecimento   BIGSERIAL PRIMARY KEY,
    data                TIMESTAMP NOT NULL,
    flag_comparecimento BOOLEAN NOT NULL,
    observacoes         VARCHAR(255),
    id_assistido        BIGINT REFERENCES assistido(id_assistido),
    created_by          INTEGER,
    creation_date       TIMESTAMP,
    last_updated_by     INTEGER,
    last_update_date    TIMESTAMP
);

-- Usuarios
INSERT INTO usuario (email, nome_user, senha, role, ativo, created_by, creation_date, tipo_acesso, key_cloak_id)
VALUES ('admin@away.com', 'admin', '$2a$12$qBs2NkLlduDhP0BpGGa5i.83JyktmTRyeji/cAk1CvZ4.pUCXZBvq', 'ADMIN', true, 1, CURRENT_TIMESTAMP, 1, '')
ON CONFLICT (email) DO UPDATE SET role = 'ADMIN';

INSERT INTO usuario (email, nome_user, senha, role, ativo, created_by, creation_date, tipo_acesso, key_cloak_id)
VALUES ('funcionario@away.com', 'funcionario', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'FUNCIONARIO', true, 1, CURRENT_TIMESTAMP, 2, '')
ON CONFLICT (email) DO UPDATE SET role = 'FUNCIONARIO';

UPDATE usuario SET ativo = true WHERE ativo IS NULL;
UPDATE usuario SET role = 'ADMIN' WHERE role IN ('ADMINISTRADOR', 'ADMIN');
UPDATE usuario SET role = 'FUNCIONARIO' WHERE role IS NULL OR role = '' OR role NOT IN ('ADMIN', 'FUNCIONARIO');

-- Enderecos
INSERT INTO endereco (rua, cep, bairro, cidade, estado, numero, created_by, creation_date)
VALUES ('Rua das Flores', '12345-678', 'Centro', 'Sao Paulo', 'SP', '123', 1, CURRENT_TIMESTAMP);

INSERT INTO endereco (rua, cep, bairro, cidade, estado, numero, created_by, creation_date)
VALUES ('Avenida Paulista', '01234-567', 'Bela Vista', 'Sao Paulo', 'SP', '456', 1, CURRENT_TIMESTAMP);

INSERT INTO endereco (rua, cep, bairro, cidade, estado, numero, created_by, creation_date)
VALUES ('Rua da Liberdade', '01510-000', 'Liberdade', 'Sao Paulo', 'SP', '789', 1, CURRENT_TIMESTAMP);

-- Pessoas
INSERT INTO pessoa (nome, segundo_nome, cpf, data_nascimento, telefone, id_endereco, created_by, creation_date)
VALUES ('Joao', 'Silva Santos', '12345678901', '1985-05-15', '11999999999', 1, 1, CURRENT_TIMESTAMP)
ON CONFLICT (cpf) DO NOTHING;

INSERT INTO pessoa (nome, segundo_nome, cpf, data_nascimento, telefone, id_endereco, created_by, creation_date)
VALUES ('Maria', 'Santos Costa', '98765432100', '1990-08-22', '11888888888', 2, 1, CURRENT_TIMESTAMP)
ON CONFLICT (cpf) DO NOTHING;

INSERT INTO pessoa (nome, segundo_nome, cpf, data_nascimento, telefone, id_endereco, created_by, creation_date)
VALUES ('Pedro', 'Costa Lima', '11122233344', '1988-12-10', '11777777777', 3, 1, CURRENT_TIMESTAMP)
ON CONFLICT (cpf) DO NOTHING;

-- Assistidos
INSERT INTO assistido (num_auto, num_processo, observacao, id_pessoa, created_by, creation_date)
VALUES ('AUTO001', 'PROC001', 'Assistido em regime aberto', 1, 1, CURRENT_TIMESTAMP)
ON CONFLICT (num_auto) DO NOTHING;

INSERT INTO assistido (num_auto, num_processo, observacao, id_pessoa, created_by, creation_date)
VALUES ('AUTO002', 'PROC002', 'Assistido em regime semiaberto', 2, 1, CURRENT_TIMESTAMP)
ON CONFLICT (num_auto) DO NOTHING;

INSERT INTO assistido (num_auto, num_processo, observacao, id_pessoa, created_by, creation_date)
VALUES ('AUTO003', 'PROC003', 'Assistido em regime fechado', 3, 1, CURRENT_TIMESTAMP)
ON CONFLICT (num_auto) DO NOTHING;