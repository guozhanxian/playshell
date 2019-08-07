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
export GOPATH=/root/go

export GO112MODULE=on
export GO111MODULE=on
export GOPROXY=https://goproxy.io

export GOBIN=$GOPATH/bin
export PATH=$PATH:$GOBIN:$GOROOT/bin

export SPRING_HOME=/root/spring-2.2.0-cli
export PATH=$SPRING_HOME/bin:$PATH

export NODE_HOME=/usr/local/node
export PATH=$NODE_HOME/bin:$PATH

MAVEN_HOME=/usr/local/maven/apache-maven-3.5.4
PATH=$MAVEN_HOME/bin:$PATH
export MAVEN_HOME PATH

M2_HOME=/usr/local/maven/apache-maven-3.5.4
export M2_HOME

export SVN_EDITOR=vi


export SONAR_SCANNER_HOME=/opt/sonar-scanner-3.4.0
export PATH=${SONAR_SCANNER_HOME}/bin:$PATH
```