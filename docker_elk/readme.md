# elk-docker
1. [elk-docker官方网址](https://elk-docker.readthedocs.io/)
2. 安装需要的环境
   - Docker。win下需要Linux容器。
   - 分配给docker的内存最小4G
   - mmap至少为262144或更大。使用命令`sysctl vm.max_map_count`查看。否则需要使用`root`用户执行下面脚本。如果要永久改变该值，需要修改`/etc/sysctl.conf`文件中的变量。
```
    sysctl -w vm.max_map_count=262144
```
3. 获取镜像，并执行。脚本如下：
```
$ sudo docker pull sebp/elk
$ sudo docker run -p 5601:5601 -p 9200:9200 -p 5044:5044 -it --name elk sebp/elk
```
4. 端口信息。
```
5601 (Kibana Web程序端口)
9200 (Elasticsearch JSON端口)
5044 (Logstash Beats工具接收日志传输接口)
```
5. docker-compose脚本
[docker-compose.yml](./docker-compose.yml)。运行docker-compose脚本如下：
```
$ sudo docker-compose up elk
```