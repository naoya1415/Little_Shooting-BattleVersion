package battleVersion.BattleMode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import battleVersion.BattleFieldConfigBean;

public class UDPListener extends Thread {
	

	BattleFieldConfigBean bfc = null;
	byte[] dpack_b=null;
	DatagramPacket dpack=null;
		
	
	DatagramSocket ds  = null;
	BattleMode bm=null;
	
	NetworkThread nt =null;
	
	Boolean continueFlag = true;
	
	public  UDPListener(BattleMode bm,NetworkThread nt,BattleFieldConfigBean bfc) throws SocketException{

		 this.bm = bm;
		 this.nt = nt;
		 this.bfc = bfc;
		 this.dpack_b = new byte[bfc.getTrans_size()];
		 this.dpack = new DatagramPacket(dpack_b,dpack_b.length);
		 
		 this.ds = bfc.getMyUDPSocket();
		 ds.setSoTimeout(bfc.getListenerInterval());			  
	}
	
	public void close(){
		continueFlag = false;
	}
	
	public void run() {
		
		 while(continueFlag) {
				
			try {
				ds.receive(dpack);
				
			} catch (SocketTimeoutException e) {
					continue;	
			} catch (IOException e) {
				ds.close();
				return;
			}

			String msg = new String(dpack.getData(), 0, bfc.getTrans_size());
			String recieve[] = msg.split(",");

			if (recieve[0].equals("start")) {
				bm.battleStartFlag = true;
				try {
					nt.sendUDPMsg("start-ok,");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (recieve[0].equals("start-ok")) {
				bm.battleStartFlag = true;
			}
			
			if (recieve[0].equals("lose")) {
				bm.setRivalLose(true);
			}
			
			if (recieve[0].equals("move")) {
//				int x = (int)((float)Integer.parseInt(recieve[1])/bm.scaleWidth) ;
//				int y = (int)((float)Integer.parseInt(recieve[2])/bm.scaleHeight);
				
				bm.updateRivalLocation(Integer.parseInt(recieve[1]), Integer.parseInt(recieve[2]));
				
			}else if(recieve[0].equals("touch")){
				bm.RivalAttack();
			}
		 }	 
		 ds.close();
	}
}
