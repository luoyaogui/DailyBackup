# 框架使用
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
