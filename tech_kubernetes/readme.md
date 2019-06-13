1. 如果Linux已经安装Docker，那么在安装Kuberntes的时候可能遇到冲突`错误：docker-ce conflicts with 2:docker-1.13.1-96.gitb2f74b2.el7.centos.x86_64`。需要卸载后，再安装Kubernetes.
```
# 查看安装过的docker
yum list installed | grep docker
# 卸载查询到的docker相关的
yum remove -y docker-ce.x86_64
yum remove -y docker-ce-cli.x86_64
# 删除容器镜像
rm -rf /var/lib/docker
# 再次安装Kubernetes
yum install -y etcd kubernetes
```
2. 修改`/etc/sysconfig/docker`配置文件中的OPTIONS部分的内容，修改如下。
```
OPTIONS='--selinux-enabled=false --insecure-registry gcr.io'
```
3. 修改`/etc/kubernetes/apiserver`配置文件中的配置，把--admission_control参数中的`ServiceAccound`参数删除。
4. 按顺序启动所有的服务。
```
systemctl start etcd
systemctl start docker
systemctl start kube-apiserver
systemctl start kube-controller-manager
systemctl start kube-scheduler
systemctl start kubelet
systemctl start kube-proxy
```
5. 创建一个`mysql-rc.yaml`文件，内容如下。
```yml
apiVersion: v1
kind: ReplicationController
metadata:
  name: mysql
spec:
  replicas: 1
  selector:
    app: mysql
  template:
    metadata:
      labels:
        app: mysql
    spec:
      containers:
      - name: mysql
        image: mysql
        ports:
        - containerPort: 3306
        env:
        - name: MYSQL_ROOT_PASSWORD
          value: "123456"
```
部署这个yml到k8s，通过命令查看结果。
```
# 部署
kubectl create -f mysql-rc.yaml
#查看刚刚创建的rc
kubectl get rc
# 查看创建的Pod
kubectl get pods
# 查看docker运行的程序
docker ps | grep mysql
```
6. 运行pod，pod不能成功启动。出现`open /etc/docker/certs.d/registry.access.redhat.com/redhat-ca.crt: no such file or directory`的解决方法。
```
# 方法一
yum install *rhsm*

#方法二
 wget http://mirror.centos.org/centos/7/os/x86_64/Packages/python-rhsm-certificates-1.19.10-1.el7_4.x86_64.rpm
 rpm2cpio python-rhsm-certificates-1.19.10-1.el7_4.x86_64.rpm | cpio -iv --to-stdout ./etc/rhsm/ca/redhat-uep.pem | tee /etc/rhsm/ca/redhat-uep.pem
docker pull registry.access.redhat.com/rhel7/pod-infrastructure:latest
```
