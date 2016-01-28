# *custom-logger* 说明 #
***2016/01/28 星期四 12:36:57***

----------
**在日常程序调试中需要自己定义一些日志在指定位置，custom-logger可以快速构建一个可标识的、分目录的日志工具。**

## 使用方法 ##
### 配置日志 ###
	    // 配置日志
		final LogConfig conf=new LogConfig();
		conf.setSubDirectory(true);// 是否分目录
		conf.setLogId("20160128");// 日志id
		conf.setLogDirectory("e:\\log");// 日志主目录, 依赖'分目录'和'日志id'
### 初始化日志 ###
	    Log.setConf(conf);
	    Log.init();
`只初始化一次`
### 使用日志打印 ###
	    Log.debug("测试");
### 效果 ###
	    [hadoop.Gongice-PC] ➤ pwd
	    /drives/e/log
	                                                                                                                                           
	    [hadoop.Gongice-PC] ➤ tree
	    .
	    +--- 20160128
	    |   +--- 20160128-debug.log
	    |   +--- 20160128-error.log
	    |   +--- 20160128-info.log
	    |   +--- 20160128-warn.log
	                                                                                                                                            
	    [hadoop.Gongice-PC] ➤ more 20160128/20160128-debug.log
	    2016-01-28 11:42:10 测试
