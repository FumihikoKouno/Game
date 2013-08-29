/**
 * 看板のクラス
 */

package Game.Sprite;

import Game.Sprite.Player;
import Game.Common.Data;
import Game.Common.KeyStatus;
import Game.MessageWindow;

import java.awt.Graphics;
import java.awt.Image;

public class Signboard extends Sprite{
	
	private String[] message;
	private boolean touching;
	private int size;
	
	private int just;
	
	private static int COL_SIZE = 30;
	
	/**
	 * コンストラクタ
	 * 座標を決めている
	 */
	public Signboard(String[] s, int x, int y, int just){
		this.just = just;
		message = s;
		size = s.length;
		width = 32;
		height = 32;
		this.x = x;
		this.y = y;
	}

	// スプライトのupdate
	public void update(){}
	
	// プレイヤーがスプライトに触れたときの関数
	public void touch(Sprite s, int dir, int[] dest){
		if((dir & (1 << HIT_DIRECT)) > 0){
			if(KeyStatus.up){
				Data.mw = new MessageWindow(message,just);
			}
		}
	}
	
	// 描画処理
	public void draw(Graphics g, int screenX, int screenY){
		g.drawImage(Data.image.signboardImage,
			x-screenX, y-screenY, x-screenX+width, y-screenY+height,
			0, 0, width, height,
			null
		);
	}
}