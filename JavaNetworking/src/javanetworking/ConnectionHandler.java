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

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (gh.gameState != GameHandler.GameState.Game) {
                                du.log("RAN CONNECTIONHANDLER THREAD");
                                if (ServerHandler.connected) {
                                    du.log("RAN CONNECTIONHANDLER THREAD IN IF STATEMENT");
                                    isClient = false;
                                    gh.gameState = GameHandler.GameState.Game;
                                    isConnecting = false;
                                }
                            }
                        }
                        
                    }).start();

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
