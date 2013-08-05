/**
 * ����̊�ՃN���X
 */

package Game.Sprite.Weapon;

import Game.Common.Data;
import Game.MapData.MapData;

import java.awt.Graphics;

public class Arrow extends Weapon{
	public int frame;
	
	private boolean stop;

	public Arrow(){
		super(0,0);
		power = 1;
	}
	public Arrow(int d){
		super(0,0);
		direction = d;
		power = 1;
		appear();
	}
	public void appear(){
		frame = Data.frame;
		if(direction <= DOWN){
			width = 15;
			height = 50;
		}else{
			width = 50;
			height = 15;
		}
		switch(direction){
		case UP:
			this.x = Data.player.getX()+6;
			this.y = Data.player.getY()-height;
			vx = 0;
			vy = -1;
			break;
		case DOWN:
			this.x = Data.player.getX()+6;
			this.y = Data.player.getY()+Data.player.getHeight();
			vx = 0;
			vy = 1;
			break;
		case LEFT:
			this.x = Data.player.getX()-width;
			vx = -18;
			vy = 1;
			this.y = Data.player.getY()+6;
			break;
		case RIGHT:
			this.x = Data.player.getX()+Data.player.getWidth();
			this.y = Data.player.getY()+6;
			vx = 18;
			vy = 1;
			break;
		}
	}
	
	// �����͂ǂ̕����ɓ����������Ƃ��̐��O�̈ʒu
	public void mapHit(int dir, int dest){
		if(frame == Data.frame) end = true;
		frame = Data.frame - 7;
		stop = true;
		switch(dir){
		case UP:
		case DOWN:
			vy = dest - y;
			break;
		case LEFT:
		case RIGHT:
			vx = dest - x;
			break;
		}
	}
	// ����̏�Ԃ�update(��͔�Ԃ��A���ł����t���[���o�Ă邩�Ƃ�)
	public void update(MapData mapData){
		move();
		if(Data.frame - frame >= 15) end = true;
		if(stop){
			vx = 0;
			vy = 0;
		}
	}
	// �`�揈��
	public void draw(Graphics g, int screenX, int screenY){
		int sx, sy;
		if(direction <= DOWN){
			sx = 0;
			sy = direction * height;
		}else{
			sx = 0;
			sy = width*2 + (direction-LEFT) * height;
		}
		int dx = x - screenX;
		int dy = y - screenY;
		g.drawImage(Data.image.arrowImage,
			dx, dy,
			dx + width, dy + height,
			sx, sy, sx + width, sy + height,
			null
		);
	}
}