# 环境搭建
1. Maven私服配置。配置公司内部私服以及阿里Maven私服
2. MySQL驱动版本升级，可以升级到5.1.46版本。
3. 开启中文模式，修改Setting.json中的语言设置。
# 开发内容
1. 开启getBoardData.do的访问权限，可以任意用户访问。（内网部署）
2. 前台现实配置后的图表，需要的步骤。
  a. 通过getBoardData.do?id=2查询看板数据。看板数据包括所有看板的信息。
  b. 将返回的看板信息中的config数据转换成cfg数据（参看dataService.js）
  c. 发送请求到getAggregateData.do获取数据。展示到表格。
# 具体配置
1. 配置跨域访问。修改spring-security-jdbc.xml文件
```
        <security:headers>
            <!--<security:frame-options policy="ALLOW-FROM" strategy="static" value="/render.html**"/>-->
            <security:frame-options disabled="true"/>
        </security:headers>
```
2. 放开getBoardData.do等地址。同样修改spring-security-jdbc.xml文件
```
        <!--放行两个接口-->
        <security:intercept-url pattern="/dashboard/getBoardData.do" access="permitAll"/>
        <security:intercept-url pattern="/dashboard/getAggregateData.do" access="permitAll"/>
        <security:intercept-url pattern="/dashboard/getBoardDataByFreeId.do" access="permitAll"/>
        <!--放行对render.html的访问-->
        <security:intercept-url pattern="/render*" access="permitAll"/>
```
