CREATE TABLE `seckill_goods_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `goods_name` varchar(128) NOT NULL COMMENT '商品名称',
  `inventory_count` int(11) NOT NULL COMMENT '库存数量',
  `start_time` datetime NOT NULL COMMENT '秒杀开始时间',
  `end_time` datetime NOT NULL COMMENT '秒杀结束时间',
  `create_time` datetime NOT NULL COMMENT '商品创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO `seckill_goods_list` VALUES (1, '1000元秒杀iphonex', 100, '2018-9-14 13:00:00', '2018-9-21 12:33:35', '2018-9-3 10:00:00');
INSERT INTO `seckill_goods_list` VALUES (2, '100元秒杀ipad', 10, '2018-9-20 14:00:00', '2018-9-21 00:00:00', '2018-9-3 10:00:00');
INSERT INTO `seckill_goods_list` VALUES (3, '1000元秒杀imax', 10, '2018-9-20 15:00:00', '2018-9-21 00:00:00', '2018-9-3 10:00:00');


CREATE TABLE `seckill_success` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(20) NOT NULL,
  `user_name` varchar(128) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name_id` (`goods_id`,`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `mobile_no` varchar(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_name` (`user_name`),
  UNIQUE KEY `uk_mobile_no` (`mobile_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `users` VALUES (1, 'test1', 'test1', '13211111111');