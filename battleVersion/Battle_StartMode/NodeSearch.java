package battleVersion.Battle_StartMode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

import battleVersion.BattleFieldConfigBean;
import battleVersion.NetworkUtil;

public class NodeSearch extends Thread {


	BattleFieldConfigBean bfc = null;
	Battle_StartMode bsm = null;
	
	
	protected byte[] dpack_b=null;

	private InetAddress MyIp=null;

	private Boolean stopFlag = false;
	
	

	public NodeSearch(Battle_StartMode bsm,BattleFieldConfigBean bfc) throws Exception {
		this.bsm = bsm;
		this.bfc = bfc;
		this.dpack_b = new byte[bfc.getTrans_size()];

		this.MyIp = InetAddress.getLocalHost();

		if (bfc.getMyName() == null) {
			bfc.setMyName(this.MyIp.getHostAddress());
		}

		bfc.setMyUDPSocket(new DatagramSocket());
	}

	public void run() {
		try {
			while (!stopFlag) {
				searchNode();
				standby((int)(bfc.getListenerInterval()*Math.random()+bfc.getReplyInterval()));
			}

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	public void close(){
		stopFlag =true;
	}

	private void searchNode() throws IOException {

		DatagramSocket receiveSocket  = new DatagramSocket();
				
		sendToFirstPorts(MyIp.getHostAddress() + "," + receiveSocket.getLocalPort()+ ","
							+bfc.getMyUDPSocket().getLocalPort()+","
								,"255.255.255.255");

		// 複数のノードから返信が来る事を想定
		Long ShutTime = System.currentTimeMillis() + bfc.getSearchTimeOut();

		Integer leftTime = null;
		
		try{
			while (true) {
				leftTime = (int) (ShutTime - System.currentTimeMillis());

				if (leftTime <= 0) {
					break;
				}

				try {
					listen(receiveSocket, Math.min(leftTime, 1000));
				} catch (SocketTimeoutException e) {
					continue;
				}
			}
	
		}finally{
			receiveSocket.close();
		}
	}

	private void standby(Integer timeOut) throws IOException {

		RivalCandidate rc = null;

		//※getter,setterを利用し、FirstPorts[0]に最小値、FirstPorts[要素数-1]に最大値を仕込むように仕掛けてあるあためこんな感じ
		DatagramSocket receiveSocket  = NetworkUtil.Open_DatagramSocket(bfc.getFirstPorts()[0], bfc.getFirstPorts()[bfc.getFirstPorts().length-1]);
		
		if(receiveSocket == null){
			return ;
		}
		
		try {
			rc = listen(receiveSocket, timeOut);
		} catch (SocketTimeoutException e) {
			return;
		}finally{
			receiveSocket.close();
		}

		try {
			Thread.sleep((int) (Math.random() * (bfc.getSearchTimeOut() - bfc
					.getReplyInterval())) / 2 + bfc.getReplyInterval());
		} catch (InterruptedException e) {
			return;
		}

		NetworkUtil.sendUDPMsg(bfc.getMyName() + "," +rc.getPort()+","+bfc.getMyUDPSocket().getLocalPort()+",", rc.getIP().getHostAddress(),
				rc.getPort());
		
		return;
	}
	private RivalCandidate listen(DatagramSocket listenSocket, Integer timeOut)
			throws SocketTimeoutException, IOException {

		DatagramPacket dpack = new DatagramPacket(dpack_b, dpack_b.length);

		listenSocket.setSoTimeout(timeOut);
		listenSocket.receive(dpack);

		String msg = new String(dpack.getData(), 0, bfc.getTrans_size());

		RivalCandidate rc = new RivalCandidate();

		rc.setIP(dpack.getAddress());
		rc.setName(msg.split(",")[0]);
		rc.setPort(Integer.parseInt(msg.split(",")[1]));
		rc.setBattleUDPPort(Integer.parseInt(msg.split(",")[2]));

		// 未発見のノードだった
		if (bfc.addRival(rc)) {
			bsm.getUnRecognizedRivals().add(rc);
		}
		return rc;
		
	}
	
	private void sendToFirstPorts(String msg, String To) throws IOException{
		for(int i= bfc.getFirstPorts().length-1;i>=0;i--){
			NetworkUtil.sendUDPMsg(msg,To,bfc.getFirstPorts()[i]);
		}
	}
	

}
