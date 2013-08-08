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
	public void update(MapData mapData){
		vx = vy = 0;
	}
	// �v���C���[���X�v���C�g�ɐG�ꂽ�Ƃ��̊֐�
	public void touch(Sprite s, int dir, int[] dest){
		int px = s.getX()+s.getWidth()-Data.CD_DIFF;
		int py = s.getY()+s.getHeight()-Data.CD_DIFF;
		// ������G�ꂽ�ꍇ
		if((dir & (1 << UP)) > 0){
			s.setVy(dest[UP]-s.getY()+1);
			return;
		}
		// ������G�ꂽ�ꍇ
		if(px <= x && py > y){
			s.setVx(x-px);
			return;
		}
		// ���_�̏ꍇ
		if(s.getX()+Data.CD_DIFF < x){
			if(s.jumping() && s.getVy() < 0) return;
			s.setVy(y-py);
			s.land();
			return;
		}
		if((s.getX()+Data.CD_DIFF+s.getVx()-x) > (py+s.getVy()-y)) return;
		if(s.jumping() && s.getVy() < 0) return;
		s.setVy(s.getX()+Data.CD_DIFF+s.getVx()-x-py+y);
		s.land();
	}
}