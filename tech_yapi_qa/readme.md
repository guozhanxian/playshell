1. 在安装的过程中遇到`jsonpath@1.0.0 postinstall:node lib/aesprim.js>generated/aesprim_browser.js`的错误。通过查找资料，在[CSDN上找到文章](https://blog.csdn.net/Angry_Mills/article/details/88728085)解决了这个问题。是因为node对于root的权限问题。执行命令，修改node权限
```
chown -R root:root node-v12.3.1
```
2. 安装后，以为直接通过3000端口就可以使用了，其实还需要node启动后才可以。执行下面的命令启动
```
cd ~/my-yapi
node ./venders/server/app.js
```
3. 在springboot项目中，引用swagger，一般使用springfox。下面给出pom的配置模板。
```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger2</artifactId>
      <version>2.9.2</version>
    </dependency>
    <!--需要ui界面的话就配上-->
    <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger-ui</artifactId>
      <version>2.9.2</version>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-configuration-processor</artifactId>
      <optional>true</optional>
    </dependency>
    
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-devtools</artifactId>
      <scope>runtime</scope>
      <optional>true</optional>
    </dependency>
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
```
5. 在程序中，编写`Swagger2类`来配置swagger2。[参考代码](./Swagger2.java)
