drop TABLE goods_list;

CREATE TABLE `goods_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `goodsName` varchar(128) NOT NULL COMMENT '商品名称',
  `inventory_count` int(11) NOT NULL COMMENT '库存数量',
  `start_time` datetime NOT NULL COMMENT '秒杀开始时间',
  `end_time` datetime NOT NULL COMMENT '秒杀结束时间',
  `create_time` datetime NOT NULL COMMENT '商品创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;