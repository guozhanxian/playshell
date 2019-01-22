# docker安装jenkins
---
1. 获取镜像
```
docker pull jenkins
```
2. 运行镜像
```
# 为了保存运行时的文件，讲volumn映射到宿主文件夹中
docker run -d -p 8080:8080 -p 50000:50000 -v $(pwd)/data:/var/jenkins_home --name jenkins jenkins
```
3. 处理错误
> 运行的时候出错，原因是jenkins用户没有对data文件夹的访问权限。通过命令修改data文件夹的使用权限
```
chown -R 1000 ~/data
```
4. 重新启动容器
```
docker start jenkins
```
5. 通过浏览器访问。注意，启动比较慢。
6. 通过Dockerfile文件构建自己的docker镜像。[可以参考这个github上的项目](https://github.com/AliyunContainerService/docker-jenkins)和[这个博客](https://blog.csdn.net/babys/article/details/71170254)
```
git clone https://github.com/AliyunContainerService/docker-jenkins
cd docker-jenkins/jenkins
docker build -t denverdino/jenkins .

# 运行自己的容器
docker rm -f jenkins
docker run -d -p 8080:8080 -p 50000:50000 -v $(pwd)/data:/var/jenkins_home --name jenkins denverdino/jenkins
```