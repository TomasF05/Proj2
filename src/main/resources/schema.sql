-- Create funcionario table if it doesn't exist
CREATE TABLE IF NOT EXISTS funcionario (
    idfuncionario NUMERIC PRIMARY KEY,
    nome VARCHAR(255),
    tipo NUMERIC,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);

-- Create servico table if it doesn't exist
CREATE TABLE IF NOT EXISTS servico (
    idservico NUMERIC PRIMARY KEY,
    nome VARCHAR(255),
    descricao VARCHAR(255)
);

-- Create peca table if it doesn't exist
CREATE TABLE IF NOT EXISTS peca (
    idpeca NUMERIC PRIMARY KEY,
    nome VARCHAR(255),
    referencia VARCHAR(255),
    preco NUMERIC,
    qtd NUMERIC
);