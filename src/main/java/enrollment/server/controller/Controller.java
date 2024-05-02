package enrollment.server.controller;

import enrollment.server.network.TCPServer;

public class Controller {
    private TCPServer tcpServer;

    public void run() {
        tcpServer = new TCPServer();
        tcpServer.connectServer();
        tcpServer.send("아아아아 되냐고!!!");
    }
}
