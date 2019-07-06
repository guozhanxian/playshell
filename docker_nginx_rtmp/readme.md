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
    
5. 进入docker，修改`nginx.conf`配置文件，加入rtsp转rtmp流的配置。
```
daemon off;

error_log logs/error.log debug;

events {
    worker_connections 1024;
}

rtmp {
    server {
        listen 1935;
        chunk_size 4000;
       
        live on;
        hls on;
        hls_fragment 5s;
        application cam1 {
            hls_path /tmp/cam1;
        }
        exec_static ffmpeg -i rtsp://test:air123456@192.168.224.10:554/cam/realmonitor?channel=2&subtype=1 -c copy -f flv rtmp://0.0.0.0:1935/cam1/stream;
    }
}

http {
    ssl_ciphers         HIGH:!aNULL:!MD5;
    ssl_protocols       TLSv1 TLSv1.1 TLSv1.2;
    ssl_session_cache   shared:SSL:10m;
    ssl_session_timeout 10m;

    server {
        listen 80;

        # Uncomment these lines to enable SSL.
        # Update the ssl paths with your own certificate and private key.
        # listen 443 ssl;
        # ssl_certificate     /opt/certs/example.com.crt;
        # ssl_certificate_key /opt/certs/example.com.key;

        location /hls {
            types {
                application/vnd.apple.mpegurl m3u8;
                video/mp2t ts;
            }
            root /opt/data;
            add_header Cache-Control no-cache;
            add_header Access-Control-Allow-Origin *;
        }

        location /live {
          alias /opt/data/hls;
          types {
              application/vnd.apple.mpegurl m3u8;
              video/mp2t ts;
          }
          add_header Cache-Control no-cache;
          add_header Access-Control-Allow-Origin *;
        }

        location /stat {
            rtmp_stat all;
            rtmp_stat_stylesheet static/stat.xsl;
        }

        location /static {
            alias /www/static;
        }

        location = /crossdomain.xml {
            root /www/static;
            default_type text/xml;
            expires 24h;
        }
    }
}

```
6. 参考文章
> [centos7.5 安装流媒体服务器(nginx+rtmp，rtsp转rtmp，rtsp转m3u8) 直播海康摄像头视频](https://blog.csdn.net/tjjingpan/article/details/89639433)
> [Nginx-rtmp+ FFmpeg +Docker + vue.js 直播系统搭建](https://www.linuxidc.com/Linux/2019-06/158950.htm)
> [centos+nginx将大华rtsp视频流转成rtmp，并在web浏览器上直播](https://blog.csdn.net/cslp517/article/details/89383080)
> [RTSP转RTMP,Nginx和nginx-rtmp-module配置直播推流服务器](https://blog.csdn.net/pnoter/article/details/90574313)
