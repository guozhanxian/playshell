# docker安装SonarQube
---
1. 获取镜像
```
docker pull postgres
docker pull sonarqube
```
2. 拷贝中文插件。SonarQube中文插件下载地址：[https://github.com/SonarQubeCommunity/sonar-l10n-zh](https://github.com/SonarQubeCommunity/sonar-l10n-zh)

|  SonarQube | 7.0  | 7.1 | 7.2 | 7.3 | 7.4 | 7.5 |
| ------ | ------ | ------ | ------ | ------ | ------ |------ |
|  sonarl10nzh | 1.20  | 1.21 | 1.22 | 1.23 | 1.24 | 1.25 |
3. 制作Dockerfile文件
```
FROM sonarqube
ADD sonar-l10n-zh-plugin-1.25.jar /opt/sonarqube/extensions/plugins/
```
4. 构建镜像
```
docker build -t sq:zh .
```
5. 启动镜像
```
docker run --name db -e POSTGRES_USER=sonar -e POSTGRES_PASSWORD=sonar -d postgres
docker run --name sq --link db -e SONARQUBE_JDBC_URL=jdbc:postgresql://db:5432/sonar -p 9000:9000 -d sq:zh
```