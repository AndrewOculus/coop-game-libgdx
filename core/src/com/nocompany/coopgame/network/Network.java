package com.nocompany.coopgame.network;

import java.io.IOException;
import java.net.InetAddress;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.nocompany.coopgame.network.protocol.InstantiateRequest;
import com.nocompany.coopgame.network.protocol.UpdatePackage;

public class Network{

	private static Network instance;
	private int udpPort = 54555;
	private int tcpPort = 54777;
	
	private Server server;
	private Client client;
	
	private UpdatePackage someRequest;
	private InstantiateRequest instantiateRequest;
	
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
	    	    
    		reqisterItems(server);

    		listenUpdates(server);
	    
	}
	
	public UpdatePackage getRequest() {
		return someRequest;
	}
	
	public InstantiateRequest getInstantiateRequest() {
		InstantiateRequest iRequest = instantiateRequest;
		instantiateRequest = null;
		return iRequest;
	}
	
	public void connectClient() throws IOException {

	    client.start();

	    listenUpdates(client);
	    	
	    	reqisterItems(client);

	    client.connect(5000, "127.0.0.1", udpPort, tcpPort);
	       
	}
	
	public void listenUpdates(EndPoint endPoint) {
		endPoint.addListener(new Listener() {
	    	
	    	@Override
	    	public void connected(Connection connection) {
	    		super.connected(connection);
	    		System.out.println(connection.isConnected() + " " + connection.toString());
	    		InstantiateRequest instantiateRequest = new InstantiateRequest();
	    		registerHero(instantiateRequest);
	    	}
	    	
    		@Override
    		public void received(Connection arg0, Object arg1) {
    			super.received(arg0, arg1);

    			if(arg1 instanceof UpdatePackage) {
    				someRequest = (UpdatePackage)arg1;
    			}
    			
    			if(arg1 instanceof InstantiateRequest) {
    				instantiateRequest = (InstantiateRequest)arg1;
    			}
    			
    		}
    		
    		@Override
    		public void disconnected(Connection arg0) {
    			// TODO Auto-generated method stub
    			super.disconnected(arg0);
    			
    			
    		}
    		
	    });
	}
	
	public void reqisterItems(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();    
	    kryo.register(UpdatePackage.class);
	    kryo.register(Vector2.class);
	    kryo.register(String.class);
	    kryo.register(InstantiateRequest.class);
	}
	
	public void registerHero(InstantiateRequest instantiateRequest) {
		sendData(instantiateRequest);
	}
	
	public void updateHero(UpdatePackage updatePackage) {
		sendData(updatePackage);
	}
	
	private void sendData(Object sr) {
		
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
