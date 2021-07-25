/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javanetworking;

import java.awt.*;
import java.net.*;
import java.io.*;

public class ServerHandler {
    public static GameHandler gh;

    public static boolean connected = false;
    private static ServerSocket MyServer;
    private static Socket clientSocket;
    private static OutputStream os;
    private static PrintWriter pw;
    private static BufferedReader br;

    public ServerHandler(GameHandler _gh) {
        gh = _gh;
    }

    public static void recieveConnect(int port) throws UnknownHostException, IOException, SocketTimeoutException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    MyServer = new ServerSocket(port);
                    clientSocket = MyServer.accept();
                    os = clientSocket.getOutputStream();
                    pw = new PrintWriter(os, true);
                    br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    connected = true;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                recievePieceMove();
            }
        }).start();
    }

    public static void disconnect() {
        try {
            if (clientSocket != null)
                clientSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        clientSocket = null;
        pw = null;
        br = null;
        connected = false;
        gh.gameState = GameHandler.GameState.Menu;
        JavaNetworking.reset();
    }

    public static void sendPieceMove(int val) {
        if (connected) {
            // add or modify.
            pw.println(val + ":" + -1);
            pw.flush();
        }
    }

    public static void sendDisconnect() {
        if (connected) {
            pw.println("esc");
        }
    }

    private static void recievePieceMove() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String inputLine;

                try {
                    while ((inputLine = br.readLine()) != null) {
                        try {
                            if (inputLine.equals("esc")) {
                                disconnect();
                                return;
                            }
                            // row:col:initrow:initcol
                            // add or modify.
                            int post0 = Integer.parseInt(inputLine.split(":")[0]);
                            int post1 = Integer.parseInt(inputLine.split(":")[1]);

                            // JavaNetworking.clientValue = post0;
                            ;
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        } catch (NullPointerException e) {
                            disconnect();
                        }
                    }
                } catch (SocketException e) {
                    disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static boolean isConnected() {
        return connected;
    }
}
