# XDubbo Mic-Core 
xdubbo从扩展点也叫微内核设计着手思考如何重构，并配合本人设计的秒杀分布式系统架构演进进行实际超高并发场景压测
重构的驱动思想是新兴springCloud,service mesh框架与实际行业特性之间存在哪些不必要的坑和无休止探索
根据作者目前的认知，无论是新兴的云端应用，还是如今DT应用，终究是要解决分布式问题，分布式问题最突出的问题
就是SOA服务化的问题，而解决SOA服务化问题最根本的就是解决服务治理，目前服务治理成熟的开源框架除了dubbo,其他的未必可靠
无论过去所谓的MSA，还是如今的Mesh 始终探讨的是服务化治理问题，值得庆幸的是他们一直在演进，国内的绝大多数企业包括绝大多数互联网企业
一直在追随，对这点来说我不知道是高兴还是遗憾...
另一个分布式关键主题就是分布式DB,目前市场上的分布式DB基本是假的，真正的分布式DB目前还在研究中或实践中
也就是说，分布式问题最关键的其实就是2个问题，1个是服务化治理，2个是分布式ACID
这也是困恼国内企业一直的病根，从来就没有好过；也许我目前实力和时间有限，但我觉得是时候去干一场了...

---
##xdubbo是个什么样的框架？</br>
1，解决分布式服务治理特性</br>
2，解决分布式事务治理特性</br>

##xdubbo分布式特性是啥？</br>
1，架构自动扩容、降级</br>
2，网络自动扩容、降级</br>
3，硬件自动扩容、降级</br>
4，部署自动扩容、降级</br>
5，应用自动扩容、降级</br>
6，存储自动扩容、降级</br>
7，全栈自动监控、预警</br>

##xdubbo重构dubbo总体输出规划</br>
1，对源码任何疑问不放过,有设计,有延伸,有深度,有广度</br>
2，内核设计模式到热插件设计模式到全管道设计模式出发</br>
3，逆向源码架构设计</br>
4，配合秒杀架构演进</br>
5，总体重构规划</br>
6，发布版本、记录</br>

##xdubbo子产品输出规划</br>
1, 全链路监控APM基于pinpoint (https://blog.csdn.net/luozhonghua2014/article/details/80593785)</br>
2, xdubbo LAAS基于Mesos      (https://blog.csdn.net/luozhonghua2014/article/details/80925053)

---
#欢迎~~~~富有想像力和设计力的全栈小伙伴来约!</br>
#xdubbo   https://github.com/luozhonghua/xdubbo</br>
#xpinpint https://github.com/luozhonghua/Xpinpoint</br>
#作者    luozhonghua     微信 Lzh20140128 </br>
#博客    https://blog.csdn.net/luozhonghua2014</br>