package communication;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Player <num> = Change the number of enemy player's cards.
 * ForceZone <n1> <c11> <c11o> ... <c1n1> <c1n1o> ... <n4> ... <c4n4o> = Enemy's force zone infomations.
 * Attack <id> <num> = Attacked the id th ForceZone with num. 
 * Block <num> = Blocked with num.
 * TurnEnd = Turn end.
 * Deck <num> = The number of deck cards.
 * Grave <mark> <num>
 */
public class SocketHandler {
	public ServerSocket ss;
	public Socket sock;
	
	public BufferedReader reader;
	public PrintWriter writer;
	
	public static final int PORT = 50000;
	
	public enum Mode{
		SERVER,
		CLIANT,
	};
	
	public SocketHandler(Mode mode, String hostName){
		try{
			switch(mode){
			case SERVER:
				ss = new ServerSocket(PORT);
				sock = ss.accept();
				break;
			case CLIANT:
				sock = new Socket(hostName,PORT);
				break;
			default:
				break;
			}
			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			writer = new PrintWriter(sock.getOutputStream(),true);
		}catch(IOException e){
		}
	}

}
