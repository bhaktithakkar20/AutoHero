package resources.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtils {

	public static Logger instanceLogger = null;
	public static FileHandler logFileHandler;
	private static String currentPackage = "";
	private static String currentClass = "";

	public static void setupLogger(String logFilePath, String moduleName, String className)
			throws SecurityException, IOException {
		try {

			Logger loggerInstance = null;
			FileHandler logFileHandler = null;

			Class[] sParamLogger = new Class[1];
			sParamLogger[0] = Logger.class;
			Class[] sParamHandler = new Class[1];
			sParamHandler[0] = FileHandler.class;
			Class<?> classObj = Class.forName(className);

			Object obj = classObj.newInstance();
			loggerInstance = Logger.getLogger(className);

			File logDir = new File(logFilePath).getParentFile();
			if (!(logDir.exists()))
				logDir.mkdir();

			logFileHandler = new FileHandler(logFilePath);

			if (loggerInstance != null) {

				loggerInstance.setUseParentHandlers(false);
				loggerInstance.addHandler(logFileHandler);
				SimpleFormatter formatter = new SimpleFormatter();
				logFileHandler.setFormatter(formatter);

				Field fieldLogger = classObj.getDeclaredField("instanceLogger");
				fieldLogger.setAccessible(true);

				Field fieldFileHandler = classObj.getDeclaredField("logFileHandler");
				fieldFileHandler.setAccessible(true);

				Method methodLogger = classObj.getDeclaredMethod("setInstanceLogger", sParamLogger);
				methodLogger.invoke(obj, loggerInstance);

				Method methodHandler = classObj.getDeclaredMethod("setLogFileHandler", sParamHandler);
				methodHandler.invoke(obj, logFileHandler);

				loggerInstance.info("Logger setup for module:" + moduleName);

			} else {
				System.out.println("[LoggerUtils] : {setupLogger} loggerInstance is set to Null for class" + className
						+ "module" + moduleName);
			}
		} catch (Exception e) {
			System.out.println(
					"[LoggerUtils] : {setupLogger} execption generated while  setting up logger" + e.getMessage());

		}
	}

}
