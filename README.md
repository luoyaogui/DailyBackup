# JAVA_HOME  
JAVA_HOME=C:\Program Files\Java\jdk1.8.0_161  
Path=~;%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;  
CLASSPATH=.;%JAVA_HOME%\lib;%JAVA_HOME%\lib\tools.jar

https://about.gitlab.com/installation/

http://www.900ppt.com/pptmoban/shangyejihuashuppt/
https://www.zhimei360.com/bp/
http://www.trjcn.com/zt/business_plan.html?trackid=ads:baidu1:1s:pc11590
http://www.zhuanzhi.ai

# MAVEN（http://maven.apache.org/index.html）  
mvn dependency:tree  
mvn clean package -Dmaven.test.skip=true  
mvn assembly:assembly  

# GIT（https://git-scm.com/、https://git-scm.com/book/zh/v2）  
eclipse安装git插件：http://download.eclipse.org/egit/updates  

git clone https://github.com/libgit2/libgit2 mylibgit(可选-修改名称)  
git status 命令查看哪些文件处于什么状态（M-修改   A-新增  ??-未跟踪）  
git add README  要跟踪 README文件  
git status -s 或 git status --short 你将得到一种格式更为紧凑的输出  
git diff 要查看尚未暂存的文件更新了哪些部分  
git diff --staged 若要查看已暂存的将要添加到下次提交里的内容  
git diff --cached 查看已经暂存起来的变化  
git commit  现在的暂存区已经准备就绪，可以提交了  
git commit -a -m "Story 182"  -m选项--将提交信息与命令放在同一行、-a选项--会自动把所有已经跟踪过的文件暂存起来一并提交，从而跳过 git add 步骤  

rm PROJECTS.md 移除文件  
git rm --cached PROJECTS.md 记录此次移除文件， --cached选项--想把文件从 Git 仓库中删除（亦即从暂存区域移除），但仍然希望保留在当前工作目录中  
git mv file_from file_to 改名，相当于“mv file_from file_to” + “git rm file_from” + “git add file_to”  

git log -p -2  查看提交历史，-p或--patch显示每次提交所引入的差异，-2只显示最近的两次提交  
git log --stat  --stat每次提交的简略统计信息  
git log --pretty=format:"%h - %an, %ar : %s"  
git log --pretty=oneline/short/full/fuller  

git commit --amend   --amend 选项的提交命令来重新提交  
git reset HEAD CONTRIBUTING.md   取消暂存  
git checkout -- CONTRIBUTING.md  撤销对文件的修改  

git remote -v  查看你已经配置的远程仓库服务器，-v会显示需要读写远程仓库使用的Git保存的简写与其对应的 URL  
git remote add pb https://github.com/paulboone/ticgit  格式"git remote add <shortname> <url>"添加一个新的远程 Git 仓库  
git fetch pb  从中拉取所有你还没有的数据。 执行完成后，你将会拥有那个远程仓库中所有分支的引用，可以随时合并或查看。  
git pull 通常会从最初克隆的服务器上抓取数据并自动尝试合并到当前所在的分支   
git push origin master  推送到远程仓库  
git remote show origin 查看某个远程仓库  
git remote rename pb paul 远程仓库的重命名  
git remote remove paul  远程仓库的移除  

注：.gitignore 的文件，列出要忽略的文件的模式  

# Lambda表达式-官方文档
http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.27

# 分析工具
- JMC（https://www.cnblogs.com/aurain/p/6178671.html、http://hg.openjdk.java.net/jmc）
- Vim（https://www.cnblogs.com/yangjig/p/6014198.html）
- GC调优（https://blog.csdn.net/jiasanshou/article/details/24909589、https://blog.csdn.net/u013851082/article/details/53516777、http://www.oracle.com/technetwork/java/javase/gc-tuning-6-140523.html）

# 框架使用  jjxiaoluo@yeah.net
BI0|NIO|AIO|TIO：https://juejin.im/entry/5940fe3ea0bb9f006b77dd14

配置管理：Apollo、diamond、disconf

调用链：Zipkin、Pinpoint、CAT

api网关：APIAxle(http://apiaxle.com/)、Tyk(http://tyk.io/)、apiGrove(http://apigrove.net/)、WSO2 API Manager( http://wso2.com/products/api-manager/)、kong(http://www.oschina.net/p/kong?from=20150510)

区块链：https://yq.aliyun.com/articles/65264?utm_campaign=wenzhang&utm_medium=article&utm_source=QQ-qun&utm_content=m_8025

连接池：HikariCP

sigar

joda-time: http://joda-time.sourceforge.net/、https://blog.csdn.net/weixin_39450045/article/details/78890539

数据同步：https://www.cnblogs.com/davidwang456/articles/9143327.html、http://baijiahao.baidu.com/s?id=1574287087256569&wfr=spider&for=pc

mmap：https://blog.csdn.net/zjf280441589/article/details/54406665
缓存guava cache：https://blog.csdn.net/u012881904/article/details/79263787

校验算法：https://blog.csdn.net/prsniper/article/details/51752064

数据归档：http://baijiahao.baidu.com/s?id=1598803347926956969&wfr=spider&for=pc

数据库大会：http://bss.csdn.net/m/topic/sdcc_invite

mysql千万级查询优化：https://www.cnblogs.com/binbinyouni/p/6070715.html、https://blog.csdn.net/chenchaoxing/article/details/25214397、https://blog.csdn.net/u011921996/article/details/77453748、https://www.cnblogs.com/hxphp/p/7651365.html、https://www.cnblogs.com/zengkefu/p/5667012.html

# 重要资源
设计：http://hao.shejidaren.com/book.html

数据存储方案：http://bigdata.it168.com/a2017/0217/3099/000003099574.shtml、http://baijiahao.baidu.com/s?id=1582114919551255162&wfr=spider&for=pc

开源实时数据处理系统Pulsar（一套搞定Kafka+Flink+DB）：http://baijiahao.baidu.com/s?id=1600068969656532668&wfr=spider&for=pc、http://www.uml.org.cn/zjjs/2016111505.asp

基本概念：https://my.oschina.net/hosee/blog/597934

分布式事务：http://jm.taobao.org/2017/09/01/post-20170901/

ServiceComb：https://blog.csdn.net/FL63Zv9Zou86950w/article/details/78393439

linux命令大全：http://man.linuxde.net/strace

OOM分析：google perftools + btrace 或 dump + MAT（http://jm.taobao.org/2011/01/13/684/， MAT/Yourkit/JProfiler/TPTP）

代码动态修改：hotswap(该补丁的网址http://ssw.jku.at/dcevm/)----http://jm.taobao.org/2013/11/24/641/

阿里GTS：https://help.aliyun.com/document_detail/48726.html?spm=5176.product48444.6.539.dwWn4h

4大Java OSGi 框架：Jarslink、Knopflerfish, Apache Felix, Equinox, Spring DM

宜信（http://college.creditease.cn）技术研发中心开源支撑智能化运维的三大利器：   UAVStack,Wormhole,DBus（http://www.sohu.com/a/191153441_115128）

JSON-RPC和restful讨论：https://www.zhihu.com/question/28570307


# 网络基本功系列：细说网络那些事儿
https://community.emc.com/thread/197851


# 权限系统
https://github.com/a466350665/smart  
https://github.com/zhaojun1998/Shiro-Action  
https://github.com/wuyouzhuguli/FEBS-Shiro  
https://github.com/LiHaodong888/pre  
https://github.com/elunez/eladmin  
