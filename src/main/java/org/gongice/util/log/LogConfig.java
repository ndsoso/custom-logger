/**
 * @author: gsw
 * @version: 1.0
 * @CreateTime: 2016年1月28日 上午9:42:38
 * @Description: 无
 */
package org.gongice.util.log;

import java.io.File;

public class LogConfig {
	// 日志主目录
	private String logDirectory = "/home/stormdev/storm/logs/";
	// 日志id
	private String logId = "test";
	// 日志模式
	private String debugPattern = "%{time:yyyy-MM-dd HH:mm:ss} %{message}\\n%{throwable}";
	private String infoPattern = "%{time:yyyy-MM-dd HH:mm:ss} %{message}\\n%{throwable}";
	private String warnPattern = "%{time:yyyy-MM-dd HH:mm:ss} %{message}\\n%{throwable}";
	private String errorPattern = "%{time:yyyy-MM-dd HH:mm:ss} [%{method}] %{message}\\n%{throwable}";
	// 日志路径
	private String debugLogPath = logDirectory + "debug.log";
	private String infoLogPath = logDirectory + "info.log";
	private String warnLogPath = logDirectory + "warn.log";
	private String errorLogPath = logDirectory + "error.log";
	// 日志大小控制
	private long maxDebugSize = 10240;
	private long maxInfoSize = 10240;
	private long maxWarnSize = 10240;
	private long maxErrorSize = 10240;
	// 是否以id分目录
	private boolean subDirectory=true;
	private boolean debugEnabled=true;

	public String getLogDirectory() {
		return logDirectory;
	}

	public void setLogDirectory(String logDirectory) {
		if (!logDirectory.endsWith(File.separator)) logDirectory = logDirectory + File.separator;
		if (subDirectory) logDirectory = logDirectory + this.logId+ File.separator;
		this.logDirectory = logDirectory;
		this.debugLogPath = logDirectory + this.logId + "-" + "debug.log";
		this.infoLogPath = logDirectory + this.logId + "-" + "info.log";
		this.warnLogPath = logDirectory + this.logId + "-" + "warn.log";
		this.errorLogPath = logDirectory + this.logId + "-" + "error.log";
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getDebugPattern() {
		return debugPattern;
	}

	public void setDebugPattern(String debugPattern) {
		this.debugPattern = debugPattern;
	}

	public String getInfoPattern() {
		return infoPattern;
	}

	public void setInfoPattern(String infoPattern) {
		this.infoPattern = infoPattern;
	}

	public String getWarnPattern() {
		return warnPattern;
	}

	public void setWarnPattern(String warnPattern) {
		this.warnPattern = warnPattern;
	}

	public String getErrorPattern() {
		return errorPattern;
	}

	public void setErrorPattern(String errorPattern) {
		this.errorPattern = errorPattern;
	}

	public String getDebugLogPath() {
		return debugLogPath;
	}

	public String getInfoLogPath() {
		return infoLogPath;
	}

	public String getWarnLogPath() {
		return warnLogPath;
	}

	public String getErrorLogPath() {
		return errorLogPath;
	}

	public long getMaxDebugSize() {
		return maxDebugSize;
	}

	public void setMaxDebugSize(long maxDebugSize) {
		this.maxDebugSize = maxDebugSize;
	}

	public long getMaxInfoSize() {
		return maxInfoSize;
	}

	public void setMaxInfoSize(long maxInfoSize) {
		this.maxInfoSize = maxInfoSize;
	}

	public long getMaxWarnSize() {
		return maxWarnSize;
	}

	public void setMaxWarnSize(long maxWarnSize) {
		this.maxWarnSize = maxWarnSize;
	}

	public long getMaxErrorSize() {
		return maxErrorSize;
	}

	public void setMaxErrorSize(long maxErrorSize) {
		this.maxErrorSize = maxErrorSize;
	}

	public boolean isDebugEnabled() {
		return debugEnabled;
	}

	public void setDebugEnabled(boolean debugEnabled) {
		this.debugEnabled = debugEnabled;
	}

	public boolean isSubDirectory() {
		return subDirectory;
	}

	public void setSubDirectory(boolean subDirectory) {
		this.subDirectory = subDirectory;
	}
	
	
	
}
