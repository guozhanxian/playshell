1. 安装gocode和gocode-gomod
```
go get -u -v github.com/mdempsky/gocode
go get -u -v github.com/stamblerre/gocode
```
2. 制作软连接
```
gocode-gomod close
cd $GOPATH/bin
rm gocode-gomod
ln -s gocode gocode-gomod
```
3. 在创建一个`.go`文件的时候，出现报错`go: cannot find main module; see 'go help modules' go`。原因是，没有使用mod，需要在项目的目录中创建一个`go.mod`文件，并加入如下内容：
```
module src
```

4. 还继续出现`go: cannot determine module path for source directory /root/mycode/golang-so (outside GOPATH, no imp`错误，那么可以创建一个`src`文件夹，将源码放入该文件夹中。

