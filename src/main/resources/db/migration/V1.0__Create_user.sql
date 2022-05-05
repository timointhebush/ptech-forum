CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(255) NOT NULL,
    `encrypted_password` VARCHAR(255) NOT NULL,
    `username` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UNIQUE_EMAIL` (`email` ASC) VISIBLE,
    UNIQUE INDEX `UNIQUE_USERNAME` (`username` ASC) VISIBLE)