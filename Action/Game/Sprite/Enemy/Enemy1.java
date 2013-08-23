
package Game.Sprite.Enemy;

import Game.Sprite.Player;
import Game.Common.*;
import Game.Sprite.Weapon.*;
import Game.Sprite.Item.*;

import java.awt.Graphics;

public class Enemy1 extends Enemy{
	public Enemy1(int x, int y){
		super(x,y);
		image = Data.image.enemyImage;
		width = 32;
		height = 32;
		vx = 0;
		vy = 0;
		life = 8;
		lifeMax = 8;
		direction = RIGHT;
		
	}
	public void update(){
		super.update();
		if(end) StateData.mapData.passSpriteList.add(new Coin(x+1,y+1));
		if(direction == LEFT) vx = -4;
		if(direction == RIGHT) vx = 4;
	}
}
