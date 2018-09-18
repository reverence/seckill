# seckill

1，	执行文件sql.sql

2，	安装好redis,这里我使用的版本是3.2.11,安装步骤百度之

3，	redis.conf配置

这里我们主要配置1主2从，拷贝redis.conf至三个新文件: redis17000.conf  redis17001.conf  redis17002.conf,三个redis实例分别使用端口17000,17001,17002,修改的内容分别见redis-conf目录下文件内容

4，	配置sentinel
Sentinel也是一个redis实例，这里我们三个配置文件，有三个哨兵，如果有两个哨兵检测到master挂了，进行切换，三个文件分别是:sentinel27000.conf  sentinel27001.conf , sentinel27002.conf,修改的内容见redis-conf目录下文件内容

5，	分别执行redis-conf目录run.txt中的命令

6，	执行打包命令mvn package -Dmaven.test.skip=true,打出的war包为myseckill.war,拷贝到tomcat webapps目录下

7，	Tomcat server.xml中配置一行<Context docBase="myseckill" path="/"  reloadable="true" ></Context>

8，	Nginx配置，新建缓存目录/home/chengzhang/nginx/cache/webpages,将default.conf文件拷贝到nginx目录/etc/nginx/conf.d/下, 执行命令sudo nginx -c /etc/nginx/nginx.conf启动nginx


9，	启动tomcat,访问地址http://www.myseckill.com/seckill/list






