-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 13, 2024 at 07:14 PM
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

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `name` varchar(255) NOT NULL,
  `userName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`name`, `userName`) VALUES
('idk fr', 'hama'),
('School', 'aa'),
('School', 'hama');

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `id` int(11) NOT NULL,
  `taskId` int(11) DEFAULT NULL,
  `text` text NOT NULL,
  `creationDate` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

CREATE TABLE `tasks` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `status` enum('COMPLETED','IN_PROGRESS','PENDING','ABANDONED') NOT NULL DEFAULT 'PENDING',
  `dueDate` date DEFAULT NULL,
  `creationDate` timestamp NOT NULL DEFAULT current_timestamp(),
  `priority` enum('LOW','MEDIUM','HIGH') DEFAULT 'LOW',
  `reminder` enum('DAILY','WEEKLY','MONTHLY') DEFAULT 'WEEKLY',
  `categoryName` varchar(255) DEFAULT NULL,
  `userName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tasks`
--

INSERT INTO `tasks` (`id`, `title`, `description`, `status`, `dueDate`, `creationDate`, `priority`, `reminder`, `categoryName`, `userName`) VALUES
(7, 'aaaaa', 'gg', 'COMPLETED', '2024-12-10', '2024-12-07 23:00:00', 'MEDIUM', 'WEEKLY', 'idk fr', 'hama'),
(9, 'idk', 'asdfghjk', 'COMPLETED', '2024-12-16', '2024-12-07 23:00:00', 'LOW', 'WEEKLY', NULL, 'hama'),
(10, 'aaaaaaa', 'aaaaaaaaaaaaaaaaaaaa', 'COMPLETED', '2024-12-27', '2024-12-07 23:00:00', 'HIGH', 'WEEKLY', NULL, 'hama'),
(12, 'moatsm', 'asdfghj', 'PENDING', '2024-12-26', '2024-12-07 23:00:00', 'MEDIUM', 'WEEKLY', 'School', 'hama'),
(13, 'ta', 'fff', 'PENDING', '2024-12-17', '2024-12-07 23:00:00', 'LOW', 'WEEKLY', NULL, 'aa'),
(14, 'sd', 'ssssssss', 'PENDING', '2024-12-24', '2024-12-07 23:00:00', 'LOW', 'WEEKLY', 'School', 'aa'),
(15, 'oday', 'asdfgh', 'PENDING', '2024-12-17', '2024-12-08 23:00:00', 'HIGH', 'WEEKLY', NULL, 'hama'),
(16, 'asdfgh', 'sdfghj', 'COMPLETED', '2024-12-18', '2024-12-08 23:00:00', 'HIGH', 'WEEKLY', 'School', 'hama');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userName` varchar(255) NOT NULL,
  `passWord` varchar(255) NOT NULL,
  `fullName` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userName`, `passWord`, `fullName`, `email`) VALUES
('aa', 'Ab1', 'aa', '1@gmai.com'),
('hama', '1', 'mohamed meftah', '1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`name`,`userName`),
  ADD KEY `userName` (`userName`) USING BTREE,
  ADD KEY `name` (`name`) USING BTREE;

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `taskId` (`taskId`);

--
-- Indexes for table `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_title` (`title`),
  ADD KEY `fk_userName` (`userName`),
  ADD KEY `tasks_ibfk_1` (`categoryName`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userName`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tasks`
--
ALTER TABLE `tasks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `categories`
--
ALTER TABLE `categories`
  ADD CONSTRAINT `userName` FOREIGN KEY (`userName`) REFERENCES `users` (`userName`);

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`taskId`) REFERENCES `tasks` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `tasks`
--
ALTER TABLE `tasks`
  ADD CONSTRAINT `fk_userName` FOREIGN KEY (`userName`) REFERENCES `users` (`userName`),
  ADD CONSTRAINT `tasks_ibfk_1` FOREIGN KEY (`categoryName`) REFERENCES `categories` (`name`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
