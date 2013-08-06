/**
 * ����̊�ՃN���X
 */

package Game.Sprite.Weapon;

import Game.Common.*;
import Game.MapData.MapData;

import java.awt.Graphics;

public class Sword extends Weapon{
	public int frame;
	
	public Sword(){
		super(0,0);
		power = 1;
	}
	public Sword(int d){
		super(0,0);
		direction = d;
		power = 1;
	}
	public void appear(){
		frame = Data.frame;
		if(direction <= DOWN){
			width = 20;
			height = 30;
		}else{
			width = 30;
			height = 20;
		}
		switch(direction){
		case UP:
			this.x = StateData.player.getX()+6;
			this.y = StateData.player.getY()-height;
			break;
		case DOWN:
			this.x = StateData.player.getX()+6;
			this.y = StateData.player.getY()+StateData.player.getHeight();
			break;
		case LEFT:
			this.x = StateData.player.getX()-width;
			this.y = StateData.player.getY()+6;
			break;
		case RIGHT:
			this.x = StateData.player.getX()+StateData.player.getWidth();
			this.y = StateData.player.getY()+6;
			break;
		}
	}
	
	// ����̏�Ԃ�update(��͔�Ԃ��A���ł����t���[���o�Ă邩�Ƃ�)
	public void update(MapData mapData){
		vx = StateData.player.getVx();
		vy = StateData.player.getVy();
		if(Data.frame - frame >= 5) end = true;
		move();
	}
	// �`�揈��
	public void draw(Graphics g, int screenX, int screenY){
		int sx, sy;
		if(direction <= DOWN){
			sx = (Data.frame-frame)*width;
			sy = direction * height;
		}else{
			sx = (Data.frame-frame)*width;
			sy = width*2 + (direction-LEFT) * height;
		}
		int dx = x - screenX;
		int dy = y - screenY;
		g.drawImage(Data.image.swordImage,
			dx, dy,
			dx + width, dy + height,
			sx, sy, sx + width, sy + height,
			null
		);
	}
}