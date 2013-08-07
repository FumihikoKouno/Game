
package Game.Sprite.Enemy;

import Game.Sprite.Player;
import Game.Common.*;
import Game.MapData.MapData;
import Game.Sprite.Weapon.*;

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
	public void update(MapData mapData){
		super.update(mapData);
		if(Data.frame % 90 == 0) mapData.spriteList.add(new Shot(x,y+4,-12,-1,16));
	}
}
