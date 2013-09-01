package Game.Sprite;

import Game.Sprite.Player;
import Game.Common.Data;

import java.awt.Graphics;

public class AppearingChip extends Sprite{
	public boolean appear;
	public int limit;
	public int start;
	public int end;
	public AppearingChip(int x, int y, int start, int end, int limit){
		super(x,y);
		IMAGE_X = 96;
		IMAGE_Y = 0;
		this.start = start;
		this.end = end;
		this.limit = limit;
		width = Data.CHIP_SIZE;
		height = Data.CHIP_SIZE;
	}
	// スプライトのupdate
	public void update(){
		vx = 0;
		vy = 0;
		int f = Data.frame%limit;
		if(start < f && f < end){
			appear = true;
		}else{
			appear = false;
		}
	}
	// プレイヤーがスプライトに触れたときの関数
	public void touch(Sprite s, int dir, int[] dest){
		if(!appear) return;
		int px = s.getX()+s.getWidth()-Data.CD_DIFF;
		int py = s.getY()+s.getHeight()-Data.CD_DIFF;
		if((dir & (1 << DOWN)) > 0){
			s.mapHit(DOWN,dest[DOWN]);
			// s.setVy(dest[DOWN]-s.getY());
			// s.land();
			return;
		}
		if((dir & (1 << LEFT)) > 0){
			s.mapHit(LEFT,dest[LEFT]);
//			s.setVx(dest[LEFT]-s.getX());
		}
		if((dir & (1 << RIGHT)) > 0){
			s.mapHit(RIGHT,dest[RIGHT]);
//			s.setVx(dest[RIGHT]-s.getX());
		}
		if((dir & (1 << UP)) > 0){
			s.mapHit(UP,dest[UP]);
//			s.setVy(dest[UP]-s.getY());
		}
	}
	
	// 描画処理
	public void draw(Graphics g, int screenX, int screenY){
		if(!appear) return;
		super.draw(g,screenX,screenY);
		/*
		int ix = 0;
		int iy = 0;
		g.drawImage(image,
			x-screenX, y-screenY, x-screenX+width, y-screenY+height,
			ix, iy, ix+width, iy+height,
			null
		);
		*/
	}
}