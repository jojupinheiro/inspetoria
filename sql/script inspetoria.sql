CREATE SCHEMA IF NOT EXISTS inspetoria default character set utf8;
USE inspetoria;

CREATE TABLE IF NOT EXISTS municipio (
    pk_id_municipio INT NOT NULL AUTO_INCREMENT,
    nome_municipio VARCHAR(150) NOT NULL,
    cod_ibge_municipio VARCHAR(12),
    PRIMARY KEY (pk_id_municipio)
);

CREATE TABLE IF NOT EXISTS municipioPadrao (
    pk_id_municipio INT NOT NULL AUTO_INCREMENT,
    fk_id_municipio INT NOT NULL,
	PRIMARY KEY (pk_id_municipio),
    FOREIGN KEY (fk_id_municipio)
        REFERENCES municipio (pk_id_municipio)
);

CREATE TABLE IF NOT EXISTS endereco (
    pk_id_endereco INT NOT NULL AUTO_INCREMENT,
    tipo_logradouro_endereco VARCHAR(100),
    logradouro_endereco VARCHAR(150),
    numero_endereco VARCHAR(150),
    PRIMARY KEY (pk_id_endereco)
);

CREATE TABLE IF NOT EXISTS contato (
    pk_id_contato INT NOT NULL AUTO_INCREMENT,
    telefone1_contato VARCHAR(11),
    telefone2_contato VARCHAR(11),
    email_contato VARCHAR(40),
    PRIMARY KEY (pk_id_contato)
);

CREATE TABLE IF NOT EXISTS veterinario (
    pk_id_veterinario INT NOT NULL AUTO_INCREMENT,
    nome_veterinario VARCHAR(120) NOT NULL,
    if_veterinario VARCHAR(11) NOT NULL,
    crmv_veterinario VARCHAR(6),
    PRIMARY KEY (pk_id_veterinario)
);

CREATE TABLE IF NOT EXISTS redator (
    pk_id_redator INT NOT NULL AUTO_INCREMENT,
    nome_redator VARCHAR(40),
    PRIMARY KEY (pk_id_redator)
);

CREATE TABLE IF NOT EXISTS propriedade (
    pk_id_propriedade INT NOT NULL AUTO_INCREMENT,
    fk_id_endereco_propriedade INT NOT NULL,
    fk_id_contato_propriedade INT,
    cod_propriedade VARCHAR(15),
    PRIMARY KEY (pk_id_propriedade),
    FOREIGN KEY (fk_id_endereco_propriedade)
        REFERENCES endereco (pk_id_endereco),
	FOREIGN KEY (fk_id_contato_propriedade)
        REFERENCES contato (pk_id_contato)
);

CREATE TABLE IF NOT EXISTS produtor (
    pk_id_produtor INT NOT NULL AUTO_INCREMENT,
    fk_id_endereco_produtor INT,
    fk_id_municipio INT NOT NULL,
    fk_id_contato_produtor INT,
    nome_produtor VARCHAR(150) NOT NULL,
    cpf_cnpj_produtor VARCHAR(15) NOT NULL,
    rg_produtor VARCHAR(14),
    tipo_pessoa_produtor BOOLEAN NOT NULL DEFAULT 0,      -- 0 para pessoa física e 1 para pessoa jurídica
    observacao_produtor VARCHAR(250),
    PRIMARY KEY (pk_id_produtor),
    FOREIGN KEY (fk_id_municipio)
		REFERENCES municipio (pk_id_municipio),
    FOREIGN KEY (fk_id_endereco_produtor)
        REFERENCES endereco (pk_id_endereco),
    FOREIGN KEY (fk_id_contato_produtor)
        REFERENCES contato (pk_id_contato)
);

CREATE TABLE IF NOT EXISTS produtor_propriedade (
    pk_id_produtor_propriedade INT NOT NULL AUTO_INCREMENT,
    fk_id_produtor INT NOT NULL,
    fk_id_propriedade INT NOT NULL,
    ie_produtor_propriedade VARCHAR(15) UNIQUE,
    PRIMARY KEY (pk_id_produtor_propriedade),
    FOREIGN KEY (fk_id_produtor)
        REFERENCES produtor (pk_id_produtor),
    FOREIGN KEY (fk_id_propriedade)
        REFERENCES propriedade (pk_id_propriedade)
);

