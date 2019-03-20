# 本地搭建[thingsborad](https://github.com/thingsboard/thingsboard)的时候遇到的坑
1. 编译项目的时候需要使用`gradle-2.13-bin`，国内访问gradle下载很慢，请用下载工具下载后，拷贝到`C:\Users\Administrator\.gradle\wrapper\dists\gradle-2.13-bin`文件夹中
2. 本地编译的时候，请参考官网的[Contribution Guide](https://thingsboard.io/docs/user-guide/contribution/how-to-contribute/)
3. 编译ui模块的时候，会出现错误。主要有两个地方，一个是下载npm出错，一个是下载github上的文件出错。
   1. github本地需要配置加速。修改`hosts`文件
   ```
      #GitHub 访问速度加快
      192.30.255.113 github.com
      #GitHub 下载速度加快
      151.101.185.194 github.global.ssl.fastly.net
   ```
   2. 修改`ui/pom.xml`文件，添加`<downloadRoot>http://npm.taobao.org/mirrors/node/</downloadRoot>`
    ```xml
                    <execution>
                        <id>install node and npm</id>
                        <goals>
                            <goal>install-node-and-npm</goal>
                        </goals>
                        <configuration>
                            <nodeVersion>v6.9.1</nodeVersion>
                            <npmVersion>6.4.1</npmVersion>
							<downloadRoot>http://npm.taobao.org/mirrors/node/</downloadRoot>
                        </configuration>
                    </execution>
    ```
   3. 修改`ui/package.json`文件，将`git:`修改为`https:`
   4. 修改`msa/js-executor/pom.xml`文件，添加`<downloadRoot>http://npm.taobao.org/mirrors/node/</downloadRoot>`

---
# [thingsborad在Google的论坛](https://groups.google.com/forum/#!forum/thingsboard)
---
# 单独部署thingsboard遇到的坑
## 用docker安装postgresql
```
docker run -it --rm -v $PWD/pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
```
## 用docker安装Cassandra
```
# 获取镜像
docker pull cassandra:3
# 运行镜像
docker run -d --name "cassandra_cluster" -e CASSANDRA_BROADCAST_ADDRESS=172.17.0.42 -e CASSANDRA_CLUSTER_NAME="Cassandra-Cluser-v2" -e CASSANDRA_SEEDS="172.17.0.42" -v /data/cassandra-docker-data:/var/lib/cassandra -p 7000:7000 -p 7001:7001 -p 7199:7199 -p 9042:9042 -p 9160:9160 --restart always cassandra:3
```
## 安装Thingsboard
1. [下载JRE环境](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)，命令安装
```
# 安装Linux JRE环境。
yum localinstall jdk-8u161-linux-x64.rpm
# 制作软连接
ln -fs /usr/java/jdk1.8.0_161/jre/bin/java /usr/bin/java
```
2. [下载thingsboard的rpm安装文件](https://github.com/thingsboard/thingsboard/releases/download/v2.3/thingsboard-2.3.rpm)
```
# 安装thingsboard --nodeps的作用是忽略关联检查
rpm -Uvh --nodeps thingsboard-2.3.rpm
```
3. 修改`/etc/thingsboard/conf/thingsboard.yml`配置文件。如果使用postgresql数据库，那么把hqlsdb的配置注释掉。如果使用Cassandra，那么修改下面的配置内容。
```yml
database:
  entities:
    type: "${DATABASE_ENTITIES_TYPE:cassandra}" # cassandra OR sql
  ts:
    type: "${DATABASE_TS_TYPE:cassandra}" # cassandra OR sql (for hybrid mode, only this value should be cassandra)
```
4. 执行安装脚本
```
sudo /usr/share/thingsboard/bin/install/install.sh --loadDemo
```
5. 启动服务
```
sudo service thingsboard start
```
---
# 对thingsboard源码的研究记录
1. 用js编写的MQTT的测试客户端代码。使用命令`node publish.js`执行下面的JS程序。
```javascript
var mqtt = require('mqtt');
var host = '172.17.0.42';
var access_token = 'hX8YsIRuFH8JxbDgF1I4';
console.log('Connecting to: %s using access token: %s', host, access_token);

var client  = mqtt.connect('mqtt://'+ host,{
    username: access_token
});

//用来延时的JS函数
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms))
}

client.on('connect', async function () {
    console.log('Client connected!');
	for(;;)
	{
		var data = parseInt(Math.random()*100);
		msg = '{"temperature":'+ data + '}';
		client.publish('v1/devices/me/telemetry', msg);
		console.log('Telemetry published!' + msg);
		await sleep(1000);
	}
});
```
2. 得
