CREATE TABLE `artist_type` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `artist_id` BIGINT UNSIGNED NOT NULL,
  `type_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Index pour la table `artist_type`
--
ALTER TABLE `artist_type`
  ADD KEY `artist_type_artist_id_IDX_3060D1B6B7970CF8_fk_artists_id` (`artist_id`);

ALTER TABLE `artist_type`
  ADD KEY `artist_type_type_id_IDX_3060D1B6C54C8C93_fk_types_id` (`type_id`);

--
-- Contraintes pour la table `artist_type`
--
ALTER TABLE `artist_type`
  ADD CONSTRAINT `artist_type_artist_id_3060D1B6B7970CF8_fk_artists_id`
  FOREIGN KEY (`artist_id`) REFERENCES `artists` (`id`)
  ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE `artist_type`
  ADD CONSTRAINT `artist_type_type_id_3060D1B6C54C8C93_fk_types_id`
  FOREIGN KEY (`type_id`) REFERENCES `types` (`id`)
  ON UPDATE CASCADE ON DELETE RESTRICT;
