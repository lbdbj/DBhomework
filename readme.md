## 大作业开发文档
#### 一. 整体架构（各个包的功能）
###### 1. entity
*用来自定义各种需要的对象。*

比如每一篇文章就是一个ArticleInfo的对象，包括
* authors作者（注意不止一个）
* title 标题
* journal 出版物名字
* volume 卷号
* year 出版年份
* pages 所在页数
* ee 文章链接

###### 2. gui.listener
*用来定义不同界面按钮的点击事件。*

首先所有类必须实现ActionListener接口并重写actionPerformed方法。然后利用单例模式获得界面类的实例（instance)编写相应点击事件即可。
###### 3. gui.model
*用来定义界面中表格所要显示的内容。* 

首先自定义的表格类必须实现TableModel接口，并重写相应的方法。具体可参照InfTableModel类的写法或者自行百度。
###### 4. gui.panel
*用来编写不同界面。* 

每个界面都对应一个类，不需要太多改动，如果界面中有按钮只需要在构造函数中给按钮添加点击事件函数即可。如果想要启动程序运行MainPanel类即可。
###### 5. service
*前后端的枢纽，用来进一步集成工具类中的函数，方便在界面类中调用。*

在本程序中其实就是比如你要实现一个功能需要调用工具类中的很多函数，你可以把调用很多函数的代码封装成一个Service类，方便前端调用。
###### 6. Util
*核心包，包含了所有的工具函数。*

因为大作业不允许使用数据库，所以核心的数据结构代码都放到了工具类中，同时还包含了界面工具类和解析xml文件的工具类。

#### 二.功能实现
###### 1. 基本搜索

* 主要逻辑

将xml文件中的信息转换成想要的格式并保存到新的文件（srcfile）中。为标题名和作者名分别建立索引文件（tilteindex和authorindex），每次搜索先从索引文件找到文章在srcfile中的位置，再从srcfile中将文章信息取出。

* 具体实现

    1. __说明：__由于xml文件中的信息一致性较差，所以只选用了主标记为article和inproceedings的470多万条文章信息。

    2. 解析出的srcfile格式为
      	* !author\r\n
      	* *title\r\n
      	* @journal/booktitle\r\n
      	* #volume\r\n
      	* $year\r\n
      	* %pages\r\n
      	* ^ee\r\n
    
    3. 如果一篇文章没有超过500字节就将它用空格填满500个字节同时存放到srcfile1文件中，如果超过500字节就把它用空格填满成5000个字节同时存到srcfile2文件中。

    4. 作者搜索实现原理（标题搜索实现原理类似不再赘述）
    __注意：__因为采用的是开哈希，所以作者索引文件会有两个，高精度索引文件hash映射的长度会更长但同时每个hashcode的存储空间会更小，更不容易发生哈希冲突。低精度索引文件则相反。下面有时会出现两个类似的函数，比如setPageContent(int index)和setPageContent2(int index)代表分别对高精度和低精度索引文件操作。
       ①getAuthorPos(String str, int len)函数可以根据指定的作者名和映射长度，利用哈希算法返回指定的hashcode,这个hashcode就是对应文章在srcfile中的位置。此处哈希算法借用了JDK中的哈希算法，具体请自行搜索查看原理。
       
       ②setPageContent(int index)和setPageContent2(int index)设置每页的内容。也就是比如hashcode为1的所有文章在srcfile中的位置称为一页。
       
       ③setFile()和setFile2()，向两个作者索引文件中写入数据。
       
       ④getArticleByFile(String author, RandomAccessFile rafAuthor1, RandomAccessFile rafSrc1,RandomAccessFile rafAuthor2,RandomAccessFile rafSrc2)函数。通过给定的作者名获取所有的文章信息并返回一个list。里面用到了SrcFileUtil的judgeAuthor函数和formatArticle函数。
       
       ⑤SrcFileUtil的judgeAuthor函数可以判断指定位置的文章的作者是否包含给定的作者。
       formatArticle函数可以把从srcfile文件中取出的文章信息转换为ArticleInfo对象。
    
