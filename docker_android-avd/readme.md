# thedrhax-dockerfiles/android-avd包含最新的Android SDK和可配置的AVD
1. [thedrhax-dockerfiles/android-avd官网地址](https://github.com/thedrhax-dockerfiles/android-avd)
2. 拉取镜像，运行镜像
```
docker pull thedrhax/android-avd
docker run -it --rm --device /dev/kvm -p 5554:5554 -p 5555:5555 -p 5900:5900 -p 6080:6080 thedrhax/android-avd
```
3. 使用adb命令连接，并上传apk
```
adb connect ip地址:5555
```
4. 使用VNC Viewer连接
```
ssvncviewer ip地址:5900
```
5. 使用noVNC连接AVD
```
http://ip地址:6080/vnc.html
```