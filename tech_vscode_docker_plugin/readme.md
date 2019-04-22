1. 在服务器上开启Docker远程访问。Linux下编辑`vim /usr/lib/systemd/system/docker.service`。 如下是我的配置文件，需要添加的部分是在第12行：`ExecStart=/usr/bin/dockerd`的后面加上`-H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock`，ok之后 `:wq` ,保存并退出。
```
[Unit]
Description=Docker Application Container Engine
Documentation=https://docs.docker.com
After=network-online.target firewalld.service
Wants=network-online.target
 
[Service]
Type=notify
# the default is not to use systemd for cgroups because the delegate issues still
# exists and systemd currently does not support the cgroup feature set required
# for containers run by docker
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock #add
ExecReload=/bin/kill -s HUP $MAINPID
# Having non-zero Limit*s causes performance problems due to accounting overhead
# in the kernel. We recommend using cgroups to do container-local accounting.
LimitNOFILE=infinity
LimitNPROC=infinity
LimitCORE=infinity
# Uncomment TasksMax if your systemd version supports it.
# Only systemd 226 and above support this version.
#TasksMax=infinity
TimeoutStartSec=0
# set delegate yes so that systemd does not reset the cgroups of docker containers
Delegate=yes
# kill only the docker process, not all processes in the cgroup
KillMode=process
# restart the docker process if it exits prematurely
Restart=on-failure
StartLimitBurst=3
StartLimitInterval=60s
 
[Install]
WantedBy=multi-user.target
```
2. 重启docker的守护进程，重启docker。命令如下：
```
systemctl daemon-reload
systemctl start docker 
```
3. 在本机Win10上，配置系统环境变量，添加环境变量`DOCKER_HOST=tcp://远程Docker机器的ip:2375`
4. 在VSCode中，在设置-->用户设置-->Docker Configuration菜单下，把Docker Host设置为：`http://远程Docker机器的ip:2375`。这样就可以在VSCode中管理远程的Docker容器了。
5. 修改SpringBoot项目的pom.xml文件，添加如下插件：
```xml
<build>
    <plugins>
		<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
      <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>1.0.0</version>
                <configuration>
                    <imageName>airtimes/${project.name}</imageName>
                    <baseImage>java</baseImage>
                    <entryPoint>["java","-jar","/${project.build.finalName}.jar}"]</entryPoint>
                    <dockerHost>http://172.17.0.42:2375</dockerHost>
                    <resources>
                        <resource>
                            <targetPath>/</targetPath>
                            <directory>${project.build.directory}</directory>
                            <include>${project.build.finalName}.jar</include>
                        </resource>
                    </resources>
                </configuration>
            </plugin>
      </plugins>
	</build>
```
6. 执行Maven命令，打包应用程序，并推送到远程的Docker服务器上。
```
mvn clean package docker:build -DskipTests
```
