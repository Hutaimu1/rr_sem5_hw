
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
