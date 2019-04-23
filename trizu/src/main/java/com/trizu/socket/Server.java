package com.trizu.socket;


import java.util.List;

public interface Server {
    int getConnectionsCount();
	Connection getConnectionByHouseId(String houseid);
    void start();
    void stop();
    List<Connection> getConnections();
    void addListener(Connection.Listener listener);
}
