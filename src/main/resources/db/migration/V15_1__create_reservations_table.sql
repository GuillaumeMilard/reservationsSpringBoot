CREATE TABLE `reservations` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT UNSIGNED NOT NULL,
  `representation_id` BIGINT UNSIGNED NOT NULL,
  `places` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Index pour la table `reservations`
--
ALTER TABLE `reservations`
  ADD KEY `reservations_user_id` (`user_id`);

ALTER TABLE `reservations`
  ADD KEY `reservations_representation_id` (`representation_id`);

--
-- Contraintes pour la table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `reservations_user_id`
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
  ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE `reservations`
  ADD CONSTRAINT `reservations_representation_id`
  FOREIGN KEY (`representation_id`) REFERENCES `representations` (`id`)
  ON UPDATE CASCADE ON DELETE RESTRICT;
