package org.gongice.util.log;

import org.gongice.util.log.Hierarchy;
import org.gongice.util.log.LogTarget;
import org.gongice.util.log.Logger;
import org.gongice.util.log.Priority;
import org.gongice.util.log.format.ExtendedPatternFormatter;
import org.gongice.util.log.output.io.StreamTarget;
import org.gongice.util.log.output.io.rotate.RevolvingFileStrategy;
import org.gongice.util.log.output.io.rotate.RotateStrategyBySize;
import org.gongice.util.log.output.io.rotate.RotatingFileTarget;

import java.io.File;
import java.io.IOException;

/**
 * Simple wrapper to the incorporated LogKit to log under a single logging name.
 *
 * @author Bruce Ritchie
 */
public class Log {

	private static final Logger debugLog = Hierarchy.getDefaultHierarchy().getLoggerFor("eng-DEBUG");
	private static final Logger infoLog = Hierarchy.getDefaultHierarchy().getLoggerFor("eng-INFO");
	private static final Logger warnLog = Hierarchy.getDefaultHierarchy().getLoggerFor("eng-WARN");
	private static final Logger errorLog = Hierarchy.getDefaultHierarchy().getLoggerFor("eng-ERR");
	
	private static boolean debugEnabled;

	private static LogConfig conf;// 配置文件
	public static void setConf(LogConfig conf) {
		Log.conf = conf;
	}

	/**
	 * 初始化方法
	 * 
	 */
	public static void init() {
		final String logDirectory = conf.getLogDirectory();
		final File logDir = new File(logDirectory);
		if (!logDir.exists()) {
			logDir.mkdirs();
			//System.out.println("创建LOG主目录");
		}
		debugEnabled = conf.isDebugEnabled();

		createLogger(conf.getDebugPattern(), conf.getDebugLogPath(), conf.getMaxDebugSize(), debugLog,Priority.DEBUG);
		createLogger(conf.getInfoPattern(), conf.getInfoLogPath(), conf.getMaxInfoSize(), infoLog,Priority.INFO);
		createLogger(conf.getWarnPattern(), conf.getWarnLogPath(), conf.getMaxWarnSize(), warnLog,Priority.WARN);
		createLogger(conf.getErrorPattern(), conf.getErrorLogPath(), conf.getMaxErrorSize(), errorLog,Priority.ERROR);
	}

	private static void createLogger(String pattern, String logName,long maxLogSize, Logger logger, Priority priority) {
		// debug log file
		ExtendedPatternFormatter formatter = new ExtendedPatternFormatter(pattern);
		StreamTarget target;
		Exception ioe = null;
		try {
			// messengerHome was not setup correctly
			if (logName == null) {
				throw new IOException("LogName was null - MessengerHome not set?");
			} else {
				RevolvingFileStrategy fileStrategy = new RevolvingFileStrategy(logName, 3);
				RotateStrategyBySize rotateStrategy = new RotateStrategyBySize(maxLogSize * 1024);
				target = new RotatingFileTarget(formatter, rotateStrategy,fileStrategy);
			}
		} catch (IOException e) {
			ioe = e;
			// can't log to file, log to stderr
			target = new StreamTarget(System.err, formatter);
		}
		logger.setLogTargets(new LogTarget[] { target });
		logger.setPriority(priority);
		if (ioe != null) {
			logger.debug("Error occurred opening log file: " + ioe.getMessage());
		}
	}

	public static boolean isErrorEnabled() {
		return errorLog.isErrorEnabled();
	}

	public static boolean isFatalEnabled() {
		return errorLog.isFatalErrorEnabled();
	}

	public static boolean isDebugEnabled() {
		return debugEnabled;
	}

	public static void setDebugEnabled(boolean enabled) {
		debugEnabled = enabled;
	}

	public static boolean isInfoEnabled() {
		return infoLog.isInfoEnabled();
	}

	public static boolean isWarnEnabled() {
		return warnLog.isWarnEnabled();
	}

	public static void debug(String s) {
		if (isDebugEnabled()) {
			debugLog.debug(s);
		}
	}

	// public static void ctrl(String s) {
	// ctrlLog.info(s);
	// }

	public static void debug(Throwable throwable) {
		if (isDebugEnabled()) {
			debugLog.debug("", throwable);
		}
	}

