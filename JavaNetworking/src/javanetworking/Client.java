package javanetworking;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread{
	public static Thread clientThread;
	public static boolean isSearching;
	public void run(){
			try {
				isSearching = true;
				Scanner sc = new Scanner(System.in);
				InetAddress ip = InetAddress.getByName("localhost");
				Socket s = new Socket(ip,2345);
				
				DataInputStream dis= new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				
				JavaNetworking.gh.gameState = GameHandler.GameState.Game;
				while(true) {
					du.log(dis.readUTF());
					String tosend=sc.nextLine();
					dos.writeUTF(tosend);
					
					if(tosend.equals("Exit")) {
						s.close();
						du.log("Connection Closed");
						break;
					}
					String received = dis.readUTF();
					du.log("Server Says: "+ received);
				}
				sc.close();
				dis.close();
				dos.close();
			}
			catch(Exception e) {
				System.out.println("Client Error"+e);
			}
		}
	}