
package Game.Sprite.Enemy;

import Game.Sprite.Player;
import Game.Common.*;
import Game.Sprite.Weapon.*;
import Game.Sprite.Item.*;

import java.awt.Graphics;

public class Enemy0 extends Enemy{
	public Enemy0(int x, int y){
		super(x,y);
		image = Data.image.enemyImage;
		width = 32;
		height = 32;
		direction = LEFT;
		vx = 0;
		vy = 0;
		life = 50;
		lifeMax = 50;
	}
	public void update(){
		super.update();
		if(end){
			StateData.mapData.passSpriteList.add(new ElementItem(x,y-2*Data.CHIP_SIZE,Element.FIRE));
			StateData.mapData.passSpriteList.add(new ElementItem(x,y-1*Data.CHIP_SIZE,Element.WATER));
			StateData.mapData.passSpriteList.add(new ElementItem(x,y,Element.THUNDER));
		}
		if(Data.frame % 30 == 0 && jumpCount == 0){
			vy = -16;
			jumpCount = 1;
		}
		if(Data.frame % 23 == 0) StateData.mapData.passSpriteList.add(new Shot(x,y+height/2,-16,0,5));
	}
}
