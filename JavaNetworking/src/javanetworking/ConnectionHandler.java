package javanetworking;

import java.io.IOException;

public class ConnectionHandler {
    public final int portNumber = 5657;
    public static GameHandler gh;

    public boolean isConnecting = false;
    public boolean isClient;

    public ConnectionHandler(GameHandler _gh) {
        gh = _gh;
    }

    public void connect(String string, String host) {
        if (string == "Host") {
            if (!isConnecting) {
                try {
                    isConnecting = true;
                    ServerHandler.recieveConnect(portNumber); // 5657
                    if (ServerHandler.connected) {
                        isClient = false;
                        gh.gameState = GameHandler.GameState.Game;
                        isConnecting = false;
                    }
                } catch (IOException ex) {
                    System.out.println("Cannot host server: " + ex.getMessage());
                    isConnecting = false;
                }

            }

        } else if (string == "Connect") {
            if (!isConnecting) {
                try {
                    isConnecting = true;
                    ClientHandler.connect(host, portNumber);
                    if (ClientHandler.connected) {
                        isClient = true;
                        gh.gameState = GameHandler.GameState.Game;
                        isConnecting = false;
                    }
                } catch (IOException ex) {
                    System.out.println("Cannot join server: " + ex.getMessage());
                    isConnecting = false;
                }
            }

        }
    }
}
