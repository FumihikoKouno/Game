package Game.Sprite.Item;

import Game.Sprite.Sprite;
import Game.Common.Data;
import Game.Common.StateData;
import Game.Sprite.Player;

import java.awt.Graphics;

public class Coin extends Sprite{

	public Coin(int x, int y){
		super(x,y);
		IMAGE_X = 0;
		IMAGE_Y = 0;
		width = 32;
		height = 32;
	}
	// �X�v���C�g��update
	public void update(){
		vx = 0; vy = 0;
		animationUpdate(15);
	}
	// �v���C���[���X�v���C�g�ɐG�ꂽ�Ƃ��̊֐�
	public void touch(Sprite s, int dir, int[] dest){
		if(s instanceof Player){
			StateData.player.coin++;
			end = true;
			Data.se.coinSound.play();
		}
	}
}