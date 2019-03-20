# 删除文件第一行
---
```python
# _*_ coding:utf-8 _*_

#origin_f = open('D:/bigData/2.txt',"r",encoding='gb18030', errors='ignore')
origin_f = open('D:/bigData/1.txt',"r+")
new_f = open("D:/bigData/3.txt","w+")

line = origin_f.readline()
while True:
    line = origin_f.readline()
    new_f.writelines(line)
    if not line:
        break
origin_f.close()
new_f.close()
```
