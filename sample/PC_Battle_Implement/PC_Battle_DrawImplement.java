package sample.PC_Battle_Implement;


import java.awt.Color;

import sample.PC_Implement.PC_DrawImplement;
import battleVersion.Battle_DrawImplementIF;

/**
 * PC(AWT)依存コードの実装
 * @author n-dolphin
 * @version 1.00 2014/01/17
 */
public class PC_Battle_DrawImplement extends PC_DrawImplement implements Battle_DrawImplementIF{

	public void drawRivalPlane(Integer x, Integer y) {
		
	    int xPoints[] = {x, x+fc.myWidth/2,x-fc.myWidth/2};
	    int yPoints[] = {y, y-fc.myHeight/2, y-fc.myHeight/2};

	    g.setColor(Color.red);
		g.fillPolygon(xPoints, yPoints, 3);
	}
	
	
	public void drawOwnEnemyPlane(Boolean[] isOwnEnemyAlive,Integer[] x,Integer[] y) {
		g.setColor(Color.white);
		for (int i = 0; i < fc.numOfEnemy; i++) {
			// ミサイルの配置
			if (isOwnEnemyAlive[i]) {
				int xPoints[] = {x[i], x[i]+fc.enemyWidth/2,x[i]-fc.enemyWidth/2};
			    int yPoints[] = {y[i], y[i]+fc.enemyHeight/2, y[i]+fc.enemyHeight/2};

				g.fillPolygon(xPoints, yPoints, 3);

			}
		}
	}
	
	public void drawRivalMissile(Boolean isRivalMissileActive,Integer x,Integer y) {
		if(isRivalMissileActive){
			g.setColor(Color.yellow);
			g.fillRect(x, y, fc.missileWidth, fc.missileHeight);
		}
	}

	public void drawOwnEnemyMissile(Boolean[] isOwnEnemyMissileActive,Integer[] x,Integer[] y) {
		for (int i = 0; i < fc.numOfEnemy; i++) {
			
			System.out.println(isOwnEnemyMissileActive[i]);
			// ミサイルの配置
			if (isOwnEnemyMissileActive[i]) {
				g.setColor(Color.white);
				g.fillRect(x[i], y[i], fc.missileWidth, fc.missileHeight);
			}
		}
	}
}
