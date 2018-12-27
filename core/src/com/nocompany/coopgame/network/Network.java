package com.nocompany.coopgame.network;

import java.io.IOException;
import java.net.InetAddress;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;

public class Network{

	private static Network instance;
	private int udpPort = 54555;
	private int tcpPort = 54777;
	
	private Server server;
	private Client client;
	
	public static Network getInstance() {
		if(instance == null)
			instance = new Network();
		
		return instance;
	}
	
	private Network() {
		client = new Client();
	}

	public void CreateServer() {
		Gdx.app.postRunnable(new Runnable() {
			
			@Override
			public void run() {
				server = new Server();
			    server.start();
			    try {
					server.bind(udpPort, tcpPort);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	public void DestroyServer() {
		if(server!=null) {
			server.stop();
			System.out.println("server stopped");
		}
	}
	
	public java.util.List<InetAddress> GetAdrList() {
		return client.discoverHosts(udpPort, tcpPort);
	}

}
