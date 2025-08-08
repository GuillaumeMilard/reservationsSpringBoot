CREATE TABLE `localities` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `postal_code` varchar(6) COLLATE utf8mb4_unicode_ci NOT NULL UNIQUE,
  `name` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL UNIQUE,
  PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
