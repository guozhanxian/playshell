# dubbo-admin docker 镜像
1. [dubbo-admin官方地址](https://github.com/chenchuxin/dubbo-admin-docker)
2. 获取镜像
```
docker pull chenchuxin/dubbo-admin
```
3. 运行镜像
```
docker run -d -p 8080:8080 -e dubbo.registry.address=zookeeper://172.17.0.42:2181 -e dubbo.admin.root.password=root -e dubbo.admin.guest.password=guest chenchuxin/dubbo-admin
```
4. 登录`http://ip:port`访问，用户名密码是：`root`:`root`和`guest`:`guest`