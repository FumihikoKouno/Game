/**
 * ライフゲージ
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
		g.drawRect(x-lifeMax,y-15,w,h);
		int ratio = (100*life)/lifeMax;
		if(ratio <= 25){
			g.setColor(Color.RED);
		}else if(ratio <= 50){
			g.setColor(Color.ORANGE);
		}else{
			g.setColor(Color.GREEN);
		}
		g.fillRect(x-lifeMax+1,y-14,(w-1)*life/lifeMax,h-1);
	}
}