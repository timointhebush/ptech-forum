CREATE TABLE IF NOT EXISTS `role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `member_id` BIGINT NOT NULL,
    `author` VARCHAR(45) NOT NULL,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `fk_role_member_idx` (`member_id` ASC) VISIBLE,
    CONSTRAINT `fk_role_user`
      FOREIGN KEY (`member_id`)
          REFERENCES `member` (`id`)
          ON DELETE CASCADE)