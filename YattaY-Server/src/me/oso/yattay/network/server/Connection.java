package me.oso.yattay.network.server;

import java.io.*;
import java.net.*;

/**
 * Connexion.java
 * 
 * @author Ibanez Thomas
 * @date 21 sept. 2016
 */
public class Connection extends Thread {

	private Socket socket;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;

	public Connection(Socket socket) {
		this.socket = socket;
		this.setName("Connection with " + socket.getInetAddress());
	}

	@Override
	public void run() {
		try {
			inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outToClient = new DataOutputStream(socket.getOutputStream());
			String message = "";

			while (!socket.isClosed()) {
				message = inFromClient.readLine();
				if(message == null || message == "-1") {
					shutDown();
				}
				Server.getLog().info(message+" from "+socket.getInetAddress());
			}
		} catch (IOException e1) {
			//Let the socket close
		}
	}

	public void shutDown() {
		try {
			socket.close();
			inFromClient.close();
			outToClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Socket getSocket() {
		return socket;
	}
}