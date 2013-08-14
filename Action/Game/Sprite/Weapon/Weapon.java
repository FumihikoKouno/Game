/**
 * 武器の基盤クラス
 */

package Game.Sprite.Weapon;

import Game.Sprite.Sprite;
import Game.Common.*;

import java.awt.Color;
import java.awt.Graphics;

import java.util.Random;

public class Weapon extends Sprite{
	public static final byte SWORD = 0;
	public static final byte ARROW = 1;
	public static final byte SHOT = 2;
	public Weapon(int x, int y){
		super(x,y);
	}
	public void setDirection(int d){
		direction = d;
	}
	// 実際に武器を出現させる
	public void appear(){assert(false);}
	
	// 武器の攻撃力
	public int power;
	// 武器のID(必要かどうかは分からないけど…)
	public int weaponID;
	// 武器の属性
	public int element;
	// 敵の武器かどうか
	protected boolean enemy;
	
	public void draw(Graphics g, int screenX, int screenY){
		Random rand = new Random(spriteID);
		switch(element){
		case Element.NONE:
			return;
		case Element.FIRE:
			g.setColor(new Color(255,0,0,128));
			break;
		case Element.WATER:
			g.setColor(new Color(0,0,255,128));
			break;
		case Element.THUNDER:
			g.setColor(new Color(255,255,0,128));
			break;
		}
		for(int i = -10; i < width; i+=10){
			for(int j = -10; j < height; j+=10){
				if(rand.nextInt(100) < 20){
					int size = rand.nextInt(20);
					g.fillOval(x-screenX+i,y-screenY+j,size,size);
				}
			}
		}
	}
}