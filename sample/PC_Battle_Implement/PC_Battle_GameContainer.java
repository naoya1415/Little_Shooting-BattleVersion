package sample.PC_Battle_Implement;


import gameLogic.GameContainer;
import gameLogic.mode.PlayMode;
import gameLogic.mode.Result_ClearMode;
import gameLogic.mode.Result_GameOverMode;
import gameLogic.mode.StartMode;
import battleVersion.BattleFieldConfigBean;
import battleVersion.BattleMode.BattleMode;
import battleVersion.BattleResult_Lose.BattleResult_LoseMode;
import battleVersion.BattleResult_Win.BattleResult_WinMode;
import battleVersion.Battle_StartMode.Battle_StartMode;

/**
 * PC(AWT)向けサンプル実装の、ゲームコンテナ
 * @author n-dolphin
 * @version 1.00 2014/01/17
 */
public class PC_Battle_GameContainer extends GameContainer{
	
	/**
	 * コンストラクタ
	 * @param Width 画面の幅
	 * @param Height 画面の高さ
	 */
	public PC_Battle_GameContainer(Integer Width, Integer Height) {
		super(Width, Height, new PC_Battle_DrawImplement(),new BattleFieldConfigBean());
		
		super.addMode(new StartMode());
		super.addMode(new PlayMode());
		
		super.addMode(new Result_ClearMode());

		super.addMode(new Result_GameOverMode());
		
		
		super.addMode(new Battle_StartMode());
		super.addMode(new BattleMode());
		super.addMode(new BattleResult_WinMode());
		super.addMode(new BattleResult_LoseMode());
		
		super.changeMode(Battle_StartMode.name);
		
	}
	
	@Override
	public <Graphics> void  update(Graphics panel){
		super.update(panel);
	}

}
