-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : db
-- Généré le : sam. 01 août 2020 à 15:51
-- Version du serveur :  8.0.20
-- Version de PHP : 7.4.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `library_emprunts`
--
CREATE DATABASE IF NOT EXISTS `library_emprunts` DEFAULT CHARACTER SET utf8mb4;
USE `library_emprunts`;

-- --------------------------------------------------------

--
-- Structure de la table `emprunt_entity`
--

CREATE TABLE `emprunt_entity` (
  `id` bigint NOT NULL,
  `date_emprunt` datetime DEFAULT NULL,
  `date_retour` datetime DEFAULT NULL,
  `exemplaire_barcode` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `is_extended` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `emprunt_entity`
--

INSERT INTO `emprunt_entity` (`id`, `date_emprunt`, `date_retour`, `exemplaire_barcode`, `user_id`, `is_extended`) VALUES
(1, '2020-06-01 14:05:24', NULL, '819003', 'b9cb236e-912c-468c-a636-ae1f4fe4723b', b'0');

-- --------------------------------------------------------

--
-- Structure de la table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(1);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `emprunt_entity`
--
ALTER TABLE `emprunt_entity`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
