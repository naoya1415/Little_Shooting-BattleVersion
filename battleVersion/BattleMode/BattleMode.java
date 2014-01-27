/**
 * 
 */
package battleVersion.BattleMode;

import gameLogic.DrawImplement.DrawImplementIF;
import gameLogic.mode.PlayMode;
import gameLogic.mode.Result_GameOverMode;
import gameLogic.mode.Bean.FieldConfigBean;
import gameLogic.mode.Bean.TextBean;
import battleVersion.BattleFieldConfigBean;
import battleVersion.Battle_DrawImplementIF;
import battleVersion.BattleResult_Lose.BattleResult_LoseMode;
import battleVersion.BattleResult_Win.BattleResult_WinMode;

/**
 * @author n-dolphin
 *
 */
public class BattleMode extends PlayMode{

	public final static String name = "BattleMode";

	
	Integer rivalX =-1;
	Integer rivalY=-1;
	
	Battle_DrawImplementIF bdi= null;
	
	BattleFieldConfigBean bfc= null;
	
	public Boolean battleStartFlag = null;

	static Boolean scaled = false;
	Float scaleWidth =null;
	Float scaleHeight =null;
	
	NetworkThread nt = null;

	private Boolean rivalLose=null;
	
	/**
	 *Battle_Start画面に戻るためのボタン 
	 */
//	protected ButtonBean returnButton = new ButtonBean();
	
	/**
	 * 敵機ミサイルの速さ
	 */
	public Integer[] OwnEnemyMissileSpeed ;
	
	
	/**
	 * 対戦相手のゲーム開始を待っていることを表示するテキストのBean
	 */
	protected TextBean waitingText = new TextBean();
	
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String open(DrawImplementIF DI, FieldConfigBean FC) throws Exception{

		super.open(DI, FC);
		
		bfc = (BattleFieldConfigBean)FC;
		bdi = (Battle_DrawImplementIF)DI;
		battleStartFlag = false;
		rivalLose=false;
		
		isRivalMissileActive = false;
		if(!scaled){
			calcScale(FC.screenWidth,FC.screenHeight);
			setScale();	
		}

		this.rivalY = fc.screenHeight /10 * 3;	
		
		
		nt = new NetworkThread(this,bfc);
		
		nt.sendUDPMsg("start,");
		
		initOwnEnemies();
		
		setupWaitingText();
//		setupReturnButton();
		
		return null;
	}
	
	/**
	 * ゲームの結果によって表示されるテキストのBeanを設定
	 */
	void setupWaitingText(){
		/*スタートボタンのコンフィグ*/
		waitingText.setText("waiting");
		waitingText.setStartPosition(null,fc.screenHeight/3- waitingText.getTextHeight());
		waitingText.setHorizonalPosition("center");
	}
	
//	/**
//	 * Battle_Start画面に戻るためのボタンの設定
//	 */
//	protected void setupReturnButton(){
//		returnButton.setText("return");
//		returnButton.setStartPosition(null, fc.screenHeight/2- returnButton.getTextHeight());
//		returnButton.setHorizonalPosition("center");
//	}
	
	protected void calcScale(Integer screenWidth,Integer screenHeight){
		this.scaleWidth  = (float)FieldConfigBean.defaultScreenWidth / (float)screenWidth   ;
		this.scaleHeight= (float)FieldConfigBean.defaultScreenHeight/(float)screenHeight;	
	}
	
