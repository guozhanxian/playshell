# 使用Dubbo开发微服务
1. 使用Docker安装zookeeper。[参考zookeeper镜像](../docker_zookeeper/readme.md)
2. 使用Docker安装dubbo-admin。[参考docker-dubbo-admin镜像](../docker_dubbo_admin/readme.md)
3. 创建父模板项目，maven导入相关依赖。
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.ralph</groupId>
    <artifactId>dubbo-parent</artifactId>
    <packaging>pom</packaging>
    <version>1.0-SNAPSHOT</version>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.1.RELEASE</version>
    </parent>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <java.version>1.8</java.version>
    </properties>
    <modules>
        <module>dubbo-consumer</module>
        <module>dubbo-provider</module>
        <module>dubbo-api</module>
    </modules>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j</artifactId>
            <version>1.3.8.RELEASE</version>
        </dependency>
        <!-- Dubbo -->
        <dependency>
            <groupId>io.dubbo.springboot</groupId>
            <artifactId>spring-boot-starter-dubbo</artifactId>
            <version>1.0.0</version>
        </dependency>
        <!-- Junit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
    </dependencies>
</project>
```
4. 创建接口项目`dubbo-api`，在其中创建服务接口。
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>dubbo-parent</artifactId>
        <groupId>com.ralph</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>dubbo-api</artifactId>
</project>
```
```java
public interface DubboService {
    public void sayHello(String name);
}
```
5. 创建服务提供者项目`dubbo-provider`，编写相关配置和程序。
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>dubbo-parent</artifactId>
        <groupId>com.ralph</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>dubbo-provider</artifactId>
    <dependencies>
        <dependency>
            <groupId>com.ralph</groupId>
            <artifactId>dubbo-api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```
```
spring.dubbo.application.name=dubbo-provider
spring.dubbo.registry.address=zookeeper://172.17.0.42:2181
spring.dubbo.protocol.name=dubbo
spring.dubbo.protocol.port=20880
spring.dubbo.scan=com.ralph.web.servive
```
```java
@Service(version = "1.0.0")
@Component
public class DubboServiceImpl implements DubboService{
    @Override
    public void sayHello(String name) {
        System.out.println(name);
        System.out.println("我是服务，我已经被执行了！");
    }
}
```

6. 创建服务消费者项目`dubbo-consumer`，编写配置文件和程序。
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>dubbo-parent</artifactId>
        <groupId>com.ralph</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>dubbo-consumer</artifactId>
    <dependencies>
        <dependency>
            <groupId>com.ralph</groupId>
            <artifactId>dubbo-api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

```
server.port=9999
#应用名称
spring.dubbo.application.name=dubbo-consumer
#注册中心地址
spring.dubbo.registry.address=zookeeper://172.17.0.42:2181
spring.dubbo.scan=com.ralph.web
```

```java
@RestController
@RequestMapping("/dubbo")
public class DubboController {
    @Reference(version = "1.0.0")
    DubboService dubboService;

    @RequestMapping("sayHello")
    public void sayHello() {
        System.out.println("开始调用其他微服务");
        dubboService.sayHello("我是服务A，我要调用你了~");
    }
}
```