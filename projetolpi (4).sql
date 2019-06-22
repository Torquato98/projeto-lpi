-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 22-Jun-2019 às 05:20
-- Versão do servidor: 10.1.37-MariaDB
-- versão do PHP: 7.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `projetolpi`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `areas_conhecimento`
--

CREATE TABLE `areas_conhecimento` (
  `id` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `grandes_areas_conhecimento_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `areas_conhecimento`
--

INSERT INTO `areas_conhecimento` (`id`, `nome`, `grandes_areas_conhecimento_id`) VALUES
(1, 'Matemática', 1),
(2, 'Probabilidade e Estatística', 1),
(3, 'Ciência da Computação', 1),
(4, 'Biologia Geral', 2),
(5, 'Genética', 2),
(6, 'Botânica', 2),
(7, 'Engenharia Civil', 3),
(8, 'Engenharia das Minas', 3),
(9, 'Engenharia de Materias e Metalúrgica', 3),
(10, 'Medicina', 4),
(11, 'Odontologia', 4),
(12, 'Farmácia', 4),
(13, 'Agronomia', 5),
(14, 'Recursos Florestais e Engenharia', 5),
(15, 'Florestal', 5),
(16, 'Direito', 6),
(17, 'Administração', 6),
(18, 'Economia', 6);

-- --------------------------------------------------------

--
-- Estrutura da tabela `areas_pesquisa`
--

CREATE TABLE `areas_pesquisa` (
  `id` int(11) NOT NULL,
  `nome` varchar(80) NOT NULL,
  `areas_conhecimento_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `areas_pesquisa`
--

INSERT INTO `areas_pesquisa` (`id`, `nome`, `areas_conhecimento_id`) VALUES
(1, 'Matemática', 1),
(2, 'Informática', 3),
(3, 'Engenharia Civil', 7),
(4, 'Medicina', 10),
(5, 'Economia', 18);

-- --------------------------------------------------------

--
-- Estrutura da tabela `avaliadores`
--

CREATE TABLE `avaliadores` (
  `id` int(11) NOT NULL,
  `grau` varchar(45) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `sexo` char(1) NOT NULL,
  `rg` varchar(12) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `dt_nasc` date NOT NULL,
  `instituicao_id` int(11) NOT NULL,
  `areas_pesquisa_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `avaliadores`
--

INSERT INTO `avaliadores` (`id`, `grau`, `nome`, `sexo`, `rg`, `cpf`, `dt_nasc`, `instituicao_id`, `areas_pesquisa_id`) VALUES
(1, 'Mestre', 'Ricardo Freitas Costas', 'M', '39.510.267-4', '462.700.550-40', '1969-01-07', 3, 4),
(2, 'Mestre', 'Suzana Regina Silva', 'F', '40.437.146-2', '833.065.300-50', '1989-10-19', 1, 5),
(3, 'Doutor', 'Agenor Santos Campos', 'M', '47.233.253-3', '954.999.840-18', '1997-10-27', 2, 3),
(4, 'Doutor', 'Jubileu Gonçalves', 'M', '34.504.637-7', '846.165.470-68', '1990-01-08', 4, 1),
(5, 'Doutor', 'José da Silva Alencar', 'M', '46.909.024-8', '958.858.010-29', '1995-09-24', 5, 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `grandes_areas_conhecimento`
--

CREATE TABLE `grandes_areas_conhecimento` (
  `id` int(11) NOT NULL,
  `nome` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `grandes_areas_conhecimento`
--

INSERT INTO `grandes_areas_conhecimento` (`id`, `nome`) VALUES
(1, 'Ciências Exatas e da Terra'),
(2, 'Ciências Biológicas'),
(3, 'Engenharias'),
(4, 'Ciências da Saúde'),
(5, 'Ciências Agrárias'),
(6, 'Ciências Sociais Aplicadas');

-- --------------------------------------------------------

--
-- Estrutura da tabela `graus_conhecimento`
--

CREATE TABLE `graus_conhecimento` (
  `id` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `graus_conhecimento`
--

INSERT INTO `graus_conhecimento` (`id`, `nome`) VALUES
(1, 'Mestre'),
(2, 'Doutor');

-- --------------------------------------------------------

--
-- Estrutura da tabela `instituicao`
--

CREATE TABLE `instituicao` (
  `id` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `instituicao`
--

INSERT INTO `instituicao` (`id`, `nome`) VALUES
(1, 'Ifac - Instituto Federal do Acre'),
(2, 'Ufac - Universidade Federal do Acre'),
(3, 'Uniforte - União Educacional do Norte'),
(4, 'Fadasi - Faculdade Diocesana São José'),
(5, 'Faao - Faculdade da Amazônia Ocidental');

-- --------------------------------------------------------

--
-- Estrutura da tabela `pesquisadores`
--

CREATE TABLE `pesquisadores` (
  `id` int(11) NOT NULL,
  `rg` varchar(12) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `nome` varchar(80) NOT NULL,
  `sexo` char(1) NOT NULL,
  `data_nasc` date NOT NULL,
  `instituicao_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `pesquisadores`
--

INSERT INTO `pesquisadores` (`id`, `rg`, `cpf`, `nome`, `sexo`, `data_nasc`, `instituicao_id`) VALUES
(1, '16.601.245-2', '976.990.090-70', 'João da Silva Melo', 'M', '1981-11-21', 4),
(2, '32.225.800-5', '274.419.660-60', 'Roberto Souza Matos', 'M', '1990-02-19', 2),
(3, '23.742.868-4', '372.931.900-00', 'Joana Santos Souza', 'F', '1992-07-14', 1),
(4, '21.944.091-8', '255.824.350-00', 'Christopher Felix Menezes', 'M', '1995-04-09', 5),
(5, '30.644.299-1', '068.959.260-46', 'Jennifer Ribeiro Lima', 'F', '1979-05-02', 3),
(6, '26.398.507-6', '825.682.050-08', 'Ana Rocha de Oliveira', 'F', '1969-09-11', 5),
(7, '49.034.313-2', '110.972.060-25', 'Miguel Lopes Figueiredo', 'M', '1976-12-02', 2),
(8, '11.120.404-5', '312.594.580-16', 'Vitória Lima Leste', 'F', '1967-06-19', 3),
(9, '32.702.316-8', '500.532.470-43', 'Lucas da Silva Souza', 'M', '1981-03-18', 1),
(10, '37.758.246-3', '105.299.080-00', 'Rogério Leão Souza', 'F', '1985-05-22', 4);

-- --------------------------------------------------------

--
-- Estrutura da tabela `pesquisadores_graus`
--

CREATE TABLE `pesquisadores_graus` (
  `id` int(11) NOT NULL,
  `graus_conhecimento_id` int(11) NOT NULL,
  `pesquisadores_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `pesquisadores_graus`
--

INSERT INTO `pesquisadores_graus` (`id`, `graus_conhecimento_id`, `pesquisadores_id`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 2, 4),
(5, 2, 5),
(6, 2, 6),
(7, 2, 7),
(8, 2, 8),
(9, 2, 9),
(10, 2, 10);

-- --------------------------------------------------------

--
-- Estrutura da tabela `projetos`
--

CREATE TABLE `projetos` (
  `id` int(11) NOT NULL,
  `titulo` varchar(80) NOT NULL,
  `duracao` varchar(5) NOT NULL,
  `orcamento` decimal(10,2) NOT NULL,
  `data_envio` date NOT NULL,
  `data_resposta` date DEFAULT NULL,
  `resposta` int(11) DEFAULT NULL,
  `pesquisadores_id` int(11) NOT NULL,
  `instituicao_id` int(11) NOT NULL,
  `avaliadores_id` int(11) NOT NULL,
  `areas_conhecimento_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `projetos`
--

INSERT INTO `projetos` (`id`, `titulo`, `duracao`, `orcamento`, `data_envio`, `data_resposta`, `resposta`, `pesquisadores_id`, `instituicao_id`, `avaliadores_id`, `areas_conhecimento_id`) VALUES
(1, 'Inteligência Articial para Cegos', '46:14', '10000.00', '2019-05-25', '2019-06-13', 1, 1, 2, 1, 3),
(2, 'Técnicas de Ecônomia Financeira Interpessoal', '21:04', '5000.00', '2019-03-12', '2019-04-11', 1, 2, 2, 3, 18),
(3, 'Cadeira de Rodas Eletronica', '21:04', '3000.00', '2019-05-02', '2019-05-02', 1, 4, 4, 1, 3),
(4, 'Estruturas Feitas de Chumbo', '48:00', '10000.00', '2018-10-17', '2018-11-16', 1, 3, 5, 4, 7),
(5, 'Cálculos de Geometria Análitica', '41:00', '2000.00', '2019-05-17', '2019-05-20', 1, 8, 4, 5, 18),
(6, 'Patinete Movido a Energia Solar', '94:15', '20000.00', '2018-01-01', '2018-12-18', 1, 6, 2, 3, 3),
(7, 'Celulas Tronco', '38:00', '13500.00', '2019-01-09', '2019-03-26', 1, 3, 1, 2, 5),
(8, 'Calculos Variáveis', '50:00', '50000.00', '2019-03-12', '2019-03-21', 1, 8, 3, 5, 18),
(9, 'Placas de Energia Solar', '00:50', '1000.00', '2019-05-16', '2019-05-22', 1, 5, 3, 1, 9),
(10, 'Mordomo Robô', '36:00', '60000.00', '2019-02-12', '2019-03-13', 1, 3, 3, 1, 3),
(11, 'Filosofia Existencial', '43:00', '245.00', '2019-07-24', '2019-07-25', 0, 1, 3, 4, 17),
(12, 'Desenvolvimento Ambiental Auto-Sustentável', '27:00', '50000.00', '2019-08-14', '2019-08-16', 0, 4, 3, 2, 6),
(13, 'Tecnologia 5D', '20:00', '150000.00', '2019-08-20', '2019-08-23', 0, 4, 1, 2, 1),
(14, 'Teclado Para Celulares', '12:00', '2000.00', '2019-08-25', '2019-08-26', 0, 6, 2, 5, 1),
(15, 'Mouses Visuais', '23:00', '27000.00', '2019-08-27', '2019-09-01', 0, 5, 3, 4, 1),
(16, 'Cama Auto-Ajustadora', '00:50', '2000.00', '2019-09-02', NULL, NULL, 3, 2, 2, 1),
(17, 'Teoria Das Cordas', '01:30', '300.00', '2019-09-04', NULL, NULL, 3, 1, 3, 1),
(18, 'Neuro-ciência Inovadora', '02:40', '4000.00', '2019-09-07', NULL, NULL, 2, 1, 5, 4),
(19, 'Sonambulismo Abneural', '05:30', '5000.00', '2019-09-20', NULL, NULL, 2, 2, 3, 4),
(20, 'Bio-degradação Ambiental', '10:20', '325.50', '2019-10-25', NULL, NULL, 5, 3, 4, 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `areas_conhecimento`
--
ALTER TABLE `areas_conhecimento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_grandes_areas_conhecimento_areas_conhecimento` (`grandes_areas_conhecimento_id`);

--
-- Indexes for table `areas_pesquisa`
--
ALTER TABLE `areas_pesquisa`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_areas_pesquisa_areas_conhecimento` (`areas_conhecimento_id`);

--
-- Indexes for table `avaliadores`
--
ALTER TABLE `avaliadores`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_avaliadores_instituicao` (`instituicao_id`),
  ADD KEY `fk_avaliadores_areas_pesquisa` (`areas_pesquisa_id`);

--
-- Indexes for table `grandes_areas_conhecimento`
--
ALTER TABLE `grandes_areas_conhecimento`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `graus_conhecimento`
--
ALTER TABLE `graus_conhecimento`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `instituicao`
--
ALTER TABLE `instituicao`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pesquisadores`
--
ALTER TABLE `pesquisadores`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_pesquisadores_instituicao` (`instituicao_id`);

--
-- Indexes for table `pesquisadores_graus`
--
ALTER TABLE `pesquisadores_graus`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_pesquisadores_pesquisadores_graus` (`pesquisadores_id`),
  ADD KEY `fk_graus_conhecimento_pesquisadores_graus` (`graus_conhecimento_id`);

--
-- Indexes for table `projetos`
--
ALTER TABLE `projetos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_projetos_pesquisadores` (`pesquisadores_id`),
  ADD KEY `fk_projetos_instituicao` (`instituicao_id`),
  ADD KEY `fk_projetos_avaliadores` (`avaliadores_id`),
  ADD KEY `fk_projetos_areas_conhecimento` (`areas_conhecimento_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `areas_conhecimento`
--
ALTER TABLE `areas_conhecimento`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `areas_pesquisa`
--
ALTER TABLE `areas_pesquisa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `avaliadores`
--
ALTER TABLE `avaliadores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `grandes_areas_conhecimento`
--
ALTER TABLE `grandes_areas_conhecimento`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `graus_conhecimento`
--
ALTER TABLE `graus_conhecimento`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `instituicao`
--
ALTER TABLE `instituicao`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `pesquisadores`
--
ALTER TABLE `pesquisadores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `pesquisadores_graus`
--
ALTER TABLE `pesquisadores_graus`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `projetos`
--
ALTER TABLE `projetos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `areas_conhecimento`
--
ALTER TABLE `areas_conhecimento`
  ADD CONSTRAINT `fk_grandes_areas_conhecimento_areas_conhecimento` FOREIGN KEY (`grandes_areas_conhecimento_id`) REFERENCES `grandes_areas_conhecimento` (`id`);

--
-- Limitadores para a tabela `areas_pesquisa`
--
ALTER TABLE `areas_pesquisa`
  ADD CONSTRAINT `fk_areas_pesquisa_areas_conhecimento` FOREIGN KEY (`areas_conhecimento_id`) REFERENCES `areas_conhecimento` (`id`);

--
-- Limitadores para a tabela `avaliadores`
--
ALTER TABLE `avaliadores`
  ADD CONSTRAINT `fk_avaliadores_areas_pesquisa` FOREIGN KEY (`areas_pesquisa_id`) REFERENCES `areas_pesquisa` (`id`),
  ADD CONSTRAINT `fk_avaliadores_instituicao` FOREIGN KEY (`instituicao_id`) REFERENCES `instituicao` (`id`);

--
-- Limitadores para a tabela `pesquisadores`
--
ALTER TABLE `pesquisadores`
  ADD CONSTRAINT `fk_pesquisadores_instituicao` FOREIGN KEY (`instituicao_id`) REFERENCES `instituicao` (`id`);

--
-- Limitadores para a tabela `pesquisadores_graus`
--
ALTER TABLE `pesquisadores_graus`
  ADD CONSTRAINT `fk_graus_conhecimento_pesquisadores_graus` FOREIGN KEY (`graus_conhecimento_id`) REFERENCES `graus_conhecimento` (`id`),
  ADD CONSTRAINT `fk_pesquisadores_pesquisadores_graus` FOREIGN KEY (`pesquisadores_id`) REFERENCES `pesquisadores` (`id`);

--
-- Limitadores para a tabela `projetos`
--
ALTER TABLE `projetos`
  ADD CONSTRAINT `fk_projetos_areas_conhecimento` FOREIGN KEY (`areas_conhecimento_id`) REFERENCES `areas_conhecimento` (`id`),
  ADD CONSTRAINT `fk_projetos_avaliadores` FOREIGN KEY (`avaliadores_id`) REFERENCES `avaliadores` (`id`),
  ADD CONSTRAINT `fk_projetos_instituicao` FOREIGN KEY (`instituicao_id`) REFERENCES `instituicao` (`id`),
  ADD CONSTRAINT `fk_projetos_pesquisadores` FOREIGN KEY (`pesquisadores_id`) REFERENCES `pesquisadores` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
