-- Script de inicialização para ambiente de desenvolvimento
-- Versão simplificada sem blocos DO/END

-- Desativa verificações de FK temporariamente
SET session_replication_role = 'replica';

-- Limpeza das tabelas (ordem importante - relacionamentos primeiro)
TRUNCATE TABLE servico_produto CASCADE;
TRUNCATE TABLE cliente_telefones CASCADE;
TRUNCATE TABLE empresa_telefone CASCADE;
TRUNCATE TABLE fornecedor_telefones CASCADE;
TRUNCATE TABLE confirmation_token CASCADE;

-- Depois as entidades principais
TRUNCATE TABLE servico CASCADE;
TRUNCATE TABLE produto CASCADE;
TRUNCATE TABLE familia CASCADE;
TRUNCATE TABLE empresa CASCADE;
TRUNCATE TABLE fornecedor CASCADE;
TRUNCATE TABLE cliente CASCADE;
TRUNCATE TABLE usuario CASCADE;

-- Reativa verificações de FK
SET session_replication_role = 'origin';

-- Reinicia as sequences
-- ALTER SEQUENCE familia_codigo_seq RESTART WITH 1;
-- ALTER SEQUENCE produto_codigo_seq RESTART WITH 1;
-- ALTER SEQUENCE servico_codigo_seq RESTART WITH 1;
-- ALTER SEQUENCE usuario_id_seq RESTART WITH 1;

-- Inserção de Famílias (5 exemplos)
INSERT INTO familia (nome) VALUES
       ('Eletrônicos'),
       ('Móveis'),
       ('Alimentos'),
       ('Limpeza'),
       ('Brinquedos');

-- Inserção de Empresas (5 exemplos com CNPJ válidos)
INSERT INTO empresa (razao_social, cnpj, inscricao_estadual, email, site, logotipo_path, cep, logradouro, numero, bairro, cidade, uf) VALUES
       ('Tech Solutions LTDA', '52124034000191', '123456789', 'contato@techsol.com.br', 'www.techsol.com.br', '/logos/techsol.png', '01001000', 'Rua das Flores', '100', 'Centro', 'São Paulo', 'SP'),
       ('Moveis Planejados SA', '16992749000100', '987654321', 'vendas@moveisplan.com.br', 'www.moveisplan.com.br', '/logos/moveisplan.png', '20020000', 'Avenida Brasil', '200', 'Jardins', 'Rio de Janeiro', 'RJ'),
       ('Alimentos Naturais EIRELI', '35386928000130', '456123789', 'sac@alimentosnaturais.com.br', 'www.alimentosnaturais.com.br', '/logos/alimentos.png', '30030000', 'Rua das Palmeiras', '300', 'Vila Nova', 'Belo Horizonte', 'MG'),
       ('Clean Produtos de Limpeza LTDA', '35429353000196', '321654987', 'sac@clean.com.br', 'www.clean.com.br', '/logos/clean.png', '40040000', 'Avenida Paulista', '400', 'Bela Vista', 'São Paulo', 'SP'),
       ('Brinquedos Educativos ME', '65174033000101', '654987321', 'contato@brinquedosedu.com.br', 'www.brinquedosedu.com.br', '/logos/brinquedos.png', '50050000', 'Rua das Acácias', '500', 'Centro', 'Curitiba', 'PR');

-- Inserção de Telefones para Empresas
INSERT INTO empresa_telefone (empresa_id, tipo, ddd, numero) VALUES
       ((SELECT id FROM empresa WHERE cnpj = '52124034000191'), 'CELULAR', 11, '99998888'),
       ((SELECT id FROM empresa WHERE cnpj = '16992749000100'), 'CELULAR', 11, '988887777'),
       ((SELECT id FROM empresa WHERE cnpj = '35386928000130'), 'FIXO', 21, '33334444'),
       ((SELECT id FROM empresa WHERE cnpj = '35386928000130'), 'FIXO', 31, '55556666'),
       ((SELECT id FROM empresa WHERE cnpj = '35429353000196'), 'FIXO', 31, '55556667'),
       ((SELECT id FROM empresa WHERE cnpj = '35429353000196'), 'FIXO', 11, '77778888'),
       ((SELECT id FROM empresa WHERE cnpj = '65174033000101'), 'CELULAR', 41, '99991111');

-- Inserção de Fornecedores (5 exemplos - 3 PJ e 2 PF)
INSERT INTO fornecedor (cpf_ou_cnpj, tipo_pessoa, nome_ou_razao_social, email, cep, logradouro, numero, bairro, cidade, uf) VALUES
       ('75942559000130', 'PJ', 'Fornecedor Eletrônicos LTDA', 'vendas@fornecedoreletro.com.br', '01001000', 'Rua das Flores', '100', 'Centro', 'São Paulo', 'SP'),
       ('31944838000167', 'PJ', 'Distribuidora de Móveis SA', 'contato@distribuidoramoveis.com.br', '20020000', 'Avenida Brasil', '200', 'Jardins', 'Rio de Janeiro', 'RJ'),
       ('58615447000155', 'PJ', 'Alimentos Importados EIRELI', 'compras@alimentosimport.com.br', '30030000', 'Rua das Palmeiras', '300', 'Vila Nova', 'Belo Horizonte', 'MG'),
       ('52842321006', 'PF', 'João da Silva', 'joao.silva@gmail.com', '40040000', 'Avenida Paulista', '400', 'Bela Vista', 'São Paulo', 'SP'),
       ('98812430090', 'PF', 'Maria Oliveira', 'maria.oliveira@hotmail.com', '50050000', 'Rua das Acácias', '500', 'Centro', 'Curitiba', 'PR');

