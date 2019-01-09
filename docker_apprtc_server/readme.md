# AppRTC 是 WebRTC 的一个 demo 应用，为了能快速安装一个WebRTC服务器和AppRTC服务器，采用了docker方式。
---
1. 下载镜像
```
docker pull piasy/apprtc-server
```
2. 执行镜像
```
docker run --rm \
  -p 8080:8080 -p 8089:8089 -p 3478:3478 -p 3478:3478/udp -p 3033:3033 \
  --expose=59000-65000 \
  -e PUBLIC_IP=172.17.0.41 \
  -v <path to constants.py>:/apprtc_configs \
  -t -i piasy/apprtc-server
```
3. 创建一个contants.py配置文件
[constants.py文件模板](./contants.py)