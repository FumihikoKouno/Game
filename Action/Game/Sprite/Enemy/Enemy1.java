
package Game.Sprite.Enemy;

import Game.Sprite.Player;
import Game.Common.*;
import Game.MapData.MapData;
import Game.Sprite.Weapon.*;

import java.awt.Graphics;

public class Enemy1 extends Enemy{
	public Enemy1(int x, int y){
		super(x,y);
		image = Data.image.enemyImage;
		width = 32;
		height = 32;
		vx = 4;
		vy = 0;
		life = 2;
		direction = RIGHT;
		
	}
	public void update(MapData mapData){
		super.update(mapData);
		if(direction == LEFT) vx = -4;
		if(direction == RIGHT) vx = 4;
	}
}
