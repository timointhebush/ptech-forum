CREATE TABLE IF NOT EXISTS `comment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `content` TEXT NOT NULL,
    `post_id` BIGINT NOT NULL,
    `member_id` BIGINT NOT NULL,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `fk_comments_posts_idx` (`post_id` ASC) VISIBLE,
    INDEX `fk_comment_member_idx` (`member_id` ASC) VISIBLE,
    CONSTRAINT `fk_comments_posts`
     FOREIGN KEY (`post_id`)
         REFERENCES `post` (`id`)
         ON DELETE CASCADE,
    CONSTRAINT `fk_comment_member`
     FOREIGN KEY (`member_id`)
         REFERENCES `member` (`id`)
         ON DELETE CASCADE)