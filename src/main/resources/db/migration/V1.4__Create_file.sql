CREATE TABLE IF NOT EXISTS `file` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `post_id` BIGINT NULL,
    `name` VARCHAR(255) NOT NULL,
    `original_name` VARCHAR(255) NULL,
    `size` INT NULL,
    `extension` VARCHAR(10) NULL,
    `relative_path` VARCHAR(255) NOT NULL,
    `sequence` INT NULL,
    `file_type` VARCHAR(45) NULL,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `fk_file_post_idx` (`post_id` ASC) VISIBLE,
    CONSTRAINT `fk_file_post`
        FOREIGN KEY (`post_id`)
            REFERENCES `post` (`id`)
            ON DELETE CASCADE)