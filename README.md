# JAVA_HOME  
JAVA_HOME=C:\Program Files\Java\jdk1.8.0_161  
Path=~;%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;  
CLASSPATH=.;%JAVA_HOME%\lib;%JAVA_HOME%\lib\tools.jar

# 框架使用  jjxiaoluo@yeah.net
配置管理：Apollo、diamond、disconf

调用链：Zipkin、Pinpoint、CAT

api网关：APIAxle(http://apiaxle.com/)、Tyk(http://tyk.io/)、apiGrove(http://apigrove.net/)、WSO2 API Manager( http://wso2.com/products/api-manager/)、kong(http://www.oschina.net/p/kong?from=20150510)

区块链：https://yq.aliyun.com/articles/65264?utm_campaign=wenzhang&utm_medium=article&utm_source=QQ-qun&utm_content=m_8025


# 重要资源

基本概念：https://my.oschina.net/hosee/blog/597934

分布式事务：http://jm.taobao.org/2017/09/01/post-20170901/

linux命令大全：http://man.linuxde.net/strace

OOM分析：google perftools + btrace 或 dump + MAT（http://jm.taobao.org/2011/01/13/684/， MAT/Yourkit/JProfiler/TPTP）

代码动态修改：hotswap(该补丁的网址http://ssw.jku.at/dcevm/)----http://jm.taobao.org/2013/11/24/641/

阿里GTS：https://help.aliyun.com/document_detail/48726.html?spm=5176.product48444.6.539.dwWn4h

4大Java OSGi 框架：Knopflerfish, Apache Felix, Equinox, Spring DM

宜信（http://college.creditease.cn）技术研发中心开源支撑智能化运维的三大利器：   UAVStack,Wormhole,DBus（http://www.sohu.com/a/191153441_115128）

JSON-RPC和restful讨论：https://www.zhihu.com/question/28570307


# 网络基本功系列：细说网络那些事儿
https://community.emc.com/thread/197851

# Elasticsearch
《Elasticsearch技术解析与实战》
	包含Elasticsearch 5最新功能，凝聚了作者多年开发经验，分布式大数据全文搜索与数据挖掘必备工具。


《大数据搜索与日志挖掘及可视化方案——ELK Stack:Elasticsearch、Logstash、Kibana》(第二版)

　　对大数据的搜索与挖掘，在当今的“互联网+” 时代是很有必要的。


《大数据搜索 与日志挖掘及可视化方案--ELK Stack Elasticsearch Logstash Kibana(第2版)》
	提出 的分布式大数据搜索与日志挖掘及可视化方案是基于 ELK Stack而提出的，它能有效应对海量大数据所带 来的分布式存储与处理、全文检索、日志挖掘、可视 化等问题。
	构建在全文检索开源软件Lucene之上的 Elasticsearch，不仅能对海量规模的数据完成分布 式索引与检索，还能提供数据聚合分析。
	据国际** 的数据库产品评测机构DBEngines的统计，在2016 年1月，Elasticsearch已超过Solr等，成为排名第 一的搜索引擎类应用;
	Logstash能有效处理来源于各 种数据源的日志信息;Kibana能得出可视化分析结果 。了解基于ELK Stack的大数据搜索与日志挖掘及可 视化方案，掌握Elasticsearch、Logstash、Kibana 的基本使用方法和技巧，很有必要。

《深入理解ElasticSearch》
　　欢迎来到ElasticSearch的世界。通过阅读本书，我们将带你接触与ElasticSearch紧密相关的各种话题。本书会从介绍Apache Lucene及ElasticSearch的基本概念开始。
	即使读者熟悉这些知识，简略的介绍也是很有必要的，掌握背景知识对于全面理解集群构建、索引文档、搜索这些操作背后到底发生了什么至关重要。


《Elasticsearch服务器开发》
	本书这一版针对Elasticsearch的最新版本更新了内容，增加了第1版中遗漏的重要内容。本书首先介绍如何启动和运行Elasticsearch、Elasticsearch的基本概念，以及如何以最基本的方式索引和搜索数据。
	接下来，本书讨论了Querydsl查询语言，通过它可以创建复杂的查询并过滤返回的结果。此外，本书还展示了如何使用切面技术(faceting)基于查询结果来计算汇总数据，如何使用新引进的聚合框架，如何使用Elasticsearch的空间搜索和预搜索。
	最后，这本书将阐释Elasticsearch的管理API，如分片安置控制和集群处理等功能。不管你是全文检索和Elasticsearch的初学者，还是使用过Elasticsearch，你都能从本书中有所收获。

微服务架构中，众多服务将其资产和功能都通过API暴露出
来，同时也扩大了系统的被攻击面。因此一个零信任——
“永不信任，始终验证”的安全架构势在必行。然而，由于
服务代码复杂性的增加以及在多语言环境中缺少库和语言
的支持，服务之间的安全控制往往会被忽略。为了解决这
个复杂性，我们已经看到将安全性委托给进程外Sidecar的
做法，Sidecar是一个独立的进程或一个容器，它与每个服
务一起部署和调度，并共享相同的执行上下文、主机和身
份。Sidecar实现了安全功能，如对服务间的通信作透明加
密、TLS终止，以及对调用方服务或最终用户的鉴权机制。在
实现自己的用于端点安全的SIDECAR之前，我们推荐你先
研究一下Istio、linkerd或者Envoy。
