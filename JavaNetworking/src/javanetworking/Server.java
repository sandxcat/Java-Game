package javanetworking;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
	public static Thread serverThread;
	public static boolean isHosting;
	public static ServerSocket serverSocket;
	public static Socket socket;
	public static boolean bruh = true;
	public void run(){
        du.log("Server run() called.");
	    serverSocket = null;
        try {
            serverSocket = new ServerSocket(2345);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
		System.out.println("ServerSocket: " + serverSocket);
		isHosting = true;
        du.log(isHosting+"");
		while (true) {
			socket = null;
			try {
				socket = serverSocket.accept();
				if(bruh){
					JavaNetworking.gh.gameState = GameHandler.GameState.Game;
				}
				else{
					socket.close();
                    serverThread.interrupt();
					//break;
				}
				
				System.out.println("A new Client is connected: " + socket);

				DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

				System.out.println("Assigning new thread for this Client");

				Thread t = new MultiClientHandler(socket, dis, dos);
				t.start();
			} catch (Exception e) {
                e.printStackTrace();
				try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
			}
		}
	}

	public static void stopServer(){
		try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		bruh = false;
	}
	
}

//clienthandler class
class MultiClientHandler extends Thread {
	final DataInputStream dis;
	final DataOutputStream dos;
	final Socket socket;

	// constructor
	public MultiClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
		this.socket = s;
		this.dis = dis;
		this.dos = dos;
	}

	public void run() {
		String received;
		while (true) {
			try {
				dos.writeUTF("Send message to server (Type Exit to terminate connection)");
				received = dis.readUTF();

				if (received.equals("Exit")) {
					System.out.println("Client " + this.socket + " sends exit...");
					System.out.println("Closing the connection");
					this.socket.close();
					System.out.println("Connection Closed");
					break;
				}
				dos.writeUTF(received);
				System.out.println("Response of client: " + received);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		try {
			this.dis.close();
			this.dos.close();
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}
}