package Game.Sprite;

import Game.Sprite.Player;
import Game.Common.Data;

import java.awt.Graphics;

public class RightUpSlope extends Sprite{

	public RightUpSlope(int x, int y){
		super(x,y);
		image = Data.image.rightUpSlopeImage;
		width = Data.CHIP_SIZE;
		height = Data.CHIP_SIZE;
	}
	// スプライトのupdate
	public void update(){
		vx = vy = 0;
	}
	// プレイヤーがスプライトに触れたときの関数
	public void touch(Sprite s, int dir, int[] dest){
		int px = s.getX()+s.getWidth()-Data.CD_DIFF;
		int py = s.getY()+s.getHeight()-Data.CD_DIFF;
		// 下から触れた場合
		if((dir & (1 << UP)) > 0){
			s.setVy(dest[UP]-s.getY()+1);
			return;
		}
		// 右から触れた場合
		if(s.getX()+Data.CD_DIFF >= x+width && py > y){
			s.setVx(x+width-Data.CD_DIFF-s.getX());
			return;
		}
		// 頂点の場合
		if(px > x+width){
			if(s.jumping() && s.getVy() < 0) return;
			s.setVy(y-py);
			s.land();
			return;
		}
		if((px+s.getVx()-x) + (py+s.getVy()-y) <= Data.CHIP_SIZE) return;
		if(s.jumping() && s.getVy() < 0) return;
		s.setVy(Data.CHIP_SIZE-(px+s.getVx()-x)-(py-y));
		s.land();
	}
}