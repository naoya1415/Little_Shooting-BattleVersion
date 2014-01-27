package battleVersion.Battle_StartMode;

import gameLogic.DrawImplement.DrawImplementIF;
import gameLogic.mode.StartMode;
import gameLogic.mode.Bean.ButtonBean;
import gameLogic.mode.Bean.FieldConfigBean;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import battleVersion.BattleFieldConfigBean;
import battleVersion.BattleMode.BattleMode;


public class Battle_StartMode extends StartMode{
	public final static String name = "Battle_StartMode";
	
	@Override
	public String getName() {
		return name;
	}
	NodeSearch ns ;
	
	BattleFieldConfigBean  bfc = null;
	

	private Queue<RivalCandidate> unRecognizedRivals =null ;
	
	/**
	 * スタートボタンのBean
	 */
	protected List<ButtonBean> battleStartButtons = null ;
	protected Integer battleButtonNextX = 	null;
	protected Integer battleButtonNextY = null ;

	Map <ButtonBean,RivalCandidate> rivalButtonMap = null;
	
	@Override
	public String open(DrawImplementIF DI, FieldConfigBean FC) throws Exception{
		super.open(DI, FC);
		bfc = (BattleFieldConfigBean)FC;
		
		
		//対戦相手を確定
		bfc.setRivalName(null);
		bfc.setRivalIp(null);
		bfc.setRivalUDPport(null);
		bfc.setRivalSelected(false);
		unRecognizedRivals = new ArrayDeque<RivalCandidate>();
		battleStartButtons = new ArrayList<ButtonBean>();
		rivalButtonMap = new HashMap <ButtonBean,RivalCandidate>() ;
		bfc.setRivalCandidates(new ArrayList<RivalCandidate>());
		 
		battleButtonNextX = (int)(fc.screenWidth/4.5);
		battleButtonNextY = (int)(fc.screenHeight/1.5);
		
		ns = new NodeSearch(this,bfc);
		ns.start();
		return null;
	}

	@Override
	public String update() {
		super.update();
			
//		//ボタンが設定されていないRivalを登録する
		if(!unRecognizedRivals.isEmpty()){
			RivalCandidate newRival = unRecognizedRivals.poll();
			ButtonBean newBattleStartButton = setupBattleStartButton(newRival);
			battleStartButtons.add(newBattleStartButton);
			this.rivalButtonMap.put(newBattleStartButton,newRival);
		}
			
		//登録済みのRivalのボタンを描画する
		for(ButtonBean rival:this.battleStartButtons){
			di.drawButton(rival);
		}
			
		return null;
	}

	/**
	 * スタートボタンのBeanを設定
	 */
	ButtonBean setupBattleStartButton(RivalCandidate rc){
		ButtonBean battleStartButton = new ButtonBean();
		
		/*スタートボタンのコンフィグ*/
		System.out.println(rc.getName());
		battleStartButton.setText("VS " + rc.getName());
		battleStartButton.setStartPosition(null,this.battleButtonNextY);
		this.battleButtonNextY +=(int)(fc.screenHeight/1.5);
		

		return battleStartButton;
	}
	 
	
	@Override
	public String touch(Integer X, Integer Y) {
//		super.touch(X, Y);
		return null;
	}

	@Override
	public String pointerDown(Integer X, Integer Y) {
		String result = null;
		if((result =super.pointerDown(X, Y))!=null){
			return result;
		}
		
		
		//登録済みのRivalのボタンを描画する
		for(ButtonBean rivalButton:this.battleStartButtons){
			if(rivalButton.isInside(X, Y)){	
				rivalButton.setColor(FieldConfigBean.DefaultPressedButtonColor);
				break;
			}
		}
		
		return null;
	}

	@Override
	public String pointerUp(Integer X, Integer Y) {
		String result = null;
		if((result =super.pointerUp(X, Y))!=null){
			return result;
		}
		
		//登録済みのRivalのボタンを描画する
		for(ButtonBean rivalButton:this.battleStartButtons){
			rivalButton.setColor(FieldConfigBean.DefaultButtonColor);
			if(rivalButton.isInside(X, Y)){	
				
				RivalCandidate toBattle = this.rivalButtonMap.get(rivalButton);
				
				//対戦相手を確定
				bfc.setRivalName(toBattle.getName());
				bfc.setRivalIp(toBattle.getIP());
				bfc.setRivalUDPport(toBattle.getBattleUDPPort());
				bfc.setRivalSelected(true);
				
				ns.close();
				return BattleMode.name;
			}
		}
		return null;
	}

	@Override
	public String move(Integer X, Integer Y) {
//		super.move(X, Y);
		return null;
	}

	@Override
	public String keyPressed(char KeyChar) throws Exception {
		super.keyPressed(KeyChar);
		return null;
	}

	@Override
	public String close() throws Exception{
		super.close();
		ns.close();
		
		return null;
		
	}
	/**
	 * @return unRecognizedRivals
	 */
	public Queue<RivalCandidate> getUnRecognizedRivals() {
		return unRecognizedRivals;
	}

	/**
	 * @param unRecognizedRivals セットする unRecognizedRivals
	 */
	public void setUnRecognizedRivals(Queue<RivalCandidate> unRecognizedRivals) {
		this.unRecognizedRivals = unRecognizedRivals;
	}
	

}
