# 爬虫获取某MMA网站，获取所有赔率数据-仅做学习使用。

技术栈：jsoup+htmlunit

从零开始，基于面向对象的设计，爬取到整个MMA网站赔率信息。

htmlunit--java的无头浏览器

jsoup html分析工具

难点：1.面向对象的设计（如何包装数据，这个花了很长时间思考）
     2.网站数据规则分析（数据获取非常容易，但是具体怎么抓取，然后如何放是难题）
     3.获取隐藏js事件触发的数据内容。（这个数据必须借助无头浏览器，模拟实际操作，包括点击，录入等）   