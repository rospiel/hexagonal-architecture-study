set foreign_key_checks = 0;

DROP TABLE IF EXISTS `bank_slip_tax`;

CREATE TABLE `bank_slip_tax` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `due_date` date NOT NULL,
  `original_value` decimal(11,2) NOT NULL,
  `payment_date` date NOT NULL,
  `taxes` decimal(11,2) NOT NULL,
  `type` tinyint NOT NULL,
  `updated_value` decimal(6,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


set foreign_key_checks = 1;