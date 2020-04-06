CREATE TABLE `t_weather` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city_code` int(11) NOT NULL DEFAULT '0',
  `date_time` bigint(20) NOT NULL DEFAULT '0',
  `main_temp` double NOT NULL DEFAULT '0',
  `main_temp_min` double NOT NULL DEFAULT '0',
  `main_temp_max` double NOT NULL DEFAULT '0',
  `main_pressure` double NOT NULL DEFAULT '0',
  `humidity` double NOT NULL DEFAULT '0',
  `description` varchar(64) NOT NULL DEFAULT '',
  `date_time_txt` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;