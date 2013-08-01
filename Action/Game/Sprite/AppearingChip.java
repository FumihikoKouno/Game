package Game.Sprite;

import Game.Sprite.Player;
import Game.MapData.MapData;
import Game.Common.Data;

import java.awt.Graphics;

public class AppearingChip extends Sprite{
	public boolean appear;
	public int limit;
	public int start;
	public int end;
	public AppearingChip(int x, int y, int start, int end, int limit){
		super(x,y);
		this.start = start;
		this.end = end;
		this.limit = limit;
		image = Data.image.appearingChipImage;
		width = Data.CHIP_SIZE;
		height = Data.CHIP_SIZE;
	}
	// �X�v���C�g��update
	public void update(MapData mapData){
		int f = Data.frame%limit;
		if(start < f && f < end){
			appear = true;
		}else{
			appear = false;
		}
	}
	// �v���C���[���X�v���C�g�ɐG�ꂽ�Ƃ��̊֐�
	public void touch(Sprite s, int dir, int[] dest){
		if(!appear) return;
		int px = s.getX()+s.getWidth()-Data.CD_DIFF;
		int py = s.getY()+s.getHeight()-Data.CD_DIFF;
		if((dir & (1 << DOWN)) > 0){
			s.setVy(dest[DOWN]-s.getY());
			s.land();
			return;
		}
		if((dir & (1 << LEFT)) > 0){
			s.setVx(dest[LEFT]-s.getX());
		}
		if((dir & (1 << RIGHT)) > 0){
			s.setVx(dest[RIGHT]-s.getX());
		}
		if((dir & (1 << UP)) > 0){
			s.setVy(dest[UP]-s.getY());
		}
	}
	
	// �`�揈��
	public void draw(Graphics g, int screenX, int screenY){
		if(!appear) return;
		int ix = 0;
		int iy = 0;
		g.drawImage(image,
			x-screenX, y-screenY, x-screenX+width, y-screenY+height,
			ix, iy, ix+width, iy+height,
			null
		);
	}
}