CREATE TABLE IF NOT EXISTS motivo_ai (
    pk_id_motivo_ai INT NOT NULL AUTO_INCREMENT,
    preve_adv_motivo_ai BOOLEAN NOT NULL,           -- 0 para não, 1 para sim
    lei_motivo_ai VARCHAR(40) NOT NULL,
    art_lei_motivo_ai VARCHAR(40) NOT NULL,
    decreto_motivo_ai VARCHAR(40) NOT NULL,
    art_decreto_motivo_ai VARCHAR(40) NOT NULL,
    penalidade_decreto_motivo_ai VARCHAR(40) NOT NULL,
    art_penalidade_decreto_motivo_ai VARCHAR(40) NOT NULL,
    multa_inicial_motivo_ai DECIMAL(7 , 2 ),
    adicional_animal_motivo_ai DECIMAL(5 , 2 ),
    descricao_motivo_ai TEXT NOT NULL,
    resumo_descricao_motivo_ai VARCHAR(120) NOT NULL,
    PRIMARY KEY (pk_id_motivo_ai)
);

CREATE TABLE IF NOT EXISTS ai (
    pk_id_ai INT NOT NULL AUTO_INCREMENT,
    fk_municipio_lavratura_ai INT NOT NULL,
    fk_id_motivo_ai INT NOT NULL,
    fk_produtor_ai INT NOT NULL,
    fk_id_veterinario_ai INT,
    enquadramento_adicional_ai TEXT,
    advertencia_ai BOOLEAN NOT NULL DEFAULT 0,           -- 0 para multa, 1 para advertencia
    reincidente_ai BOOLEAN NOT NULL DEFAULT 0,           -- 0 para não, 1 para sim
    desconto_ai BOOLEAN NOT NULL DEFAULT 0,				 -- 0 para não, 1 para sim
    numero_ai INT NOT NULL,
    data_ai DATETIME NOT NULL,
    data_ciencia_ai DATE,
    historico_ai TEXT,
    observacoes_ai TEXT,
    processo_ai VARCHAR(20),
    redator_ai VARCHAR(40),
    PRIMARY KEY (pk_id_ai),
    FOREIGN KEY (fk_municipio_lavratura_ai)
        REFERENCES municipio (pk_id_municipio),
    FOREIGN KEY (fk_id_motivo_ai)
        REFERENCES motivo_ai (pk_id_motivo_ai),
	FOREIGN KEY (fk_produtor_ai)
        REFERENCES produtor (pk_id_produtor),
	FOREIGN KEY (fk_id_veterinario_ai)
        REFERENCES veterinario (pk_id_veterinario)
);

CREATE TABLE IF NOT EXISTS ai_especie (
	pk_id_ai_especie INT NOT NULL AUTO_INCREMENT,
    fk_id_ai INT NOT NULL,
    nome_especie_ai_especie VARCHAR(40) NOT NULL,
    qtd_especie INT NOT NULL,
    PRIMARY KEY (pk_id_ai_especie),
    CONSTRAINT fk_ai_especie_ai
    FOREIGN KEY (fk_id_ai)
        REFERENCES ai (pk_id_ai) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dc (
    pk_id_dc INT NOT NULL AUTO_INCREMENT,
    fk_municipio_dc INT NOT NULL,
    fk_produtor_dc INT NOT NULL,
    numero_dc INT NOT NULL,
    data_dc DATE NOT NULL,
    observacoes_dc TEXT,
    PRIMARY KEY (pk_id_dc),
    FOREIGN KEY (fk_municipio_dc)
        REFERENCES municipio (pk_id_municipio),
	FOREIGN KEY (fk_produtor_dc)
        REFERENCES produtor (pk_id_produtor)
);

CREATE TABLE IF NOT EXISTS programa (
    pk_id_programa INT NOT NULL AUTO_INCREMENT,
    nome_programa VARCHAR(150) NOT NULL,
    sigla_programa VARCHAR(30) NOT NULL,
    observacoes_programa TEXT,
    PRIMARY KEY (pk_id_programa)
);

CREATE TABLE IF NOT EXISTS auto_interdicao (
    pk_id_ai INT NOT NULL AUTO_INCREMENT,
    fk_municipio_lavratura_ai INT NOT NULL,
    fk_id_programa_ai INT NOT NULL,
    fk_produtor_ai INT NOT NULL,
    fk_id_veterinario_ai INT,
    numero_ai INT NOT NULL,
    data_ai DATE NOT NULL,
    data_ciencia_ai DATE,
    observacoes_ai TEXT,
    PRIMARY KEY (pk_id_ai),
    FOREIGN KEY (fk_municipio_lavratura_ai)
        REFERENCES municipio (pk_id_municipio),
    FOREIGN KEY (fk_id_programa_ai)
        REFERENCES programa (pk_id_programa),
	FOREIGN KEY (fk_produtor_ai)
        REFERENCES produtor (pk_id_produtor),
	FOREIGN KEY (fk_id_veterinario_ai)
        REFERENCES veterinario (pk_id_veterinario)
);