-- Telefones para Fornecedores
INSERT INTO fornecedor_telefones (fornecedor_cpf_ou_cnpj, tipo, ddd, numero) VALUES
       ('75942559000130', 'CELULAR', 11, '99998888'),
       ('75942559000130', 'CELULAR', 11, '988887777'),
       ('31944838000167', 'FIXO', 21, '33334444'),
       ('58615447000155', 'FIXO', 31, '55556666'),
       ('52842321006', 'CELULAR', 11, '977776666'),
       ('98812430090', 'FIXO', 41, '32221111'),
       ('98812430090', 'CELULAR', 41, '99991111');

-- Inserção de Clientes (5 exemplos - 3 PF e 2 PJ)
INSERT INTO cliente (cpf_ou_cnpj, tipo_pessoa, nome_ou_razao_social, email, cep, logradouro, numero, bairro, cidade, uf) VALUES
       ('96729915099', 'PF', 'Carlos Pereira', 'carlos.pereira@gmail.com', '01001000', 'Rua das Flores', '100', 'Centro', 'São Paulo', 'SP'),
       ('05716198007', 'PF', 'Ana Santos', 'ana.santos@hotmail.com', '20020000', 'Avenida Brasil', '200', 'Jardins', 'Rio de Janeiro', 'RJ'),
       ('49221274020', 'PF', 'Pedro Oliveira', 'pedro.oliveira@yahoo.com.br', '30030000', 'Rua das Palmeiras', '300', 'Vila Nova', 'Belo Horizonte', 'MG'),
       ('28172560000142', 'PJ', 'Empresa Comercial LTDA', 'compras@empresacomercial.com.br', '40040000', 'Avenida Paulista', '400', 'Bela Vista', 'São Paulo', 'SP'),
       ('03836960000184', 'PJ', 'Distribuidora ABC SA', 'contato@distribuidoraabc.com.br', '50050000', 'Rua das Acácias', '500', 'Centro', 'Curitiba', 'PR');

-- Inserção de telefones para clientes
INSERT INTO cliente_telefones (cliente_cpf_ou_cnpj, tipo, ddd, numero) VALUES
       ('96729915099', 'CELULAR', 11, '988887777'),
       ('05716198007', 'FIXO', 21, '25556666'),
       ('05716198007', 'CELULAR', 21, '988885555'),
       ('49221274020', 'FIXO', 31, '32223333'),
       ('28172560000142', 'FIXO', 11, '33334444'),
       ('28172560000142', 'FIXO', 11, '33334445'),
       ('03836960000184', 'CELULAR', 41, '99991111');

-- Inserção de Produtos (5 exemplos)
INSERT INTO produto (nome, descricao, preco, familia, fornecedor) VALUES
       ('Smartphone X', 'Smartphone última geração', 2999.90, 1, '75942559000130'),
       ('Sofá 3 lugares', 'Sofá em couro sintético', 1899.00, 2, '31944838000167'),
       ('Arroz Integral', 'Pacote 5kg de arroz integral', 24.90, 3, '58615447000155'),
       ('Detergente Neutro', 'Detergente 500ml', 3.50, 4, '52842321006'),
       ('Quebra-Cabeça 500 peças', 'Quebra-cabeça educativo', 59.90, 5, '98812430090');

-- Inserção de Usuários (5 exemplos)
INSERT INTO usuario (nome_usuario, email, senha, categoria, ativo) VALUES
       ('admin', 'admin@empresa.com.br', '$2a$10$xD7bxJ/qE4U5HsiPa0YQ0uR.5X5zNeDVjJXJQ9pN3u9p6d2YzWQ1K', 'ADMINISTRADOR', true),
       ('gerente', 'gerente@empresa.com.br', '$2a$10$xD7bxJ/qE4U5HsiPa0YQ0uR.5X5zNeDVjJXJQ9pN3u9p6d2YzWQ1K', 'ADMINISTRADOR', true),
       ('vendedor1', 'vendedor1@empresa.com.br', '$2a$10$xD7bxJ/qE4U5HsiPa0YQ0uR.5X5zNeDVjJXJQ9pN3u9p6d2YzWQ1K', 'USUARIO', true),
       ('vendedor2', 'vendedor2@empresa.com.br', '$2a$10$xD7bxJ/qE4U5HsiPa0YQ0uR.5X5zNeDVjJXJQ9pN3u9p6d2YzWQ1K', 'USUARIO', true),
       ('analista', 'analista@empresa.com.br', '$2a$10$xD7bxJ/qE4U5HsiPa0YQ0uR.5X5zNeDVjJXJQ9pN3u9p6d2YzWQ1K', 'USUARIO', true);

-- Inserção de Serviços (5 exemplos)
INSERT INTO servico (cliente, mao_de_obra_descricao, mao_de_obra_preco, preco_total_produtos, preco_total, desconto_tipo, desconto_valor, preco_total_com_desconto) VALUES
       ('96729915099', 'Instalação de aplicativos', 50.00, 2999.90, 3049.90, 'PORCENTAGEM', 10.00, 2744.91),
       ('05716198007', 'Montagem de móveis', 150.00, 1899.00, 2049.00, 'VALOR', 100.00, 1949.00),
       ('49221274020', 'Entrega expressa', 30.00, 24.90, 54.90, NULL, NULL, 54.90),
       ('28172560000142', 'Limpeza pós-obra', 200.00, 3.50, 203.50, 'PORCENTAGEM', 5.00, 193.33),
       ('03836960000184', 'Instruções de uso', 80.00, 59.90, 139.90, NULL, NULL, 139.90);

-- Relacionamento Serviço-Produto
INSERT INTO servico_produto (servico_codigo, produto_codigo) VALUES
       (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);