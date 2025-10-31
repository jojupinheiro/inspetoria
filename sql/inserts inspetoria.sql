insert into motivo_ai (preve_adv_motivo_ai, lei_motivo_ai, art_lei_motivo_ai, decreto_motivo_ai, art_decreto_motivo_ai, penalidade_decreto_motivo_ai,
	art_penalidade_decreto_motivo_ai, multa_inicial_motivo_ai, adicional_animal_motivo_ai, descricao_motivo_ai, resumo_descricao_motivo_ai)
	values 
    (1,"13.467/2010", "5º, inc. IV", "52.434/2015", "18º, inc. V", "52.434", "33º, inc. I", 60.0, 3.0, 
    "não declarar o inventário dos animais descritos abaixo, nos prazos e formas estabelecidos pelo Serviço Veterinário Oficial,", "Não declarar inventário"),
    (1,"13.467/2010", "5º, inc. IV", "52.434/2015", "18º, inc. V", "52.434", "34º, inc. I", 100.0, 3.0, 
    "não manter inventário atualizado junto ao SVO por categoria de animais existentes na propriedade, nos prazos e formas estabelecidos pelo SVO,", "Não manter inventário atualizado"),
    (1,"13.467/2010", "5º, inc. IV", "52.434/2015", "18º, inc. IX", "52.434", "35º, inc. I", 100.0, 0, 
    "não prestar informações sobre o recebimento de animais, mediante apresentação de documento oficial de trânsito no prazo de 30 dias a contar da emissão da GTA,", "Não apresentar GTA em 30d"),
    (1,"13.467/2010", "5º, inc. I", "52.434/2015", "21º, inc. I", "52.434", "35º, inc. I", 100.0, 0, 
    " ", "Agropec - Não executar medidas de DSA"),
    (1,"13.467/2010", "5º, inc. I", "52.434/2015", "21º, inc. I", "52.434", "50º, inc. I", 100.0, 0, 
    " ", "Agropec - Não prestar informações"),
    (1,"13.467/2010", "7º, inc. II", "52.434/2015", "24º", "52.434", "54º, inc. I", 1000.0, 0, 
    " ", "Evento - Não prestar informações (ocorr. sanit./comerc.)"),
    (1,"13.467/2010", "5º, inc. VII", "52.434/2015", "18º, inc. III", "52.434", "32º, inc. I", 60.0, 0, 
    " ", "Não possuir cadastro - proprietários, transport., deposit."),
    (0,"13.467/2010", "5º, inc. V", "52.434/2015", "18º, inc. XI", "52.434", "40º", 60.0, 1.0, 
    " ", "Não realizar a vacinação"),
    (1,"13.467/2010", "5º, inc. IV", "52.434/2015", "18º, inc. II", "52.434", "35º", 100.0, 0, 
    " ", "Produtor - Não prestar informações"),
    (1,"13.467/2010", "5º, inc. I", "52.434/2015", "18º, inc. I e XIII", "52.434", "35º", 100.0, 0, 
    " ", "Produtor genérico - Não executar medidas de DSA"),
    (0,"13.467/2010", "5º, inc. VI", "52.434/2015", "22º, inc. III", "52.434", "43º", 70.0, 5.0, 
    " ", "Receber animais sem GTA (eventos)"),
    (0,"13.467/2010", "5º, inc. VI", "52.434/2015", "18º, inc. VIII", "52.434", "43º", 70.0, 5.0, 
    " ", "Receber animais sem GTA (produtor)"),
	(0,"13.467/2010", "11º, § 1º e 2º", "52.434/2015", "18º, inc. VII", "52.434", "42º", 100.0, 3.0, 
    "Transitar animais de peculiar interesse do estado sem a devida documentação de trânsito e/ou zoossanitária", "Transitar sem GTA");

insert into programa (nome_programa, sigla_programa, observacoes_programa) values
('Programa Nacional de Controle e Erradicação de Brucelose e Tuberculose', 'PNCEBT', null),
('Programa Nacional de Controle da Raiva Herbívora', 'PNCRH', null),
('Programa Nacional de Prevenção e Vigilância à Encefalopatia Espongiforme Bovina', 'PNEEB', null),
('Programa Nacional de Erradicação da Febre Aftosa', 'PNEFA', null),
('Programa Nacional de Sanidade dos Animais Aquáticos', 'PNSAA', null),
('Programa Nacional de Sanidade Apícola', 'PNSAP', null),
('Programa Nacional de Sanidade Avícola', 'PNSA', null),
('Programa Nacional de Sanidade Equina', 'PNSE', null),
('Programa Nacional de Sanidade Suína', 'PNSS', null),
('Programa Estadual de Sanidade Ovina', 'PROESO', null);


insert into veterinario (nome_veterinario,if_veterinario,crmv_veterinario) values 
("Rebeca Dopke", "4873920/01", "13925"),
("Antonio Werner", "7423101/01", "9088"),
("João Juliano Pinheiro", "3891496/01", "13217"),
("Ricardo Strohschoen", "4232631/01", "3027"),
("Jean Ricardo Anacker", "3960749/01", "9188");

insert into municipio (nome_municipio, cod_ibge_municipio) values ("São Gabriel", "4318309");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Venâncio Aires", "4322608");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("São Francisco de Assis", "4318101");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Pejuçara", "4314308");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Mato Leitão", "4312153");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Lavras do Sul", "4311502");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Soledade", "4320800");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Vale Verde", "4322541");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Santiago", "4317400");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Alegrete", "4300406");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Augusto Pestana", "4301503");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Cacequi", "4302907");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Cerro Largo", "4305207");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Morro Redondo", "4312450");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Eugênio de Castro", "4307831");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Quaraí", "4315305");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Santa Maria", "4316907");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Santana do Livramento", "4317103");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Vera Cruz", "4322707");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Santo Antônio das Missões", "4317707");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Santo Antônio da Patrulha", "4317608");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Quevedos", "4315321");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("São Martinho da Serra", "4318491");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Uruguaiana", "4322400");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Passo do Sobrado", "4314076");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Palmeira das Missões", "4313706");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Santa Vitória do Palmar", "4317301");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("São Vicente do Sul", "4320001");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Cachoeira do Sul", "4303004");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Piratini", "4314605");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Jacuizinho", "4310876");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Santa Bárbara do Sul", "4316709");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Candelária", "4304200");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("André da Rocha", "4300661");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Bossoroca", "4302501");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Manoel Viana", "4311759");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Nova Palma", "4313102");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Passo Fundo", "4314100");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Caçapava do Sul", "4302808");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Dom Pedrito", "4306601");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Bagé", "4301602");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Jaguarão", "4311007");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Aceguá", "4300034");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Santa Cruz do Sul", "4316808");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Roque Gonzales", "4316303");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Lajeado", "4311403");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Imigrante", "4310363");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Nicolau Vergueiro", "4312674");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Erechim", "4307005");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Canoas", "4304606");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Caxias do Sul", "4305108");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Guaíba", "4309308");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Porto Alegre", "4314902");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("São Miguel das Missões", "4318806");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Garruchos", "4308656");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Rio Pardo", "4315701");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Rosário do Sul", "4316402");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Itaqui", "4310603");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("São Borja", "4318002");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Arroio Grande", "4301305");
insert into municipio (nome_municipio, cod_ibge_municipio) values ("Camaquã", "4303509");

insert into municipioPadrao (fk_id_municipio) values ((SELECT pk_id_municipio FROM municipio WHERE (nome_municipio = 'Venâncio Aires')));