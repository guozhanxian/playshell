1. 安装gocode和gocode-gomod
```
go get -u -v github.com/mdempsky/gocode
go get -u -v github.com/stamblerre/gocode
```
2.制作软连接
```
gocode-gomod close
cd $GOPATH/bin
rm gocode-gomod
ln -s gocode gocode-gomod
```
