package battleVersion.BattleResult_Lose;

import gameLogic.mode.Result_ClearMode;
import gameLogic.mode.Bean.FieldConfigBean;
import battleVersion.Battle_StartMode.Battle_StartMode;

public class BattleResult_LoseMode extends Result_ClearMode{
	/**
	 * 本モードの名前
	 */
	final public static String name = "Battle_LoseMode";
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	protected void setupResultText(){
		super.setupResultText();
		
		resultText.setText("Lose");
		resultText.setStartPosition(null, fc.screenHeight/3- resultText.getTextHeight());
		resultText.setHorizonalPosition("center");
	}
	
	@Override
	public String pointerUp(Integer X, Integer Y) {
		restartButton.setColor(FieldConfigBean.DefaultButtonColor);
		if(restartButton.isInside(X, Y)){
			return Battle_StartMode.name;			
		}
		return null;
	}
}
