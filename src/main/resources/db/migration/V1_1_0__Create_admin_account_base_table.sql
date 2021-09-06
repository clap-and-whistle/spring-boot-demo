CREATE TABLE `admin_account_base` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_status` int NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
