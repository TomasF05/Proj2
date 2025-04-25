-- Check if the funcionario table exists and has no records
INSERT INTO funcionario (idfuncionario, nome, tipo, username, password)
SELECT 1, 'Admin', 1, 'admin', 'admin'
WHERE NOT EXISTS (SELECT 1 FROM funcionario LIMIT 1);

-- Add some sample services if none exist
INSERT INTO servico (idservico, nome, descricao)
SELECT 1, 'Troca de Óleo', 'Troca de óleo do motor e filtro'
WHERE NOT EXISTS (SELECT 1 FROM servico LIMIT 1);

INSERT INTO servico (idservico, nome, descricao)
SELECT 2, 'Revisão Completa', 'Revisão de todos os componentes do veículo'
WHERE NOT EXISTS (SELECT 1 FROM servico WHERE idservico = 2);

-- Add some sample parts if none exist
INSERT INTO peca (idpeca, nome, referencia, preco, qtd)
SELECT 1, 'Filtro de Óleo', 'FO-123', 15.99, 20
WHERE NOT EXISTS (SELECT 1 FROM peca LIMIT 1);

INSERT INTO peca (idpeca, nome, referencia, preco, qtd)
SELECT 2, 'Filtro de Ar', 'FA-456', 25.99, 15
WHERE NOT EXISTS (SELECT 1 FROM peca WHERE idpeca = 2);