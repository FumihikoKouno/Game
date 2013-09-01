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
	// スプライトのupdate
	public void update(){
		vx = 0; vy = 0;
		animationUpdate(15);
	}
	// プレイヤーがスプライトに触れたときの関数
	public void touch(Sprite s, int dir, int[] dest){
		if(s instanceof Player){
			StateData.player.coin++;
			end = true;
			Data.se.coinSound.play();
		}
	}
}