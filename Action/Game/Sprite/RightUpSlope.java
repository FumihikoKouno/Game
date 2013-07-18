package Game.Sprite;

import Game.Sprite.Player;
import Game.MapData.MapData;
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
	public void update(MapData mapData){}
	// プレイヤーがスプライトに触れたときの関数
	public void touch(Sprite s, int dir, int[] dest){
		int px = s.getX()+s.getWidth();
		int py = s.getY()+s.getHeight();
		if(px >= x+width){
			s.setVy(y-py+Data.CD_DIFF);
		}else{
			px -= x;
			s.setVy(y+height-px-py+Data.CD_DIFF);
		}
	}
}