CREATE TABLE IF NOT EXISTS `post` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255) NOT NULL,
    `content` LONGTEXT NOT NULL,
    `member_id` BIGINT NOT NULL,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `fk_post_member_idx` (`member_id` ASC) VISIBLE,
    CONSTRAINT `fk_post_user`
      FOREIGN KEY (`member_id`)
          REFERENCES `member` (`id`)
          ON DELETE CASCADE)