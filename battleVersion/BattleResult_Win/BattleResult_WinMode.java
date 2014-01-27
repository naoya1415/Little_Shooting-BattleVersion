/**
 * 
 */
package battleVersion.BattleResult_Win;

import gameLogic.mode.Result_ClearMode;
import gameLogic.mode.Bean.FieldConfigBean;
import battleVersion.Battle_StartMode.Battle_StartMode;

/**
 * @author n-dolphin
 *
 */
public class BattleResult_WinMode extends Result_ClearMode{
	/**
	 * 本モードの名前
	 */
	final public static String name = "Battle_WinMode";
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	protected void setupResultText(){
		super.setupResultText();
		
		resultText.setText("Win");
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
