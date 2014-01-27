package battleVersion.BattleMode;

import java.io.IOException;
import java.net.SocketException;

import battleVersion.BattleFieldConfigBean;
import battleVersion.NetworkUtil;

public class NetworkThread {

	BattleFieldConfigBean bfc = null;
	

	Boolean continueFlag = true;
	
	UDPListener listen;
	
	
	public NetworkThread(BattleMode bm,BattleFieldConfigBean bfc) throws SocketException{
		this.bfc = bfc;
		
		listen = new UDPListener(bm,this,bfc);
		listen.start();
	}
	
	public void sendUDPMsg(String msg)throws IOException{
		NetworkUtil.sendUDPMsg(msg, bfc.getRivalIp(), bfc.getRivalUDPport());
	}
	
}