	public static void debug(String s, Throwable throwable) {
		if (isDebugEnabled()) {
			debugLog.debug(s, throwable);
		}
	}


	public static void rotateDebugLogFile() {
		RotatingFileTarget target = (RotatingFileTarget) debugLog
				.getLogTargets()[0];
		try {
			target.rotate();
		} catch (IOException e) {
			System.err
					.println("Warning: There was an error rotating the Jive debug log file. "
							+ "Logging may not work correctly until a restart happens.");
		}
	}

	public static void info(String s) {
		if (isInfoEnabled()) {
			infoLog.info(s);
		}
	}

	public static void info(Throwable throwable) {
		if (isInfoEnabled()) {
			infoLog.info("", throwable);
		}
	}

	public static void info(String s, Throwable throwable) {
		if (isInfoEnabled()) {
			infoLog.info(s, throwable);
		}
	}

	
	public static void rotateInfoLogFile() {
		RotatingFileTarget target = (RotatingFileTarget) infoLog
				.getLogTargets()[0];
		try {
			target.rotate();
		} catch (IOException e) {
			System.err
					.println("Warning: There was an error rotating the Jive info log file. "
							+ "Logging may not work correctly until a restart happens.");
		}
	}

	public static void warn(String s) {
		if (isWarnEnabled()) {
			warnLog.warn(s);
		}
	}

	public static void warn(Throwable throwable) {
		if (isWarnEnabled()) {
			warnLog.warn("", throwable);
		}
	}

	public static void warn(String s, Throwable throwable) {
		if (isWarnEnabled()) {
			warnLog.warn(s, throwable);
		}
	}


	public static void rotateWarnLogFile() {
		RotatingFileTarget target = (RotatingFileTarget) warnLog
				.getLogTargets()[0];
		try {
			target.rotate();
		} catch (IOException e) {
			System.err
					.println("Warning: There was an error rotating the Jive warn log file. "
							+ "Logging may not work correctly until a restart happens.");
		}
	}

	public static void error(String s) {
		if (isErrorEnabled()) {
			errorLog.error(s);
			if (isDebugEnabled()) {
				printToStdErr(s, null);
			}
		}
	}

	public static void error(Throwable throwable) {
		if (isErrorEnabled()) {
			errorLog.error("", throwable);
			if (isDebugEnabled()) {
				printToStdErr(null, throwable);
			}
		}
	}

	public static void error(String s, Throwable throwable) {
		if (isErrorEnabled()) {
			errorLog.error(s, throwable);
			if (isDebugEnabled()) {
				printToStdErr(s, throwable);
			}
		}
	}


	public static void rotateErrorLogFile() {
		RotatingFileTarget target = (RotatingFileTarget) errorLog
				.getLogTargets()[0];
		try {
			target.rotate();
		} catch (IOException e) {
			System.err
					.println("Warning: There was an error rotating the Jive error log file. "
							+ "Logging may not work correctly until a restart happens.");
		}
	}

	public static void fatal(String s) {
		if (isFatalEnabled()) {
			errorLog.fatalError(s);
			if (isDebugEnabled()) {
				printToStdErr(s, null);
			}
		}
	}

	public static void fatal(Throwable throwable) {
		if (isFatalEnabled()) {
			errorLog.fatalError("", throwable);
			if (isDebugEnabled()) {
				printToStdErr(null, throwable);
			}
		}
	}

	public static void fatal(String s, Throwable throwable) {
		if (isFatalEnabled()) {
			errorLog.fatalError(s, throwable);
			if (isDebugEnabled()) {
				printToStdErr(s, throwable);
			}
		}
	}

	/**
	 * Returns the directory that log files exist in. The directory name will
	 * have a File.separator as the last character in the string.
	 *
	 * @return the directory that log files exist in.
	 */
/*	public static String getLogDirectory() {
		return logDirectory;
	}*/

	private static void printToStdErr(String s, Throwable throwable) {
		// if (s != null) {
		// System.err.println(s);
		// }
		// if (throwable != null) {
		// StringWriter sw = new StringWriter();
		// PrintWriter pw = new PrintWriter(sw);
		// throwable.printStackTrace(pw);
		// System.err.print(sw.toString());
		// System.err.print("\n");
		// }
	}
}
