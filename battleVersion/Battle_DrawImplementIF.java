package battleVersion;

import gameLogic.DrawImplement.DrawImplementIF;

/**
 * バトルモードで追加される描画処理実装についてのインタフェース
 * @author Naoya Ichikawa
 * @version 1.10 2014/01/27
 *
 */
public interface  Battle_DrawImplementIF extends DrawImplementIF{
	/**
	 * ライバル機を描画する
	 * @param x 
	 * @param y 
	 */
	public void drawRivalPlane(Integer x, Integer y) ;
	
	/**
	 * ライバル機を攻撃する敵機を描画する
	 * @param isOwnEnemyAlive 
	 * @param x 
	 * @param y 
	 */
	public void drawOwnEnemyPlane(Boolean[] isOwnEnemyAlive,Integer[] x,Integer[] y) ;
	
	/**
	 * 自機を攻撃する敵機のミサイルを描画する
	 * @param isRivalMissileActive 
	 * @param x 
	 * @param y 
	 */
	public void drawRivalMissile(Boolean isRivalMissileActive,Integer x,Integer y);
	
	/**
	 * ライバル機を攻撃する敵機のミサイルを描画する
	 * @param isOwnEnemyMissileActive 
	 * @param x 
	 * @param y 
	 */
	public void drawOwnEnemyMissile(Boolean[] isOwnEnemyMissileActive,Integer[] x,Integer[] y);
}
