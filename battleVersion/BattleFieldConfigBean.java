package battleVersion;

import gameLogic.mode.Bean.FieldConfigBean;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

import battleVersion.Battle_StartMode.RivalCandidate;

public class BattleFieldConfigBean extends FieldConfigBean{

	private InetAddress rivalIp=null;
	private Integer rivalUDPport = null;
	private String rivalName = null;
	
	
	private DatagramSocket myUDPSocket =null;
			
	private String myName = null;
	
	private Boolean rivalSelected = false;
	
	private Integer searchTimeOut = 500;
	private Integer replyInterval = 50;
	
	private Integer listenerInterval = 2000; 
	
	
	
	private List<RivalCandidate> rivalCandidates = null; 

	
	
	public synchronized Boolean addRival(RivalCandidate rc){
		for(RivalCandidate crc :this.rivalCandidates){
			if(rc.getName().equals(crc.getName())){
				return false;
			}
		}
		this.rivalCandidates.add(rc);
		return true;
	}
	private Integer trans_size = 6400;
	
	private Integer[] FirstPorts = {60010,60011,60012,60013,60014,60015};
	
	//最小・最大を楽に取るため、ソートしとく
	private Boolean FirstPortsSorted = false;
	
	private Integer ReplyPort = null;
	
	
	/**
	 * @return rivalCandidates
	 */
	public synchronized  List<RivalCandidate> getRivalCandidates() {
		return rivalCandidates;
	}
	/**
	 * @param rivalCandidates セットする rivalCandidates
	 */
	public synchronized void setRivalCandidates(List<RivalCandidate> rivalCandidates) {
		this.rivalCandidates = rivalCandidates;
	}
	
	/**
	 * @return rivalIp
	 */
	public InetAddress getRivalIp() {
		return rivalIp;
	}
	/**
	 * @param rivalIp セットする rivalIp
	 */
	public void setRivalIp(InetAddress rivalIp) {
		this.rivalIp = rivalIp;
	}
	/**
	 * @return rivalUDPport
	 */
	public Integer getRivalUDPport() {
		return rivalUDPport;
	}
	/**
	 * @param rivalUDPport セットする rivalUDPport
	 */
	public void setRivalUDPport(Integer rivalUDPport) {
		this.rivalUDPport = rivalUDPport;
	}
	/**
	 * @return rivalName
	 */
	public String getRivalName() {
		return rivalName;
	}
	/**
	 * @param rivalName セットする rivalName
	 */
	public void setRivalName(String rivalName) {
		this.rivalName = rivalName;
	}
	/**
	 * @return myName
	 */
	public String getMyName() {
		return myName;
	}
	/**
	 * @param myName セットする myName
	 */
	public void setMyName(String myName) {
		this.myName = myName;
	}
	/**
	 * @return rivalSelected
	 */
	public Boolean getRivalSelected() {
		return rivalSelected;
	}
	/**
	 * @param rivalSelected セットする rivalSelected
	 */
	public void setRivalSelected(Boolean rivalSelected) {
		this.rivalSelected = rivalSelected;
	}
	/**
	 * @return trans_size
	 */
	public Integer getTrans_size() {
		return trans_size;
	}
	/**
	 * @param trans_size セットする trans_size
	 */
	public void setTrans_size(Integer trans_size) {
		this.trans_size = trans_size;
	}
	
	/**
	 * @return replyPort
	 */
	public Integer getReplyPort() {
		return ReplyPort;
	}
	/**
	 * @param replyPort セットする replyPort
	 */
	public void setReplyPort(Integer replyPort) {
		ReplyPort = replyPort;
	}
	
	/**
	 * @return searchTimeOut
	 */
	public Integer getSearchTimeOut() {
		return searchTimeOut;
	}
	/**
	 * @param searchTimeOut セットする searchTimeOut
	 */
	public void setSearchTimeOut(Integer searchTimeOut) {
		this.searchTimeOut = searchTimeOut;
	}
	/**
	 * @return replyInterval
	 */
	public Integer getReplyInterval() {
		return replyInterval;
	}
	/**
	 * @param replyInterval セットする replyInterval
	 */
	public void setReplyInterval(Integer replyInterval) {
		this.replyInterval = replyInterval;
	}
	/**
	 * @return firstPorts
	 */
	public Integer[] getFirstPorts() {
		if(!FirstPortsSorted){
			Arrays.sort(FirstPorts);
			FirstPortsSorted = true;
		}
		return FirstPorts;
	}
	/**
	 * @param firstPorts セットする firstPorts
	 */
	public void setFirstPorts(Integer[] firstPorts) {
		FirstPortsSorted = false;
		FirstPorts = firstPorts;
	}
	/**
	 * @return listenerInterval
	 */
	public Integer getListenerInterval() {
		return listenerInterval;
	}
	/**
	 * @param listenerInterval セットする listenerInterval
	 */
	public void setListenerInterval(Integer listenerInterval) {
		this.listenerInterval = listenerInterval;
	}
	/**
	 * @return myUDPSocket
	 */
	public DatagramSocket getMyUDPSocket() {
		return myUDPSocket;
	}
	/**
	 * @param myUDPSocket セットする myUDPSocket
	 */
	public void setMyUDPSocket(DatagramSocket myUDPSocket) {
		this.myUDPSocket = myUDPSocket;
	}
}
