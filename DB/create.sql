create database cycling_club;
use cycling_club;

CREATE TABLE IF NOT EXISTS `User` (
  `user_id`    BIGINT NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(100),
  `email`      VARCHAR(100) UNIQUE,
  `password`   VARCHAR(100),
  `phone`      VARCHAR(20),
  `sex`        VARCHAR(100),
  `birth_date` VARCHAR(100),
  `created`    DATETIME,
  PRIMARY KEY (`user_id`)
)
  ENGINE MyISAM
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;


CREATE TABLE IF NOT EXISTS `Event` (
  `event_id`       BIGINT NOT NULL AUTO_INCREMENT,
  `name`           VARCHAR(100),
  `status`         INT,
  `start_date`     DATETIME,
  `end_date`       DATETIME,
  `route_img`      VARCHAR(255),
  `owner_id`       BIGINT,
  `emergency_flag` INT,
  `emergency_info` NVARCHAR(1000),
  `created`        DATETIME,
  PRIMARY KEY (`event_id`)
)
  ENGINE MyISAM
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS `Event_member` (
  `event_id` BIGINT,
  `user_id`  BIGINT,
  PRIMARY KEY (`event_id`, `user_id`)
)
  ENGINE MyISAM
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS `Event_route` (
  `event_id`       BIGINT,
  `start_position` VARCHAR(255),
  `end_position`   VARCHAR(255),
  `status`         INT,
  `priority`       INT,
  `duration`       INT
)
  ENGINE MyISAM
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;
