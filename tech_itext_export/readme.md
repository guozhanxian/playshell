# iText根据pdf模板，灌入数据后导出
1. 使用pdf工具创建pdf模板。使用Adobe Acrobat DC中的准备表单，将数据项、图片、表格通过文本域规定位置。以便程序可以将数据灌入对应文本域中。
2. maven导入需要的包。如果需要根据Freemarker导出html后生成pdf，需要导入freemarker相关的包。
```xml
        <dependency>
            <groupId>org.freemarker</groupId>
            <artifactId>freemarker</artifactId>
            <version>2.3.23</version>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>com.itextpdf</groupId>
            <artifactId>itextpdf</artifactId>
            <version>5.4.2</version>
        </dependency>
        <dependency>
            <groupId>com.itextpdf.tool</groupId>
            <artifactId>xmlworker</artifactId>
            <version>5.4.1</version>
        </dependency>
        <dependency>
            <groupId>com.itextpdf</groupId>
            <artifactId>itext-asian</artifactId>
            <version>5.2.0</version>
        </dependency>
        <dependency>
            <groupId>org.xhtmlrenderer</groupId>
            <artifactId>flying-saucer-pdf</artifactId>
            <version>9.0.3</version>
        </dependency>
```
3. 