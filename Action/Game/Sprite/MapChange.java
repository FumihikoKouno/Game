package Game.Sprite;

import Game.Sprite.Player;
import Game.MapData.MapData;
import Game.Common.Data;

import java.awt.Graphics;

public class MapChange extends Sprite{
	private int newID;
	private int px, py;
	private boolean touched = false;
	private Player player;

	public MapChange(int newID, int px, int py, int x, int y){
		super(x,y);
		image = Data.image.coinImage;
		this.newID = newID;
		this.px = px;
		this.py = py;
		width = Data.CHIP_SIZE;
		height = Data.CHIP_SIZE;
	}
	// スプライトのupdate
	public void update(MapData mapData){
		if(touched){
			player.x = px;
			player.y = py;
			mapData.load(newID);
			end = true;
		}
	}
	// プレイヤーがスプライトに触れたときの関数
	public void touch(Sprite s, int dir, int dest){
		if(s instanceof Player){
			player = (Player)s;
			touched = true;
		}
	}
}