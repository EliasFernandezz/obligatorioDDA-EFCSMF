-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-11-2025 a las 23:07:33
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `obligatoriodda`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `estadoCategoria` tinyint(1) NOT NULL,
  `estado_categoria` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id`, `nombre`, `estadoCategoria`, `estado_categoria`) VALUES
(1, 'Nombre', 1, b'1'),
(2, 'Apellido', 1, b'1'),
(3, 'Pais', 1, b'0'),
(4, 'Ciudad', 1, b'1'),
(5, 'Animal', 1, b'1'),
(6, 'Color', 1, b'1'),
(7, 'Comida', 1, b'1'),
(8, 'Pelicula', 1, b'1'),
(9, 'Profesion', 1, b'1'),
(10, 'Objeto', 1, b'1'),
(11, 'Cantante', 0, b'0'),
(12, 'Grupo musical', 0, b'0'),
(13, 'Serie', 0, b'0'),
(14, 'Anime', 0, b'0'),
(15, 'Videojuego', 0, b'0'),
(16, 'Personaje de serie', 0, b'0'),
(17, 'Personaje de anime', 0, b'0'),
(18, 'Personaje de videojuego', 0, b'0'),
(19, 'Personaje de pelicula', 0, b'0'),
(20, 'Meme', 0, b'0'),
(21, 'Frase famosa', 0, b'0'),
(22, 'Celebridad', 0, b'0'),
(23, 'Red social', 0, b'0'),
(24, 'Palabra random', 0, b'0');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
