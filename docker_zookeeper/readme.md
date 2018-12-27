# zookeeper
1. [zookeeper地址](https://github.com/31z4/zookeeper-docker)
2. 获取镜像
```
docker pull zookeeper
```
3. 启动容器
```
docker run --privileged=true -d --name zookeeper -p 2181:2181 -p 2888:2888 -p 3888:3888 -d zookeeper
```