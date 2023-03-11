# spring基础100问

## 第一章

1. springMVC和三层架构的三层是一个东西吗？MVC只解决了视图层。model, view, controller，用来解决用户的请求和响应问题。model用来交互，controller进行调用和返回，view进行展示。
2. springMVC的流程是什么？
3. 模板引擎的标准表达式有什么用？页面数据的动态替换。

![image-20220915171851451](D:\blgs\source\imgs\image-20220915171851451.png)

4. spring的配置类，各种properties还有各种configration文件。
5. JavaWeb的内置类。 httpRequest, HttpResponse，以及dispacher属性。
6. response返回的是一个html,那么采用vue之后，应该采用什么格式返回呢？后端返回Json。后端返回json，前端项目返回Html。同时后端只可以通过post进行获取。
7. restful风格的调用方式通常由什么？实现了**资源名称本身**和**资源的描述**之间的解耦。post和get属于资源的描述。



![image-20220915175445994](D:\blgs\source\imgs\image-20220915175445994.png)

![image-20220915180152286](D:\blgs\source\imgs\image-20220915180152286.png)

8. thymeleaf的自动配置应该从哪里可以看到？也就是说含有哪些可以设置的东西？应该从哪里找到呢？各种`properties`文件中。

![image-20220915215627706](D:\blgs\source\imgs\image-20220915215627706.png)

9. devtols的正确使用方式：直接build项目，就直接进行热交换了。核心方式就是把类和类加载器进行卸载和重新加载，不用重新启动容器了，节省了时间。
10. mybatis怎么配置数据库？基本流程 + 注意事项。 
    1. 引入依赖
    2. 配置configuration.xml，含有数据源的相关信息。注意默认的路径是resources文件夹。
    3. 创建mapper.java和mapper.xml。xml中的命名空间为java文件
    4. 直接使用模板代码读取，然后创建factory和session，最后进行
11. maven工程中的配置文件是通过什么进行读取的？把文件放在类路径下面了，从target里面可以看得到的。
12. springboot如何配置双数据库？[jdbc配置](https://www.liaoxuefeng.com/article/1127277451217344),[mybatis配置](https://www.cnblogs.com/niumoo/p/14209663.html), [mybatis配置2](https://blog.csdn.net/weixin_39835887/article/details/84921565)
    1. 写配置文件
    2. 创建配置类`@MapperScan来配置MyBatis中的.java文件`, `sqlsessionFactory`来配置xml文件
    3. 创建配置模板
    4. 进行引用
13. mybatis的xml和java文件的关系，xml文件相当于一个java接口的实现类。必然使用了反射技术，所以称之为逆向工程。就是把xml文件映射到了java文件。
14. @ContextConfiguration和RunWith(class = )的作用
15. org.junit.runners.model.InvalidTestClassError: Invalid test class 这个错误是由于导入的Junit包错误导致的！应该直接导入Junit.Test类；不是api.Test类。
16. dirverClass这个错误是由于配置双数据库的时候，应该使用Jdbc-url而不是简单的url。
17. 如果.xml没配置好，报'bind'错误。如果没有加上mapScan，报错 No qualifying bean of type

18. 对于Test，如果没有配合Junit以及@Test使用，没法有效果。另外如果想看真实的运行环境，应该加上RunWith(SpringRun.class)以及ContextConfiguration(Demo1.class)
19. yml和properties的优先级问题[参考文章](https://blog.csdn.net/qq_40837310/article/details/105981765)
20. properties和xml文件的优先级问题， xml文件大于properities文件。
21. propeties和Configuration配置的优先级问题，configuration大于properties文件。
22. @Param注解的意义是什么呢？
23. @PathParam和PathVariable还有@RequestParam,都是获取get中的参数的。用法不同而已。都是用来接收参数的。
24. 命名问题：就算不同的数据源不在同一个包下面，也不能出现同一个mapper类。
25. 出现加载不了Application错误原因：配置文件错误(xml和properties文件或者config类错误)
26. thymeleaf模板引擎报错，Url is not registered。 修改设置里面的language中的Schemes
27. 什么是EL表达式？EL是为了前端获方便取后端的值而设计的一套表达式。使用它可以方便的获取的后端的数据。
28. cookies跨域无法请求，用户头像无法显示？更新浏览器后，跨域请求有问题。
29. th:的相关语法
30. th:utext和th:text的区别，utext会解析后端返回的html的标签而text不会[参考博客](https://blog.csdn.net/rongxiang111/article/details/79678765)

```html
<!-- 分页 -->

<nav class="mt-5" th:if="${page.rows > 0}">
	<ul class="">
        <li class="">
        	<a class="" th:href="@{${page.path}(current=1)}">首页</a>
        </li>
        <li class="page-item ${page.current==1?'disabled':''}|">
        	<a class="page-link" th:href="@{${page.path}(current=${page.current-1})}"> 上一页</a>
        </li>
    </ul>
</nav>

				<nav class="mt-5" th:if="${page.rows > 0}">
					<ul class="pagination justify-content-center">
						<li class="page-item">
							<a class="page-link" th:href="@{${page.path}(current=1)}">首页</a>
						</li>
						<li th:class="|page-item ${page.current==1?'disabled':''}|">
							<a class="page-link" th:href="@{${page.path}(current=${page.current-1})}">上一页</a></li>
						<li th:class="|page-item ${i==page.current?'active':''}|" th:each="i:${#numbers.sequence(page.from,page.to)}">
							<a class="page-link" th:href="@{${page.path}(current=${i})}" th:text="${i}">1</a>
						</li>
						<li th:class="|page-item ${page.current==page.total?'disabled':''}|">
							<a class="page-link" th:href="@{${page.path}(current=${page.current+1})}">下一页</a>
						</li>
						<li class="page-item">
							<a class="page-link" th:href="@{${page.path}(current=${page.total})}">末页</a>
						</li>
					</ul>
				</nav>
```







## 第二章

1. 邮件发送的核心组件 

- ```
  MineMessage
  ```

- ```
  MineMessageHelper
  ```

- ```
  TemplateEngine
  ```

- ```
  JavaMailSender
  ```

