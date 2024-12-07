-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 06, 2024 at 02:49 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `todolist`
--

-- --------------------------------------------------------

CREATE TABLE users (
    userName VARCHAR(255) PRIMARY KEY NOT NULL,
    passWord VARCHAR(255) NOT NULL,
    fullName VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

--
--category:
--
CREATE TABLE categories (
    name VARCHAR(255) PRIMARY KEY,
    userName VARCHAR(255) NOT NULL,
    FOREIGN KEY (userName) REFERENCES users(userName) ON DELETE SET NULL
);

CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status ENUM('COMPLETED', 'IN_PROGRESS', 'PENDING', 'ABANDONED') NOT NULL,
    dueDate DATE,
    creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    priority ENUM('LOW', 'MEDIUM', 'HIGH') NOT NULL,
    reminder ENUM('DAILY', 'WEEKLY', 'MONTHLY'),
    categoryName VARCHAR(255),
    userName VARCHAR(255) NOT NULL,
    FOREIGN KEY (categoryName) REFERENCES categories(name) ON DELETE SET NULL,
    FOREIGN KEY (userName) REFERENCES users(userName) ON DELETE SET NULL
);

--
--Comments:
--
CREATE TABLE comments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    taskId INT,
    text TEXT NOT NULL,
    creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (taskId) REFERENCES tasks(id) ON DELETE CASCADE
);






/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
