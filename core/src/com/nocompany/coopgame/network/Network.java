package com.nocompany.coopgame.network;

import java.io.IOException;
import java.net.InetAddress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.nocompany.coopgame.network.protocol.SomeRequest;

public class Network{

	private static Network instance;
	private int udpPort = 54555;
	private int tcpPort = 54777;
	
	private Server server;
	private Client client;
	
	private SomeRequest someRequest;
	
	public static Network getInstance() {
		if(instance == null)
			instance = new Network();		
		return instance;
	}
	
	private Network() {
		client = new Client();
	}

	public void CreateServer() {
		
		server = new Server();
		server.start();
		
	    try {
			server.bind(udpPort, tcpPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    	    
	    Kryo kryo = server.getKryo();
	    kryo.register(SomeRequest.class);
	    kryo.register(Vector2.class);

	    server.addListener(new Listener() {
	    		@Override
	    		public void received(Connection arg0, Object arg1) {	    			
	    			if(arg1 instanceof SomeRequest) {
	    				someRequest = (SomeRequest)arg1;
	    			}
	    		}
	    });
	    
	}
	
	public SomeRequest getRequest() {
		return someRequest;
	}
	
	public void connectClient() throws IOException {

	    client.start();

	    	client.addListener(new Listener() {
	    	
	    	@Override
	    	public void connected(Connection arg0) {
	    		super.connected(arg0);
	    		System.out.println(arg0.isConnected());
	    	}
	    	
    		@Override
    		public void received(Connection arg0, Object arg1) {
    			super.received(arg0, arg1);
			System.out.println("input client");

    			if(arg1 instanceof SomeRequest) {
    				someRequest = (SomeRequest)arg1;
    			}
    		}
    		
	    });
	    	
	    	Kryo kryo = client.getKryo();    
	    kryo.register(SomeRequest.class);
	    kryo.register(Vector2.class);

	    client.connect(5000, "127.0.0.1", udpPort, tcpPort);
	       
	}
	
	public void sendData(SomeRequest sr) {
		
		if(server != null) {
			server.sendToAllUDP(sr);
		}else {
			client.sendUDP(sr);
		}
	
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

	public InetAddress GetAdr() {
		return client.discoverHost(udpPort, tcpPort);
	}

	
}