	protected void setScale(){
		fc.missileWidth = (int)(fc.missileWidth / scaleWidth);
		fc.missileHeight = (int)(fc.missileHeight / scaleHeight);
		
		fc.myWidth = (int)(fc.myWidth/scaleWidth);
		fc.myHeight= (int)(fc.myHeight/ scaleHeight);
		
		fc.enemyWidth= (int)(fc.enemyWidth/ scaleWidth);
		fc.enemyHeight= (int)(fc.enemyHeight/ scaleHeight);
		
	}
	void updateRivalLocation(Integer X,Integer Y){
		this.rivalX = fc.screenWidth - X;
	}
	void RivalAttack(){
		if (!isRivalMissileActive) {
			RivalMissileX = rivalX ;
			RivalMissileY = rivalY;
			isRivalMissileActive = true;
		}
		return ;
	}
	@Override
	public String update() throws Exception{
	
		di.setBackground(FieldConfigBean.black);
		
		if(!battleStartFlag){
			nt.sendUDPMsg("start,");
			bdi.drawText(waitingText);		
//			di.drawButton(returnButton);
			return null;
		}
		
		String isLosed= null;
		isLosed = super.update();
		
		if(isLosed == Result_GameOverMode.name){
			this.MyDamage();
			return BattleResult_LoseMode.name;
		}
		
		if(rivalLose){
			return BattleResult_WinMode.name;
		}
		
		
		calcOwnEnemyPlane();
		calcRivalMissile();
		calcOwnEnemyMissile();
		
		bdi.drawRivalPlane(rivalX, rivalY);	
		bdi.drawOwnEnemyPlane(isOwnEnemyAlive,OwnEnemyX,OwnEnemyY);

		bdi.drawEnemyMissile(isOwnEnemyMissileActive,OwnEnemyMissileX,OwnEnemyMissileY,FieldConfigBean.white);
		bdi.drawRivalMissile(isRivalMissileActive, this.RivalMissileX, this.RivalMissileY);

		return isLosed;
	}
	
	@Override
	public String close() throws Exception{
		super.close();
		return null;
		
	}
	
	
	

	@Override
	public String touch(Integer X, Integer Y) throws Exception{
		super.touch(X, Y);
		nt.sendUDPMsg("touch,"+(int)(X*scaleWidth)+","+(int)(Y*scaleHeight)+",");
		return null;
	}

	@Override
	public String pointerDown(Integer X, Integer Y)throws Exception {
		super.pointerDown(X, Y);
		return null;
	}

	@Override
	public String pointerUp(Integer X, Integer Y) throws Exception{
		super.pointerUp(X, Y);
		return null;
	}

	@Override
	public String move(Integer X, Integer Y) throws Exception{
		super.move(X, Y);
		nt.sendUDPMsg("move,"+(int)(X*scaleWidth)+","+(int)(Y*scaleHeight)+",");
		return null;
	}

	@Override
	public String keyPressed(char KeyChar) throws Exception{
		super.keyPressed(KeyChar);
		return null;
	}

	/**
	 * 各敵機のx座標
	 */
	public Integer[] OwnEnemyX = null;
	
	/**
	 * 各敵機のy座標 
	 */
	public Integer[] OwnEnemyY = null;
	
	/**
	 * 各敵機の移動方向
	 */
	public Integer[] OwnEnemyMove = null;
	/**
	 * 各敵機の死活
	 */
	public Boolean[] isOwnEnemyAlive = null;
	
	
	/**
	 * 各敵機ミサイルのX座標
	 */
	public Integer[] OwnEnemyMissileX = null;
	/**
	 * 各敵機ミサイルのY座標
	 */
	public Integer[] OwnEnemyMissileY = null;
	
	
	/**
	 * 各敵機ミサイルの死活
	 */
	public Boolean[] isOwnEnemyMissileActive = null;

