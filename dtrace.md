## 分布式跟踪
> https://github.com/luoyaogui/DistributedTracingSystem

> 现代APM体系，基本都是参考Google的Dapper（大规模分布式系统的跟踪系统）的体系来做的。通过跟踪请求的处理过程，来对应用系统在前后端处理、服务端调用的性能消耗进行跟踪，关于Dapper的介绍可以看这个链接：Dapper，大规模分布式系统的跟踪系统 by bigbully我所知道相对有名的APM系统主要有以下几个：

  
- **Pinpoint**
  ```
    github地址：GitHub - naver/pinpoint: Pinpoint is an open source APM (Application Performance Management) tool for large-scale distributed systems written in Java.对java领域的性能分析有兴趣的朋友都应该看看这个开源项目，这个是一个韩国团队开源出来的，通过JavaAgent的机制来做字节码代码植入，实现加入traceid和抓取性能数据的目的。NewRelic、Oneapm之类的工具在java平台上的性能分析也是类似的机制。
  ```
- **SkyWalking**
  ```
    github地址：wu-sheng/sky-walking这是国内一位叫吴晟的兄弟开源的，也是一个对JAVA分布式应用程序集群的业务运行情况进行追踪、告警和分析的系统，在github上也有400多颗星了。功能相对pinpoint还是稍弱一些，插件还没那么丰富，不过也很难得了。
  ```
- **Zipkin官网：OpenZipkin · A distributed tracing system**
  ```
    github地址：GitHub - openzipkin/zipkin: Zipkin is a distributed tracing system这个是twitter开源出来的，也是参考Dapper的体系来做的。Zipkin的java应用端是通过一个叫Brave的组件来实现对应用内部的性能分析数据采集。Brave的github地址：https://github.com/openzipkin/brave这个组件通过实现一系列的java拦截器，来做到对http/servlet请求、数据库访问的调用过程跟踪。然后通过在spring之类的配置文件里加入这些拦截器，完成对java应用的性能数据采集。
  ```
- **CAT**
  ```
    github地址：GitHub - dianping/cat: Central Application Tracking这个是大众点评开源出来的，实现的功能也还是蛮丰富的，国内也有一些公司在用了。不过他实现跟踪的手段，是要在代码里硬编码写一些“埋点”，也就是侵入式的。这样做有利有弊，好处是可以在自己需要的地方加埋点，比较有针对性；坏处是必须改动现有系统，很多开发团队不愿意。
  ```
- **Xhprof/Xhgui这两个工具的组合，是针对PHP应用提供APM能力的工具，也是非侵入式的。**
  ```
    Xhprof github地址：GitHub - preinheimer/xhprof: XHGUI is a GUI for the XHProf PHP extension, using a database backend, and pretty graphs to make it easy to use and interpret.Xhgui github地址：GitHub - perftools/xhgui: A graphical interface for XHProf data built on MongoDB我对PHP不熟，不过网上介绍这两个工具的资料还是蛮多的。
  ```

  > 前面三个工具里面，我推荐的顺序依次是Pinpoint—》Zipkin—》CAT。原因很简单，就是这三个工具对于程序源代码和配置文件的侵入性，是依次递增的：Pinpoint：基本不用修改源码和配置文件，只要在启动命令里指定javaagent参数即可，对于运维人员来讲最为方便；Zipkin：需要对Spring、web.xml之类的配置文件做修改，相对麻烦一些；CAT：因为需要修改源码设置埋点，因此基本不太可能由运维人员单独完成，而必须由开发人员的深度参与了，而很多开发人员是比较抗拒在代码中加入这些东西滴；相对于传统的监控软件（Zabbix之流）的区别，APM跟关注在对于系统内部执行、系统间调用的性能瓶颈分析，这样更有利于定位到问题的具体原因，而不仅仅像传统监控软件一样只提供一些零散的监控点和指标，就算告警了也不知道问题是出在哪里。
