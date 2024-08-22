-- basquiat.`member` definition

-- name으로 검색할 수 있으니 index를 건다.
-- nick_name은 unique로 잡는다.

CREATE TABLE `member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `nick_name` varchar(100) NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `member_nick_name_IDX` (`nick_name`) USING BTREE,
  KEY `member_name_IDX` (`name`) USING BTREE
) ENGINE=InnoDB;