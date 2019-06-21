1. 编写微服务后，需要使用Docker运行程序，编写Makefile的时候，使用命令`GOOS=linux GOARCH=amd64 go build`，遇到了`standard_init_linux.go:211: exec user process caused "no such file or direct`这样的错误。修改后，使用下面的配置方法。
```
build:
# 一定要注意 Makefile 中的缩进，否则 make build 可能报错 Nothing to be done for build
# protoc 命令前边是一个 Tab，不是四个或八个空格
	protoc -I. --go_out=plugins=grpc:$(GOPATH)/src/shippy/consignment-service proto/consignment/consignment.proto
	# 告知 Go 编译器生成二进制文件的目标环境：amd64 CPU 的 Linux 系统 GOARCH=linux
	CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo
	# 根据当前目录下的 Dockerfile 生成名为 consignment-service 的镜像
	docker build -t consignment-service .
run:
  # 在 Docker alpine 容器的 50001 端口上运行 consignment-service 服务
  # 可添加 -d 参数将微服务放到后台运行
	docker run -p 50051:50051 consignment-service
```
2. 当时使用`go get -u`安装包的时候，会出现对于`golang.org`下的包无法下载的网络问题。对于被墙的问题，一般两种解决方法，使用代理。现在golang提供了代理参数。使用网站[https://goproxy.io/](https://goproxy.io/)可以设置代理。
```
# Enable the go modules feature
export GO111MODULE=on
# Set the GOPROXY environment variable
export GOPROXY=https://goproxy.io
```

3. 我的`/etc/profile`中的PATH设置。
```
export JAVA_HOME=/usr/local/java/jdk1.8.0_211
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH

export NODE_HOME=/usr/src/node-v12.3.1
export PATH=$PATH:$NODE_HOME/bin
export NODE_PATH=$NODE_HOME/lib/node_modules

export PATH=$PATH:/root

export GOROOT=/opt/app/go
export GOBIN=$GOROOT/bin
export PATH=$PATH:$GOBIN
export GOPATH=/root/go
export PATH=$PATH:$GOPATH/bin
export GO112MODULE=on
export GOPROXY=https://goproxy.io

export SPRING_HOME=/root/spring-2.2.0-cli
export PATH=$SPRING_HOME/bin:$PATH
```
