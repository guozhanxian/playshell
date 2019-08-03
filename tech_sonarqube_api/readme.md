1. 近来在项目中需要调用SonarQube接口来获取分析相关的信息。通过实验，测试了创建项目、删除项目和查询统计指标相关的Api的例子。其中创建和查询可以通过认证的token就可以完成认证，但是调用删除项目接口的时候，需要管理员的权限，那么就要使用用户名和密码的认证。例子代码如下：
`用来发送Post请求到SonarQube，创建一个项目`
```java
    @Test
    public void testCreateProject() {
        String addUrl = "http://172.17.0.43:9000/api/projects/create?name=test&project=test";
        String token = "de2c49e6d597b2c10da20e74f6d5870e2997a7b0";
        try {
            String res = HttpRequest.post(addUrl).header(token, "").execute().body();
            log.info("<<<<创建项目成功>>>>>" + res);
        } catch (Exception e) {
            log.error("创建项目失败！", e);
        }

    }
```
`查询整个项目的统计信息`
```java
    @Test
    public void testFindComponent() {
        String url = "http://172.17.0.43:9000/api/measures/component?metricKeys=ncloc,complexity,violations,lines,comment_lines,comment_lines_density&component=cboard";
        String token = "de2c49e6d597b2c10da20e74f6d5870e2997a7b0";
        try {
            String res = HttpRequest.get(url).header(token, "").execute().body();
            log.info("<<<<查询统计信息成功>>>>>" + res);
        } catch (Exception e) {
            log.error("查询统计信息失败！", e);
        }
    }

    @Test
    public void testFindFileStatInfo() {
        String url = "http://172.17.0.43:9000/api/measures/component_tree?metricKeys=ncloc,complexity,violations,lines,comment_lines,comment_lines_density&component=cboard&qualifiers=FIL&q=AdminSerivce.java";
        String token = "de2c49e6d597b2c10da20e74f6d5870e2997a7b0";
        try {
            String res = HttpRequest.get(url).header(token, "").execute().body();
            log.info("<<<<查询单个文件统计信息成功>>>>>" + res);
        } catch (Exception e) {
            log.error("查询单个文件统计信息失败！", e);
        }
    }
```
2. 在调用删除接口的时候，需要进行管理员权限的验证。（这个在官方文档中，只用了一句话进行描述。并没有给出具体的例子，实验了多次，才确定可用的代码）。
```java
    @Test
    public void testDeleteProject() {
        String delUrl = "http://172.17.0.43:9000/api/projects/delete?name=test&project=test&username=admin&password=root123";
        String code = Base64.encode("admin:root123".getBytes());
        try {
            String res = HttpRequest.post(delUrl).header("Authorization", "Basic " + code).execute().body();
            log.info("<<<<删除项目成功>>>>>" + res);
        } catch (Exception e) {
            log.error("删除项目失败！", e);
        }
    }
```