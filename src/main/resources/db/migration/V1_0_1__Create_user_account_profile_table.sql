CREATE TABLE `user_account_profile` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `birth_date_str` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
