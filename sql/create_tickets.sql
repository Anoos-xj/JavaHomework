CREATE DATABASE IF NOT EXISTS `train_tickets` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `train_tickets`;

CREATE TABLE IF NOT EXISTS `tickets` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `id_card` VARCHAR(32) NOT NULL,
  `start_city` VARCHAR(64) NOT NULL,
  `end_city` VARCHAR(64) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX `idx_tickets_idcard` ON `tickets`(`id_card`);
