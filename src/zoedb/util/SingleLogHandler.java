package zoedb.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;

public class SingleLogHandler {
	
	private static SingleLogHandler instance;
	private static FileHandler handler;
	
	private SingleLogHandler() {
		try {
			handler = new FileHandler("zoedb.log", true);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		SimpleFormatter formatter = new SimpleFormatter();
		handler.setFormatter(formatter);
	}
	
	public static SingleLogHandler getInstance() {
		if(instance == null) {
			instance = new SingleLogHandler();
		}
		return instance;
	}
	
	public FileHandler getHandler() {
		return handler;
	}

}
