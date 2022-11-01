-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 01, 2022 at 02:58 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shopabc`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `id` int(11) NOT NULL,
  `activated` int(11) NOT NULL,
  `admin` int(11) NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `fullname` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `photo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `verify_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `verify_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`id`, `activated`, `admin`, `email`, `fullname`, `password`, `photo`, `username`, `verify_code`, `verify_date`) VALUES
(1, 1, 0, 'myadmin@gmail.com', 'Nguyễn Văn Tèo', 'teonv123', NULL, 'teonv', NULL, NULL),
(2, 1, 1, 'nopt@fpt.edu.vn', 'Nguyễn Thi Nở', '123', NULL, 'nopt', NULL, NULL),
(3, 1, 2, 'nopt2@fpt.edu.vn', 'Nguyễn Thi Minh', 'abcc', NULL, 'nopt2', NULL, NULL),
(4, 0, 0, 'teoa@fpt.edu.vn1', 'Nguyễn Thi Ngọc', '123', NULL, 'teoa', NULL, NULL),
(5, 0, 0, 'teonva@fpt.edu.vn', 'Nguyễn Văn Chiến', '123', NULL, 'teonvA', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`) VALUES
(1, 'Tivi'),
(2, 'Tủ Lạnh'),
(3, 'Điều hòa'),
(4, 'Laptop'),
(5, 'Điện thoại'),
(6, 'Máy giặt');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `create_date` datetime NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `note` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `number_phone` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `status` int(11) NOT NULL,
  `create_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `address`, `create_date`, `user_id`, `note`, `number_phone`, `status`, `create_by`) VALUES
(2, 'ha noi', '2022-10-20 00:00:00', 2, '@@', '0123456789', 2, NULL),
(3, 'ha noi', '2022-10-21 00:00:00', NULL, '@@', '0123456789', 1, NULL),
(4, 'ha noi', '2022-10-21 00:00:00', NULL, '@@', '0123456789', 2, NULL),
(5, 'Hai Phong', '2022-10-21 16:13:30', 1, '@@', '0123456789', 3, NULL),
(6, 'ha noi', '2022-10-21 22:04:59', NULL, '@@', '0123456789', 0, NULL),
(7, 'ha noi', '2022-10-21 22:04:59', NULL, '@@', '0123456789', 0, NULL),
(8, 'ha noi', '2022-10-21 22:09:04', NULL, '@@', '0123456789', 0, NULL),
(9, 'ha noi', '2022-10-21 22:09:04', NULL, '@@', '0123456789', 0, NULL),
(10, 'Hai Phong', '2022-10-21 22:09:47', NULL, '@@', '0123456789', 0, NULL),
(11, 'Hai Phong', '2022-10-21 22:09:47', NULL, '@@', '0123456789', 0, NULL),
(12, 'Hai Phong', '2022-10-21 22:12:13', NULL, '@@', '0123456789', 0, NULL),
(13, 'Hai Phong', '2022-10-21 22:12:13', NULL, '@@', '0123456789', 0, NULL),
(14, 'Hai Phong', '2022-10-21 22:17:10', NULL, '@@', '0123456789', 0, NULL),
(15, 'ha noi', '2022-10-21 22:18:06', NULL, '@@', '0123456789', 0, NULL),
(16, 'Hai Phong', '2022-10-21 22:19:20', NULL, '@@', '0123456789', 0, NULL),
(17, 'Hai Phong', '2022-10-21 22:21:04', NULL, '@@', '0123456789', 3, NULL),
(18, 'Hai Phong', '2022-10-21 22:23:53', 1, '123', '0123456789', 3, NULL),
(19, 'Hai Phong', '2022-10-22 12:46:57', 3, '', '0123456789', 0, NULL),
(20, 'Hai Phong', '2022-10-23 22:10:39', 3, '@@', '0123456789', 0, NULL),
(21, 'Hai Phong', '2022-10-25 16:19:39', 5, '', '0123456789', 0, NULL),
(22, 'Hai Phong', '2022-10-26 11:08:50', 3, '@@', '0123456789', 0, NULL),
(23, 'ha noi', '2022-10-26 11:09:52', 3, '', '0123456789', 0, NULL),
(24, 'Hai Phong', '2022-10-26 11:11:22', NULL, '', '0123456789', 1, NULL),
(25, 'Hai Phong', '2022-10-26 20:41:06', 2, 'Vui lòng chơ', '0123456789', 0, 5),
(26, 'Hai Phong', '2022-10-30 16:09:38', 5, '', '0123456789', 0, NULL),
(27, 'Hai Duong', '2022-10-30 17:04:22', NULL, '@@', '0123456789', 0, 5),
(28, '123', '2022-10-30 17:19:18', 5, '', '0123456789', 0, NULL),
(29, '123', '2022-10-30 17:25:51', 5, '', '0123456789', 1, NULL),
(30, 'Hà Nội', '2022-10-30 20:30:05', NULL, '', '0123456789', 1, 5);

-- --------------------------------------------------------

--
-- Table structure for table `order_details`
--

