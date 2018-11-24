# Windows下kafka安装笔记
### step 1:下载安装Zookeeper
- 下载压缩包：http://mirror.bit.edu.cn/apache/zookeeper/current/
- 解压到zookeeper-3.4.12
- 进入\conf目录，将zoo_sample.cfg重命名为zoo.cfg
- 编辑zoo.cfg,修改如下变量：
  - dataDir:修改到一个新建文件夹，如./conf/data
  - dataLogDir:修改到一个新建文件夹，如./conf/dataLog
    - 建议使用绝对路径，路径的目录分隔符用"/"而不是"\"
  - 添加集群配置：
    -     server.1=127.0.0.1:12888:1388
    -     server.2=127.0.0.1:12889:1389
    -     server.3=127.0.0.1:12887:1387   
    - server.A=B:C:D
      - A:标识第几号服务器
      - B:服务器ip
      - C:端口号
      - D:是在leader挂掉时专门用来进行选举leader所用的端口
- 复制两份配置好的zookeeper-3.4.12文件夹，重命名为..-2和..-3，如图：![image](https://github.com/Hutaimu1/images/blob/master/zoo.png?raw=true)
- 修改每个文件夹下zoo.cfg中clientPort端口号（默认为2181），如设置其他两个为2182和2183
- 创建ServerID
  - 在每个文件夹下的.\conf\data文件夹下新建myid文件（新建文本文档再改名）分别写入之前创建的集群服务器号：即1 2 3
- 在每个文件夹下的bin文件夹下启动zkServer.cmd

### step 2:安装kafka 
- 下载安装包： http://archive.apache.org/dist/kafka/0.8.2.2/kafka_2.9.2-0.8.2.2.tgz
- 解压，打开config目录，编辑server.properties，写入端口号，主机ip，分区数等信息
- 集群配置：
  - 同样复制两份重命名如上
  - 分别修改server.properties中broker.id（从0到2），修改port为不同值
 - 启动：分别进入bin\windows文件夹 ，打开命令行，指定配置文件
   -     kafka-server-start.bat ../../config/server.properties
   - 如果出现
   -     INFO [Kafka Server 1], started (kafka.server.KafkaServer)则表示开启成功


### 测试
- 创建topic（相当于邮箱，接受信息（邮件））
-     kafka-topics.bat --create --zookeeper 127.0.0.1:2181 --replication-factor 1 --partitions 1 --topic test
  - --create 创建主体
  - --topic 主题名称
  - --zookeeper 集群地址（在step1中设置的clientPort）
  - --partition 主题的分区数量（相当于你对邮箱进行分区，邮件可以发送到邮箱的不同分区中）
  - --replication-factor 分区的副本个数
- 检查是否成功：
-     kafka-topics.bat --list --zookeeper localhost:2181
- 发送消息（打开另一个kafka命令行输入以下指令）
-     kafka-console-producer.bat --broker-list localhost:9092 --topic test
  - 这里可以写入数据
- 接收消息
-     kafka-console-consumer.bat --zookeeper localhost:2181 --topic test --from-beginning
  - 可以看到刚才写入的数据