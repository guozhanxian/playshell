# Run Hadoop Cluster within Docker Containers
1. [hadoop-cluster-docker](https://github.com/kiwenlau/hadoop-cluster-docker)
2. 获取镜像并执行。
```
# 获取镜像
docker pull kiwenlau/hadoop:1.0
# 获得脚本
git clone https://github.com/kiwenlau/hadoop-cluster-docker
# 创建网络
docker network create --driver=bridge hadoop
```
3. 修改启动脚本
```
cd hadoop-cluster-docker
vi start-container.sh
# 加入端口
-p 9000:9000 \
```
4. 启动docker容器
```
sudo ./start-container.sh
```
5. 启动hadoop
```
./start-hadoop.sh
```
---
# SpringBoot连接Hadoop示例
1. 创建SpringBoot项目，导入hadoop依赖
```xml
        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-common</artifactId>
            <version>3.1.1</version>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-hdfs</artifactId>
            <version>3.1.1</version>
        </dependency>
        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-client</artifactId>
            <version>3.1.1</version>
        </dependency>
```
2. 配置SpringBoot配置文件，application.yml
```yaml
spring:
  application:
    name: hadoop
server:
  port: 9000

hdfs:
  path: hdfs://172.17.0.40:9000
  username: root
```
3. 编写Hadoop连接工具类
```java
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;

@Component
public class HadoopUtil {
    @Value("${hdfs.path}")
    private String path;
    @Value("${hdfs.username}")
    private String username;

    private static String hdfsPath;
    private static String hdfsName;

    @PostConstruct
    public void getPath() {
        hdfsPath = this.path;
    }
    @PostConstruct
    public void getName() {
        hdfsName = this.username;
    }

    public static String getHdfsPath() {
        return hdfsPath;
    }

    public String getUsername() {
        return username;
    }

    private static Configuration getConfiguration() {

        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", hdfsPath);
        return configuration;
    }

    public static FileSystem getFileSystem() throws Exception {
        FileSystem fileSystem = FileSystem.get(new URI(hdfsPath), getConfiguration(), hdfsName);
        return fileSystem;
    }
```
5. 编写控制器
```java
import com.ralph.bdatademo.util.HadoopUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hadoop")
public class HadoopController {

    @PostMapping("/mkdir")
    public Map<String,Object> mkdir(@RequestParam("path") String path) throws Exception {
        Map<String,Object> map = new HashMap<>();

        if (StringUtils.isEmpty(path)) {
            map.put("resCode","404");
            map.put("resString","请求参数为空");
            return map;
        }
        // 文件对象
        FileSystem fs = HadoopUtil.getFileSystem();
        // 目标路径
        Path newPath = new Path(path);
        // 创建空文件夹
        boolean isOk = fs.mkdirs(newPath);
        fs.close();
        if (isOk) {
            map.put("resCode","200");
            map.put("resString","文件夹创建成功！");
            return map;
        } else {
            map.put("resCode","500");
            map.put("resString","文件夹创建失败！");
            return map;
        }
    }
}
```
6. SpringBoot启动类
```java
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class BdatademoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BdatademoApplication.class, args);
    }

}
```