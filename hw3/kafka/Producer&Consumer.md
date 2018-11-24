## 搭建一个简单的kafka Producer和Consumer
### 准备阶段
* 先用命令行启动zookeeper如下图所示，双击运行zkServer.cmd：![image](https://github.com/Hutaimu1/images/blob/master/zookeeperstart.png?raw=true)
* 再开启kafka服务：命令行进入\bin\windows文件夹下，输入指令：
*     kafka-server-start.bat ../../config/server.properties
* 文件夹如图所示：![image](https://github.com/Hutaimu1/images/blob/master/kafkastart.png?raw=true)
  * 成功运行命令行会提示：
  *      INFO [Socket Server on Broker 2], Started (kafka.network.SocketServer)
  * 如下图所示：![image](https://github.com/Hutaimu1/images/blob/master/kafkasuccess.png?raw=true)

* 在bin\windows文件夹下打开windows命令行输入如下命令创建一个topic：
*     kafka-topics.bat --create --zookeeper 127.0.0.1:2181 --replication-factor 3 --partitions 3 --topic my-content2
* 创建成功可以看到在你预设的dataDir路径中创建了my-content2的三个分区(partition=3)，如下图所示：![image](https://github.com/Hutaimu1/images/blob/master/mycontent2.png?raw=true),
* 此时my-content2的三个分区都没有数据（log文件大小为0），如下图所示：![image](https://github.com/Hutaimu1/images/blob/master/mycontent2log.png?raw=true)

### Simple Producer
* 运行SimpleKafkaProducer.class中的main()函数，结果如下图所示：![image](https://github.com/Hutaimu1/images/blob/master/producerresult.png?raw=true)
  * 其中每一行作为一个message发送到my-content2中，但此时log文件并没有更新，需要consumer接收消息，为了方便只检测consumer,我用命令行接收：
  *     kafka-console-consumer.bat --zookeeper localhost:2181 --topic my-content2 --from-beginning 
  结果如图（存在乱码，有点小问题）：![image](https://github.com/Hutaimu1/images/blob/master/writetocontent.png?raw=true)
  此时可以看到log文件被写入东西(size改变)：![image](https://github.com/Hutaimu1/images/blob/master/mycontent2logresult.png?raw=true)

### Simple Consumer
* 运行SimpleKafkaConsumer.class中的main()函数，结果如下图所示：![image](https://github.com/Hutaimu1/images/blob/master/consumerresult.png?raw=true)
* 这一步consumer将message接收，不需要进入命令行