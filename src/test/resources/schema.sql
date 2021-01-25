CREATE TABLE IF NOT EXISTS `user` (
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `followdetails` (
  `id` int NOT NULL AUTO_INCREMENT,
  `followee_id` varchar(255) DEFAULT NULL,
  `follower_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `newsfeed` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  `newsfeed_owner_id` varchar(255) DEFAULT NULL,
  `post_id` varchar(255) DEFAULT NULL,
  `post_originator_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

