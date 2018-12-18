# rtmp服务器，使用nginx插件方式
1. [nginx-docker官方地址](https://github.com/alfg/docker-nginx-rtmp)
2. 获取镜像，并执行
```
docker pull alfg/nginx-rtmp
docker run -it -p 1935:1935 -p 8080:80 --rm alfg/nginx-rtmp
```
3. 推流的地址格式
```
rtmp://<ip地址>:1935/stream/$流的名字
```
4. 拉取流
    - 如果使用Safari、VLC播放器或者HLS播放器使用  `http://ip地址>:8080/live/$流的名字.m3u8` 地址格式
    - 如果使用ffplay拉取流，使用 `ffplay -fflags nobuffer rtmp://ip地址:1935/stream/流的名字` 格式拉取
    - 如果使用播放列表方式，使用 `http://ip地址:8080/live/流的名字.m3u8`
    - 如果使用OBS推流或者拉取流，地址使用 `rtmp://ip地址:1935/stream` 格式。流的key是`流的名字`