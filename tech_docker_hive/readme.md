# docker安装hive遇到的坑
---
1. 使用[docker大数据合集中的docker-compose hive安装](https://github.com/big-data-europe/docker-hive)
2. 启动后用客户端根据TXT文件生成表结构。使用RazorSQL客户端生成表结构后，需要添加`ROW format delimited fields terminated by ',' STORED AS TEXTFILE`在语句的末尾。
```sql
create table watermelon (id String,
                         color String,
                         root String,
                         stroke String,
                         venation String,
                         umbilical String,
                         touch String,
                         status String) 
                         ROW format delimited fields terminated by ',' STORED AS TEXTFILE
```
3. rz拷贝TXT数据到宿主机器，通过`docker cp`命令拷贝数据TXT文件到docker容器中。
```
# 576cd09bcf38为容器id
docker cp 5.txt 576cd09bcf38:/opt/hive/examples
```
4. 进入docker-compose创建的容器中，如果使用合集中的hive，那么执行下面的命令。
```
# 进入docker容器bash
docker-compose exec hive-server bash
# 运行hive客户端
/opt/hive/bin/beeline -u jdbc:hive2://localhost:10000
```
5. 把数据导入到hive中。由于数据中有中文直接导入会有乱码，需要设置表的导入编码。
```
# 设置数据入库时候的编码转换
alter table air_soft_opt SET SERDEPROPERTIES ('serialization.encoding'='GBK');
# 导入数据文件到hive
LOAD DATA LOCAL INPATH '/opt/hive/examples/5.txt' OVERWRITE INTO TABLE air_soft_opt;
```
6. RazorSQL客户端工具也需要设置字体为中文字体，否则会显示乱码。点击`Edit-->Preferences-->Fonts`修改字体为中文字体。
