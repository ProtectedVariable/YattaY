package me.oso.yattay.server.core;

import java.io.*;
import java.util.logging.*;

import me.oso.yattay.server.file.ConfigParser;
import me.oso.yattay.server.network.Connection;
import me.oso.yattay.server.network.NetListener;

/**
 * Server.java
 * 
 * @author Ibanez Thomas
 * @date 21 sept. 2016
 */
public class Server {

	private static Logger log;
	private NetListener netListener;
	private ConfigParser config;
	private final String CONF_FILE = "server.conf";
	
	public Server() {
		this.config = new ConfigParser(CONF_FILE);
		this.netListener = new NetListener(this.config.getAttribute("bind-address"), Integer.parseInt(this.config.getAttribute("bind-port")));
	}

	public void start() {
		log.info("Server started");
		this.netListener.start();
		try {
			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(System.in));
			String input;
			while (true) {
				input = br.readLine();
				if(input.equals("stop")) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		dispose();
	}

	public void dispose() {
		log.info("Shutting down server...");
		this.netListener.setRunning(false);
		for (Connection connection : this.netListener.getConnections()) {
			connection.shutDown();
		}
		try {
			this.netListener.getCoSocket().close();
		} catch (IOException e) {
			//Log nothing because an IOEx at this point is normal
		}
		log.info("Server stopped !");
	}

	public static void main(String[] args) {
		log = Logger.getLogger("YattaY Server");
		log.setLevel(Level.ALL);
		log.setUseParentHandlers(false);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.ALL);
		handler.setFormatter(new ServerFormatter());
		log.addHandler(handler);

		new Server().start();
	}

	public static Logger getLog() {
		return log;
	}

}