/**
 * プレイヤーを表わすクラス
 */

package Game.Sprite;

import java.awt.Graphics;
import java.awt.Color;

public class LifeGauge{
	/**
	 * 描画処理
	 */
	public static void draw(Graphics g, int x, int y, int life, int lifeMax){
		g.setColor(Color.WHITE);
		int w = lifeMax<<1;
		int h = 8;
		g.drawRect(x,y,w,h);
		if(100*life/lifeMax <= 25){
			g.setColor(Color.RED);
		}else{
			g.setColor(Color.ORANGE);
		}
		g.fillRect(x+1,y+1,(w-1)*life/lifeMax,h-1);
	}
}