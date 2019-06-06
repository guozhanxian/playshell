1. 在安装的过程中遇到`jsonpath@1.0.0 postinstall:node lib/aesprim.js>generated/aesprim_browser.js`的错误。通过查找资料，在[CSDN上找到文章](https://blog.csdn.net/Angry_Mills/article/details/88728085)解决了这个问题。是因为node对于root的权限问题。执行命令，修改node权限
```
chown -R root:root node-v12.3.1
```
