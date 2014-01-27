/**
 * 
 */
package battleVersion.Battle_StartMode;

import java.net.InetAddress;

/**
 * @author NaoyaIchikawa
 *
 */
public class RivalCandidate {
	private String Name= null;
	private InetAddress IP= null;
	private Integer Port= null;
	
	private Integer battleUDPPort= null;
	
	/**
	 * @return name
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name セットする name
	 */
	public void setName(String name) {
		Name = name;
	}
	/**
	 * @return iP
	 */
	public InetAddress getIP() {
		return IP;
	}
	/**
	 * @param iP セットする iP
	 */
	public void setIP(InetAddress iP) {
		IP = iP;
	}
	/**
	 * @return port
	 */
	public Integer getPort() {
		return Port;
	}
	/**
	 * @param port セットする port
	 */
	public void setPort(Integer port) {
		Port = port;
	}
	/**
	 * @return battleUDPPort
	 */
	public Integer getBattleUDPPort() {
		return battleUDPPort;
	}
	/**
	 * @param battleUDPPort セットする battleUDPPort
	 */
	public void setBattleUDPPort(Integer battleUDPPort) {
		this.battleUDPPort = battleUDPPort;
	}
	
}
