package Game.Sprite.Item;

import Game.Sprite.Sprite;
import Game.Sprite.Player;
import Game.Common.Data;
import Game.Common.StateData;
import Game.Sprite.Weapon.Element;

import java.awt.Graphics;
import java.awt.Color;

public class ElementItem extends Sprite{

	private int elem;
	
	public ElementItem(int x, int y, int elem){
		super(x,y);
		this.elem = elem;
		width = Data.CHIP_SIZE;
		height = Data.CHIP_SIZE;
	}
	// スプライトのupdate
	public void update(){
		vx = vy = 0;
	}
	// プレイヤーがスプライトに触れたときの関数
	public void touch(Sprite s, int dir, int[] dest){
		if(s instanceof Player){
			StateData.gotElement[elem]++;
			end = true;
		}
	}
	
	public void draw(Graphics g, int screenX, int screenY){
		g.setColor(Color.BLACK);
		g.fillOval(x-screenX,y-screenY,width,height);
		g.setColor(Color.WHITE);
		g.drawOval(x-screenX,y-screenY,width,height);
		g.drawImage(Data.image.elementIconImage,
			x-screenX, y-screenY, x-screenX+Data.CHIP_SIZE, y-screenY+Data.CHIP_SIZE,
			elem*Data.CHIP_SIZE,0,(elem+1)*Data.CHIP_SIZE,Data.CHIP_SIZE,
			null);
	}
}