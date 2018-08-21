CREATE TABLE `role_info` (
`id`  bigint(20) NOT NULL ,
`user_id`  bigint(20) NOT NULL ,
`nickname`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`icon`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`sex`  tinyint(4) NOT NULL ,
`level`  int(11) NOT NULL DEFAULT 0 ,
`create_time`  bigint(20) NOT NULL ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;