/**
 * 
 */
package battleVersion;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkUtil {
	public static DatagramSocket Open_DatagramSocket(int from, int by) throws IOException{
		int tryN = Math.max(from, by) - Math.min(from,by)+1;
		
		for (int i = 0; i < tryN; i++) {
			try {
				int tryPort = (int) (Math.random() *
										(Math.max(from, by) - Math.min(from, by)+1)
									)
								+ Math.min(from, by);
				return new DatagramSocket(tryPort);
			} catch (BindException e) {
				continue;
			}
		}
		return null;
	}
	
	public static void sendUDPMsg(String msg, String To, Integer Port) throws UnknownHostException,IOException{
			sendUDPMsg(msg,InetAddress.getByName(To),Port);

	}
	public static void sendUDPMsg(String msg, InetAddress To, Integer Port) throws IOException{
		byte[] buf = msg.getBytes();// バイト列に変換

		DatagramPacket packet = new DatagramPacket(buf, buf.length,
				To, Port);

		DatagramSocket sendSocket = new DatagramSocket();
		try{
			sendSocket.send(packet);			
		}finally{
			sendSocket.close();	
		}
	}
}
