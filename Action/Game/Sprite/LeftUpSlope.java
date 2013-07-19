package Game.Sprite;

import Game.Sprite.Player;
import Game.MapData.MapData;
import Game.Common.Data;

import java.awt.Graphics;

public class LeftUpSlope extends Sprite{

	public LeftUpSlope(int x, int y){
		super(x,y);
		image = Data.image.leftUpSlopeImage;
		width = Data.CHIP_SIZE;
		height = Data.CHIP_SIZE;
	}
	// �X�v���C�g��update
	public void update(MapData mapData){}
	// �v���C���[���X�v���C�g�ɐG�ꂽ�Ƃ��̊֐�
	public void touch(Sprite s, int dir, int[] dest){
		int px = s.getX()+s.getWidth()-Data.CD_DIFF;
		int py = s.getY()+s.getHeight()-Data.CD_DIFF;
		// ������G�ꂽ�ꍇ
		if(s.getVy() < 0 && s.getY()+Data.CD_DIFF >= y+height){
			s.setVy(y+height-Data.CD_DIFF-s.getY());
			return;
		}
		// ������G�ꂽ�ꍇ
		if(px <= x && py > y){
			s.setVx(x-px);
			return;
		}
		// ���_�̏ꍇ
		if(s.getX()+Data.CD_DIFF < x){
			s.setVy(y-py);
			s.land();
			return;
		}
		if((s.getX()+Data.CD_DIFF+s.getVx()-x) > (py+s.getVy()-y)) return;
		if(s.jumping() && s.getY() < 0) return;
		s.setVy(s.getX()+Data.CD_DIFF+s.getVx()-x-py+y);
		s.land();
	}
}