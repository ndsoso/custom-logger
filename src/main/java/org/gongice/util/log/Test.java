/**
* @author: gsw
* @version: 1.0
* @CreateTime: 2016年1月28日 上午9:45:17
* @Description: 无
*/
package org.gongice.util.log;
public class Test {

	//private final static Log log=new Log();

	public static void main(String[] args) {

		// 配置日志
		final LogConfig conf=new LogConfig();
		conf.setSubDirectory(true);// 是否分目录
		conf.setLogId("20160128");// 日志id
		conf.setLogDirectory("E:\\log");// 日志主目录, 依赖'分目录'和'日志id'

		//Log.setConf(conf);
		//Log.init();


		// 线程1 初始化
		new Thread(new Runnable() {
			public void run() {
				Log.setConf(conf);
				Log.init();
				System.out.println("初始化完成");
				/*while (true) {
					Log.debug("线程1 测试");
				}*/
			}
		}).start();

		// 线程2
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					Log.debug("线程2 测试");
				}
			}
		}).start();
	}

}
