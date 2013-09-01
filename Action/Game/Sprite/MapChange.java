package Game.Sprite;

import Game.Sprite.Player;
import Game.Common.Data;
import Game.Common.StateData;

import java.awt.Graphics;

public class MapChange extends Sprite{
	private int newID;
	private int px, py;
	private boolean touched = false;

	public MapChange(int newID, int px, int py, int x, int y){
		super(x,y);
		IMAGE_X = 0;
		IMAGE_Y = 0;
		this.newID = newID;
		this.px = px;
		this.py = py;
		width = Data.CHIP_SIZE;
		height = Data.CHIP_SIZE;
	}
	// �X�v���C�g��update
	public void update(){
		vx=vy=0;
	}
	
	// �v���C���[���X�v���C�g�ɐG�ꂽ�Ƃ��̊֐�
	public void touch(Sprite s, int dir, int[] dest){
		if(s instanceof Player){
			StateData.mapData.load(newID,px,py);
			end = true;
		}
	}
}