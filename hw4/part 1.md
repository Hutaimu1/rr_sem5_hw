## 1.首先选择一项前后端应用，这里选择了一个简单的ssh登录应用，[https://github.com/cxy1221/testcicd]

## 2.使用maven将其打包为war文件

## 3.在docker中拉取最新的tomcat镜像，找到webapps文件夹并将war包部署进镜像中

## 4.发布带有war包的tomcat镜像至docker hub

## 5.使用travis ci,编写.travis.yml文件，并进行持续集成及持续部署测试[https://www.travis-ci.org/cxy1221/testcicd/builds/476868643]


# 感想

这次没能很好地处理mysql数据库，因为ip的分配问题，虽将其固定至指定ip下依旧无法访问，没能将其持续集成有点遗憾。
