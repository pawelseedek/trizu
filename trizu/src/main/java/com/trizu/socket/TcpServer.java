package com.trizu.socket;

import org.springframework.stereotype.Component;

import com.trizu.Immutable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Component
public class TcpServer implements Server, Connection.Listener {
	
    private ServerSocket serverSocket;
    private volatile boolean isStop;
    private List<Connection> connections = new ArrayList<>();
    private List<Connection.Listener> listeners = new ArrayList<>();
	private Immutable immutable = new Immutable();
    
    public TcpServer() {	
    	try {
			serverSocket = new ServerSocket(4444);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
    @Override
    public void start() {
        new Thread(() -> {
            while (!isStop) {
                try {
                    Socket socket = serverSocket.accept();
                    if (socket.isConnected()) {
                        TcpConnection tcpConnection = new TcpConnection(socket);
                        tcpConnection.start();
                        tcpConnection.addListener(this);
                        connected(tcpConnection);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void stop() {
        isStop = true;
    }
	
	@Override
    public int getConnectionsCount() {
        return connections.size();
    }

    @Override
    public List<Connection> getConnections() {
        return connections;
    }

    @Override
    public void addListener(Connection.Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void messageReceived(Connection connection, Object message) {
    	String msg = new String((byte[]) message);
    	if(msg.contains(immutable.HOUSEID)) {
    		connection.setId(msg.split("\\.")[1]);
			connection.setPassword(msg.split("\\.")[2]);
    	}else {
            for (Connection.Listener listener : listeners) {
                listener.messageReceived(connection, message);
            }
    	}
    }

    @Override
    public void connected(Connection connection) {
		connections.add(connection);
        for (Connection.Listener listener : listeners) {
            listener.connected(connection);
        }
    }

    @Override
    public void disconnected(Connection connection) {
		connections.remove(connection);
        for (Connection.Listener listener : listeners) {
            listener.disconnected(connection);
        }
    }
    
    public Connection getConnectionByHouseId(String houseid) {
    	for(Connection c : this.connections) {
    		if(c.getId().equals(houseid)) return c;
    	}
    	return null;
    }
}
