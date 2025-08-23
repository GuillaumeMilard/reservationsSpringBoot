CREATE TABLE `shows` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `location_id` BIGINT UNSIGNED DEFAULT NULL,
  `slug` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL UNIQUE,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` longtext COLLATE utf8mb4_unicode_ci,
  `poster_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bookable` tinyint(1) NOT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET= utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Contrainte d’unicité sur slug
ALTER TABLE `shows`
  ADD CONSTRAINT `uk_shows_slug` UNIQUE (`slug`);

-- Index lisible sur location_id
ALTER TABLE `shows`
  ADD KEY `idx_shows_location` (`location_id`);

-- Clé étrangère vers locations
ALTER TABLE `shows`
  ADD CONSTRAINT `fk_shows_location`
  FOREIGN KEY (`location_id`)
  REFERENCES `locations` (`id`)
  ON DELETE SET NULL;

