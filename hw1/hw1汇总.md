# Google Borg学习报告
###  什么是Google Borg?
- Google Borg是一个集群管理器，其中每个集群含有成千上万台机器，运行着Google的很多不同的应用。负责对成千上万个应用程序提交任务进行接收、调试、启动、停止、重启、监控等。Borg通过准入控制，高效的任务打包，超额的资源分配和进程级隔离的机器共享，来实现超高的资源利用率。

###  Google Borg特性
-   隐藏资源管理和故障处理的细节，因此其用户可以专注于应用程序开发;
-   提供高可靠性和高可用性操作，支持的应用也是如此; 
-   使我们能够有效地在数万台机器上运行工作负载。


###  Google Borg优势
-   采用对作业进行优先级和配额的策略来平衡机器的压力。使其不超过其可容纳的限度。
-   对于集群的管理可以使得运行在Borg上的应用程序获得益处，包括命名和负载平衡等。
-   Borg为每个任务创建的一个这样的服务：可以检查其详细日志，执行历史和最终结果等信息，在发生错误时通过检查日志信息可以将状态恢复到某个特定的时间点，我的理解是类似于github的版本回溯
-   Borg的主机是分布式系统内核，使得能够在不牺牲性能或可维护性的情况下扩展工作负载和功能集。


###  Google Borg缺陷
-   作为任务的唯一分组机制，作业是限制性的。Borg没有一流的方式来管理整个多作业服务作为一个单一的实体，或指的是服务的相关实例，易受到黑客的攻击
-   Borg提供了一大套针对“超级用户”的功能，以便用户可以微调程序运行方式（更多参数），不幸的是，这种API的丰富性使事情变得更难针对“休闲”用户，而限制了其发展。
-   每个机器一个IP地址使事情复杂化，Borg必须调度作为资源的端口。


### 我的观点
-   作为一个使用者来说的话，Borg这种集群管理模式极大地方便了团队项目的合作开发，尽管在很多方面Borg都有着极大地优势，但是近年来像Kubernetes这些新的系统的出现，使得Borg的优势不再那么明显。当然，Kubernetes这类系统也从Borg中借鉴和改进很多方法策略，可以说，Borg是Google开发的集群管理器中的先驱者
  

