# 准备阶段
| 服务器 | Core | Memory(GB) | 系统 | 公网IP |
| :---: | :---: | :---: | :---: | :---:|
| k8s-master | 4 | 8 | Ubuntu 16.04 LTS | 172.81.250.109 |
| data2 | 1 | 1 | Ubuntu 16.04 LTS | 129.211.109.124 |
| data3 | 1 | 1 | Ubuntu 16.04 LTS | 129.211.108.94 |
* 使用了腾讯云服务器

# 各种安装包的版本要求
*  Rancher: v1.6.11
*  docker-ce:17.03.2
*  kubernetes:1.8.10
    * Attention:使用docker创建Rancher镜像过后，利用Rancher创建kubernetes集群的版本每次都会不一样，感觉上是取了kubernetes的一个随机的版本，然后只有这个1.8.10的版本能够加载kubernetes-dashboard组件，打开仪表板，其他版本都加载不出来。

# 步骤
参考博客：
1. https://blog.csdn.net/villare/article/details/79332549 
2. https://blog.csdn.net/u011142688/article/details/80372546

## step 1
在k8s-master服务器(主节点)上运行：
*     sudo docker run -d --restart always --name rancher-server -p 8080:8080 rancher/server:v1.6.11-rc3
这个时候可以访问172.81.250.109:8080进入Rancher镜像。

## step 2
在Rancher中添加环境模板，选择kubernetes容器
*  注意模板的版本一定是1.8.10，否则加载不出来仪表盘。
*  更改kubernetes镜像源为国内的源（私有仓库地址设置为 registry.cn-shenzhen.aliyuncs.com），否则要翻墙；同样将Pod Infra Container Image 由默认的 gcr.io/google_containers/pause-amd64:3.0 改成 rancher_cn/pause-amd64:3.0；将各个组件的命名空间设为rancher_cn。

## step 3
使用该环境模板创建集群
![image](https://github.com/Hutaimu1/images/blob/master/tianjiazhuji.png?raw=true)
将第五步的脚本分别在k8s-mater,data2,data3服务器的终端运行，将这三台服务器加入kubernetes集群。

## step 4 (结果展示)
![image](https://github.com/Hutaimu1/images/blob/master/zhuji.png?raw=true)
![image](https://github.com/Hutaimu1/images/blob/master/jiedian.png?raw=true)