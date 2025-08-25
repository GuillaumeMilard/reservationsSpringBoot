ALTER TABLE `users`
    ADD COLUMN `updated_at` datetime NULL AFTER `created_at`,
    ADD COLUMN `email_verified_at` datetime NULL AFTER `updated_at`,
    ADD COLUMN `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci NULL AFTER `email_verified_at`;