>  参考文章：[http://delivery.acm.org/10.1145/2750000/2741964/a18-verma.pdf?ip=59.78.47.45&id=2741964&acc=OA&key=BF85BBA5741FDC6E%2E17676C47DFB149BF%2E4D4702B0C3E38B35%2E5945DC2EABF3343C&__acm__=1538237525_c673112cd54636db56912df8f929ea10](http://delivery.acm.org/10.1145/2750000/2741964/a18-verma.pdf?ip=59.78.47.45&id=2741964&acc=OA&key=BF85BBA5741FDC6E%2E17676C47DFB149BF%2E4D4702B0C3E38B35%2E5945DC2EABF3343C&__acm__=1538237525_c673112cd54636db56912df8f929ea10)


author:516030910186 胡太穆

================================================================
# Apollo学习报告

## Apollo是什么?

* Apollo（阿波罗）是携程框架部门研发的开源配置管理中心

* Apollo能够集中化管理应用不同环境、不同集群的配置，配置修改后能够实时推送到应用端，并且具备规范的权限、流程治理等特性

## Apollo有哪些特性？

* 统一管理不同环境、不同集群的配置
    *  提供了一个统一界面集中式管理不同环境（environment）、不同集群（cluster）、不同命名空间（namespace）的配置
    *  同一份代码部署在不同的集群，可以有不同的配置，比如zk的地址等
    *  通过命名空间（namespace）可以很方便的支持多个不同应用共享同一份配置，同时还允许应用对共享的配置进行覆盖
* 配置修改实时生效（热发布）
    * 用户在Apollo修改完配置并发布后，客户端能实时（1秒）接收到最新的配置，并通知到应用程序 
* 版本发布管理 
    * 所有的配置发布都有版本概念，从而可以方便地支持配置的回滚     
* 灰度发布 
    * 支持配置的灰度发布，比如点了发布后，只对部分应用实例生效，等观察一段时间没问题后再推给所有应用实例 
* 权限管理、发布审核、操作审计
    * 应用和配置的管理都有完善的权限管理机制，对配置的管理还分为了编辑和发布两个环节，从而减少人为的错误
    * 所有的操作都有审计日志，可以方便的追踪问题
* 客户端配置信息监控 
    * 可以在界面上方便地看到配置在被哪些实例使用 
* 提供Java和.Net原生客户端
    * 提供了Java和.Net的原生客户端，方便应用集成 
    * 支持Spring Placeholder, Annotation和Spring Boot的ConfigurationProperties，方便应用使用（需要Spring 3.1.1+） 
    * 同时提供了Http接口，非Java和.Net应用也可以方便的使用 
* 提供开放平台API
    * Apollo自身提供了比较完善的统一配置管理界面，支持多环境、多数据中心配置管理、权限、流程治理等特性
    * Apollo出于通用性考虑，对配置的修改不会做过多限制，只要符合基本的格式就能够保存
    * 对于配置格式比较复杂或输入值需要进行校验的应用，Apollo支持应用方通过开放接口在Apollo进行配置的修改和发布，并且具备完善的授权和权限控制 
* 部署简单 
    * 配置中心作为基础服务，可用性要求非常高，这就要求Apollo对外部依赖尽可能地少
    * 目前唯一的外部依赖是MySQL，所以部署非常简单，只要安装好Java和MySQL就可以让Apollo跑起来
    * Apollo还提供了打包脚本，一键就可以生成所有需要的安装包，并且支持自定义运行时参数

## Apollo基础模型

* 用户在配置中心对配置进行修改并发布 
* 配置中心通知Apollo客户端有配置更新 
* Apollo客户端从配置中心拉取最新的配置、更新本地配置并通知到应用 

![avatar](https://img-blog.csdn.net/20180306170050115?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzQyODg2MzA=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

## 界面概览

![avatar](https://img-blog.csdn.net/2018030617023341?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzQyODg2MzA=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

##  添加/修改配置项

* 用户可以通过配置中心界面方便的添加/修改配置项

![avatar](https://img-blog.csdn.net/20180306170441197?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzQyODg2MzA=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

* 输入配置信息

![avatar](https://img-blog.csdn.net/20180306170536700?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzQyODg2MzA=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

## 发布配置

* 输入配置信息

![avatar](https://img-blog.csdn.net/20180306170712800?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzQyODg2MzA=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

* 填写发布信息

![avatar](https://img-blog.csdn.net/20180306170806604?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzQyODg2MzA=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

## 代码部分

* 客户端获取配置
-       Config config = ConfigService.getAppConfig();
        Integer defaultRequestTimeout = 200;
        Integer requestTimeout = 
            config.getIntProperty("request.timeout",defaultRequestTimeout);

* 客户端监听配置变化
-        Config config = ConfigService.getAppConfig();
        config.addChangeListener(new ConfigChangeListener() {
            @Override
            public void onChange(ConfigChangeEvent changeEvent) {
                for (String key : changeEvent.changedKeys()) {
                    ConfigChange change = changeEvent.getChange(key);
                    System.out.println(String.format(
                        "Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s", 
                        change.getPropertyName(), change.getOldValue(),
                        change.getNewValue(), change.getChangeType()));
                }
            }
        });


* Spring集成样例
 -       @Configuration
        @EnableApolloConfig
        public class AppConfig {}
        @Component
        public class SomeBean {
            @Value("${request.timeout:200}")
            private int timeout;
            @ApolloConfigChangeListener
            private void someChangeHandler(ConfigChangeEvent changeEvent) {
                if (changeEvent.isChanged("request.timeout")) {
                    refreshTimeout();
                }
            }
        }

## 我的观点

&ensp;&ensp;&ensp;&ensp;在项目中开发过程中经常会使用很多配置项、这会增加很多沟通成本、甚至会由于配置定义不明确致使上线出现问题，多人协同开发或版本升级过程中尤其如此，而Apollo 配置中心，它具有实时生效，支持灰度发布，分环境、分集群管理配置，和完善的权限管理等优点，使用 Apollo 可以使复杂凌乱的配置变得简单明了，维护简单，升级方便。

author:516030910185 侯哲宇

================================================================


# 调度与集群管理系统Sigma学习报告

## 1.简介

 互联网普及的20年来，尤其是近10年移动互联网、互联网+的浪潮，使互联网技术渗透到各行各业，渗透到人们生活的方方面面，这带来了互联网服务规模和数据规模的大幅增长。日益增长的服务规模和数据规模带来数据中心的急剧膨胀。在大规模的数据中心中，传统的运维方式已经不能满足规模化的需求，于是基于自动化调度的集群管理系统纷纷涌现。

Sigma 是阿里巴巴全集团范围的 Pouch 容器调度系统。2017年是 Sigma 正式上线以来第一次参与双11，在双11期间成功支撑了全集团所有容器（交易线中间件、数据库、广告等二十多业务）的调配，使双11IT成本降低50%，是阿里巴巴运维系统重要的底层基础设施。

Sigma 已经是阿里全网所有机房在线服务管控的核心角色，管控的宿主机资源达到几十万量级，重要程度不言而喻，其算法的优劣程度影响了集团整体的业务稳定性，资源利用率。




## 2.统一调度体系
Sigma 有 Alikenel、SigmaSlave、SigmaMaster 三层大脑联动协作，Alikenel 部署在每一台物理机上，对内核进行增强，在资源分配、时间片分配上进行灵活的按优先级和策略调整，对任务的时延，任务时间片的抢占、不合理抢占的驱逐都能通过上层的规则配置自行决策。

- Alikenel部署在每一台NC上，对内核进行增强，在资源分配、时间片分配上进行灵活的按优先级和策略调整，对任务的时延，任务时间片的抢占、不合理抢占的驱逐都能通过上层的规则配置自行决策。

- SigmaSlave可以在本机上进行CPU的分配、应急场景的处理。通过本机Slave对时延敏感任务快速做出决策和响应，避免因全局决策处理时间长带来的业务损失。

- SigmaMaster是一个最强的大脑，它可以统揽全局，为大量物理机的容器部署进行资源调度分配和算法优化决策。
![](https://res.infoq.com/news/2017/12/Cloud-Sigma-Pouch-Alibaba/zh/resources/2361-1514287018852.png)

整个架构是面向终态的设计理念，请求进来后把数据存储到持久化存储，调度器识别调度需求分配资源。这个系统采用阿里Pouch容器（兼容OCI标准），兼容Kubernetes API，阿里巴巴这样做的目的是希望和开源社区共同建设和发展。

## 3.优势与不足
####  优势：
内核资源隔离上的关键技术

- 在 CPU HT 资源隔离上，做了 Noise Clean 内核特性，解决在 / 离线超线程资源争抢问题。
- 在 CPU 调度隔离上，CFS 基础上增加 Task Preempt 特性，提高在线任务调度优先级。
- 在 CPU 缓存隔离上，通过 CAT，实现在、离线三级缓存 (LLC) 通道隔离 (Broadwell 及以上)。
- 在内存隔离上，拥有 CGroup 隔离 /OOM 优先级；Bandwidth Control 减少离线配额实现带宽隔离。
- 在内存弹性上，在内存不增加的情况下，提高混部效果，在线闲置时离线突破 memcg limit；需要内存时，离线及时释放。
- 在网络 QoS 隔离上，管控打标为金牌、在线打标为银牌、离线打标为铜牌，分级保障带宽。

在线集群管理上的关键技术

- 对应用的内存、CPU、网络、磁盘和网络 I/O 容量进行画像，知道它的特征、资源规格需求，不同的时间对资源真实使用情况，然后对整体规格和时间进行相关性分析，进行整体调度优化。
- 亲和互斥和任务优先级的分配，哪种应用放在一起使整体计算能力比较少、吞吐能力比较高，这是存在一定亲和性。
- 不同的场景有不同的策略，双 11 的策略是稳定优先，稳定性优先代表采用平铺策略，把所有的资源用尽，让资源层全部达到最低水位。日常场景需要利用率优先，“利用率优先” 指让已经用掉的资源达到最高水位，空出大量完整资源做规模化的计算。
- 应用做到自动收缩、垂直伸缩、分时复用。
- 整个站点的快速扩容缩容，弹性内存技术等。


####  不足：
- 静态需求满足，各种微服务就不能完全和谐相处，运行到最佳
-  无法通过cpushare 等方式削峰填谷，有效利用资源

## 4.感想
学术研究和实际真实的生产环境可能存在很大差异，而阿里的Sigma经得起实践的检验。在双11期间成功支撑了全集团所有容器的调配，使双11IT成本降低50%，实属一款优质的调度与集群管理系统。

author:516030910005 陈星伊