	/**
	 * 敵機の初期化 
	 */
	public void initOwnEnemies() {
		numOfAlive = fc.numOfEnemy;
		OwnEnemyX = new Integer[fc.numOfEnemy];
		OwnEnemyY = new Integer[fc.numOfEnemy];
		OwnEnemyMove = new Integer[fc.numOfEnemy];
		isOwnEnemyAlive = new Boolean[fc.numOfEnemy];
		OwnEnemyMissileX = new Integer[fc.numOfEnemy];
		OwnEnemyMissileY = new Integer[fc.numOfEnemy];
		isOwnEnemyMissileActive = new Boolean[fc.numOfEnemy];
		
		OwnEnemyMissileSpeed = new Integer[fc.numOfEnemy];
		for (Integer i = 0; i < 7; i++) {
			OwnEnemyX[i] = 70 * i;
			OwnEnemyY[i] = 50;
		}
		for (Integer i = 7; i < fc.numOfEnemy; i++) {
			OwnEnemyX[i] = 70 * (i - 6);
			OwnEnemyY[i] = 100;
		}
		for (Integer i = 0; i < fc.numOfEnemy; i++) {
			isOwnEnemyAlive[i] = true;
			OwnEnemyMove[i] = -1;
			OwnEnemyX[i] = fc.screenWidth -OwnEnemyX[i];
			OwnEnemyY[i] = fc.screenHeight-OwnEnemyY[i];
			
		}
		for (Integer i = 0; i < fc.numOfEnemy; i++) {
			isOwnEnemyMissileActive[i] = true;
			OwnEnemyMissileX[i] = OwnEnemyX[i] + fc.enemyWidth / 2;
			OwnEnemyMissileY[i] = OwnEnemyY[i];
			OwnEnemyMissileSpeed[i] = 10 + (i % 6);
		}
	}
	/**
	 * 敵機の計算
	 */
	protected void calcOwnEnemyPlane() {
		for (Integer i = 0; i < fc.numOfEnemy; i++) {
			if (isOwnEnemyAlive[i]) {
				if (OwnEnemyX[i] > fc.screenWidth - fc.enemyWidth) {
					OwnEnemyMove[i] = -1;
				} else if (OwnEnemyX[i] < 0) {
					OwnEnemyMove[i] = 1;
				}
				OwnEnemyX[i] += OwnEnemyMove[i] * 10;
			}
		}
	}
		
	/**
	 * 敵機のミサイルの計算
	 * @return 敗北時の遷移先名
	 */
	protected String calcOwnEnemyMissile() {
		for (Integer i = 0; i < fc.numOfEnemy; i++) {
			// ミサイルの配置
			if (isOwnEnemyMissileActive[i]) {
				OwnEnemyMissileY[i] -= OwnEnemyMissileSpeed[i];
			}
			// 敵機のミサイルの自機への当たり判定
			if ((OwnEnemyMissileX[i] >= tempMyX)
					&& (OwnEnemyMissileX[i] <= tempMyX + fc.myWidth)
					&& (OwnEnemyMissileY[i] + 5 >= myY)
					&& (OwnEnemyMissileY[i] + 5 <= myY + fc.myHeight)) {
				
			}
			// ミサイルがウィンドウ外に出たときのミサイルの再初期化
			if (OwnEnemyMissileY[i] < 0) {
				if (isOwnEnemyAlive[i]) {
					OwnEnemyMissileX[i] = OwnEnemyX[i] ;
					OwnEnemyMissileY[i] = OwnEnemyY[i] - fc.enemyHeight;
				} else {
					isOwnEnemyMissileActive[i] = false;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 自機ミサイルの死活
	 */
	protected Boolean isRivalMissileActive = null;
	/**
	 * 自機ミサイルの座標
	 */
	protected Integer RivalMissileX= null, RivalMissileY= null;
	
	/**
	 * 自機ミサイルの座標計算
	 */
	protected String calcRivalMissile()throws Exception {
		if (!isRivalMissileActive) {
			return null;
		}
		
		// ミサイルの配置
		RivalMissileY += fc.myMissileSpeed;
		// ライバル機のミサイルの自機への当たり判定
			
		if ((RivalMissileX >= myX-fc.myWidth)
				&& (RivalMissileX <= myX + fc.myWidth)
				&& (RivalMissileY + 5 >= myY - fc.myHeight)
				&& (RivalMissileY + 5 <= myY + fc.myHeight)) {
			
			return MyDamage();
		}
		// ミサイルがウィンドウ外に出たときのミサイルの再初期化
		if (RivalMissileY > fc.screenHeight){
			isRivalMissileActive = false;
		}
		return null;
	}
	
	String MyDamage()throws Exception{
		nt.sendUDPMsg("lose,");
		return Result_GameOverMode.name;
		
	}

	/**
	 * @return rivalLose
	 */
	public Boolean getRivalLose() {
		return rivalLose;
	}

	/**
	 * @param rivalLose セットする rivalLose
	 */
	public void setRivalLose(Boolean rivalLose) {
		this.rivalLose = rivalLose;
	}
	


}
