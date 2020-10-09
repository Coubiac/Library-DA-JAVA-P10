-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : db
-- Généré le : sam. 01 août 2020 à 15:52
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
-- Base de données : `library_users`
--
CREATE DATABASE IF NOT EXISTS `library_users` DEFAULT CHARACTER SET utf8mb4;
USE `library_users`;

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
(20);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `address` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `encrypted_password` varchar(255) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `postal_code` varchar(255) NOT NULL,
  `user_id` varchar(120) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `address`, `city`, `email`, `encrypted_password`, `first_name`, `last_name`, `postal_code`, `user_id`) VALUES
(9, '4 rue de la Turquoise', 'Aigrefeuille Sur Maine', 'benoit@triscel.fr', '$2a$10$BEZKhXn/f/1cyHsoGEUM6uqjblzgIHiAjzpZt3LT4YUIadlHNeInK', 'Benoit', 'GRISOT', '44140', 'b9cb236e-912c-468c-a636-ae1f4fe4723b'),
(11, 'Dans la salle info', 'Nantes', 'batch@batch.fr', '$2a$10$E6ssiMFgS9ri5yC5R1/VXOY5obfXbWmdyJPDk5cLJ6TwUm5YmMXry', 'Batch', 'Batch', '12345', 'd431acba-b0f3-4e33-a53d-694226987eca'),
(12, '4 Rue de la Turquoise', 'Aigrefeuille Sur Maine', 'benoit.grisot@gmail.com', '$2a$10$hnOuP5WltlH7k6VPd0cN2uQKcRgzIPMYti7qf.lYT2FrcFZ/nsEfy', 'benoit', 'GRISOT', '44140', '45508482-2d5a-4408-b1b8-759e0cd48980'),
(13, 'Rue Paradis', 'Paris', 'mathieu-martinez@live.com', '$2a$10$w9xp69WWewvf8J513mdAge1Z0VR6qUQHWS0dKBQRgVA5MJBkTT5PK', 'Mathieu', 'Martinez', '75000', 'c9eb6430-82c8-4397-90fd-a761aa25fbe4');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  ADD UNIQUE KEY `UK_6efs5vmce86ymf5q7lmvn2uuf` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