CREATE TABLE `order_details` (
  `id` int(11) NOT NULL,
  `price` decimal(19,2) NOT NULL,
  `quantity` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `order_details`
--

INSERT INTO `order_details` (`id`, `price`, `quantity`, `order_id`, `product_id`) VALUES
(4, '100000.00', 1, 17, 2),
(5, '100000.00', 3, 17, 4),
(6, '100000.00', 3, 17, 8),
(7, '6000.24', 2, 18, 16),
(8, '300000.00', 3, 18, 2),
(9, '200000.00', 2, 18, 8),
(10, '100000.00', 1, 19, 3),
(20, '100000.00', 1, 23, 4),
(21, '100000.00', 1, 23, 7),
(23, '200000.00', 2, 25, 10),
(27, '3000.12', 1, 8, 5),
(28, '100000.00', 1, 21, 4),
(29, '800000.00', 4, 25, 4),
(30, '800000.00', 4, 25, 3),
(31, '3000.12', 1, 25, 5),
(32, '100000.00', 1, 26, 3),
(33, '100000.00', 1, 26, 4),
(34, '100000.00', 1, 27, 4),
(35, '3000.12', 1, 27, 5),
(37, '100000.00', 1, 28, 3),
(38, '200000.00', 2, 29, 1),
(39, '100000.00', 1, 29, 3);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `available` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `price` decimal(19,2) NOT NULL,
  `quantity` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `available`, `created_date`, `image`, `name`, `price`, `quantity`, `category_id`) VALUES
(1, 0, '2022-10-12 22:39:40', '98299d15-270b-499c-a9c4-454e84ecb5f927-10-2022-08-19-tulanh.jpg', 'Tủ lạnh toshiba', '100000.00', 8, 2),
(2, 1, '2022-10-12 23:21:38', '351ed08a-91a1-47b7-9918-531d8eec407727-10-2022-08-24-10048751smarttiviqledsamsung4k65inchqa65q70aakxxv1rcj5sk.jpg', 'Tivi Samsung', '100000.00', 100, 1),
(3, 1, '2022-10-12 23:21:56', '11e923ee-37d0-43cc-9b1f-e80a1507d7ce27-10-2022-08-26-panasoniccspu12xkh8m21.jpg', 'Điều hòa Toshiba ', '100000.00', 0, 3),
(4, 1, '2022-10-12 23:22:15', 'dcc52ade-e248-4751-854d-29ab12c7d3a927-10-2022-08-29-sharpsjx176esl1org.jpg', 'Tủ Lạnh Inverter', '100000.00', 2, 2),
(5, 1, '2022-10-13 21:40:21', 'cc9d2bdb-714d-4f3f-b13d-338b24eb3fdd27-10-2022-08-27-panasoniccspu12xkh8m31.jpg', 'Inverton 120 Lít', '3000.12', 1, 3),
(6, 0, '2022-10-13 21:45:44', '4f808a83-f8de-4786-b706-5303e759071527-10-2022-08-29-10048865androidtivioledsony4k55inchxr55a90jvn31.jpg', 'toshiba shiba', '3000.12', 100, 1),
(7, 1, '2022-10-13 22:09:52', '0d99893a-f9d2-40ea-a82c-61c61ad9c10127-10-2022-08-31-10048865androidtivioledsony4k55inchxr55a90jvn31.jpg', 'toshiba shiba', '100000.00', 11, 1),
(8, 1, '2022-10-13 22:51:36', 'bc688492-d338-41ce-b5b0-ed9bcae29dce27-10-2022-08-32-10052134smarttivisamsungcrystaluhd4k55inchua55au7002kxxv2.jpg', 'toshiba shiba', '100000.00', 1, 1),
(9, 1, '2022-10-13 23:15:01', '48b664ac-d3e0-4166-bd70-6d773882258227-10-2022-08-35-GalaxyS22PlusWhite600x600.jpg', 'Điện thoại Samsung', '100000.00', 100, 5),
(10, 1, '2022-10-17 19:55:50', '020525c4-aad4-4a8c-b950-1c6731ecab1b27-10-2022-08-36-iphone13prographite600x600.jpg', 'Điện thoại Iphone', '100000.00', 158, 1),
(11, 1, '2022-10-17 19:59:42', 'ac39025c-fc16-446e-a5ab-571a1b93bf5727-10-2022-08-37-dell.png', 'Laptop Dell', '100000.00', 10, 4),
(12, 1, '2022-10-17 20:06:23', '7ba5bb4a-25c7-4a93-902a-adcc036830cd27-10-2022-08-37-shopping.png', 'Laptop Mac', '100000.00', 100, 4),
(13, 1, '2022-10-17 20:25:24', '98987f58-28cc-4a6f-886e-d7b7466b06f527-10-2022-08-38-maygiatsamsum.png', 'Inverton 120 Lít', '121212.00', 122, 6),
(14, 1, '2022-10-17 20:27:47', '5c1c7218-5333-41b7-aad3-803d756e071027-10-2022-08-38-maygiat.png', 'Máy giặt LG', '1212.00', 158, 6),
(15, 1, '2022-10-17 20:32:45', '0d83b830-5dce-4e65-9ba5-377e7a92f6d027-10-2022-08-39-bi3n5d.png', 'Máy giặt panasonic', '110000.00', 111, 6),
(16, 1, '2022-10-17 20:42:28', 'e558684f-9de0-4615-a6ab-32f5d0a4bf4427-10-2022-08-39-heydxet.png', 'Máy giặt samsung', '3000.12', 100, 6);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfdghiqofe8gwujnn3kath52os` (`user_id`),
  ADD KEY `FK4v8tx11ymgtn0672lfvv4pgvv` (`create_by`);

--
-- Indexes for table `order_details`
--
ALTER TABLE `order_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`),
  ADD KEY `FK4q98utpd73imf4yhttm3w0eax` (`product_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `order_details`
--
ALTER TABLE `order_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FK4v8tx11ymgtn0672lfvv4pgvv` FOREIGN KEY (`create_by`) REFERENCES `accounts` (`id`),
  ADD CONSTRAINT `FKfdghiqofe8gwujnn3kath52os` FOREIGN KEY (`user_id`) REFERENCES `accounts` (`id`);

--
-- Constraints for table `order_details`
--
ALTER TABLE `order_details`
  ADD CONSTRAINT `FK4q98utpd73imf4yhttm3w0eax` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  ADD CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
