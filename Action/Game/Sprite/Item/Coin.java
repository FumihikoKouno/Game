package Game.Sprite.Item;

import Game.Sprite.Sprite;
import Game.Sprite.Player;
import Game.MapData.MapData;
import Game.Common.Data;

import java.awt.Graphics;

public class Coin extends Sprite{

	public Coin(int x, int y){
		super(x,y);
		image = Data.image.coinImage;
		width = 30;
		height = 30;
	}
	// �X�v���C�g��update
	public void update(MapData mapData){
	}
	// �v���C���[���X�v���C�g�ɐG�ꂽ�Ƃ��̊֐�
	public void touch(Sprite s, int dir, int[] dest){
		if(s instanceof Player){
			Player tmp = (Player)s;
			tmp.coin++;
			end = true;
			Data.se.coinSound.play();
		}
	}
}