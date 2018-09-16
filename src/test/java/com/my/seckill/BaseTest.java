package com.my.seckill;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations={"classpath:applicationContext.xml","classpath:datasource.xml",
"classpath:spring-redis.xml","classpath:mybatis-config.xml"}) //加载配置文件
public class BaseTest {
}
