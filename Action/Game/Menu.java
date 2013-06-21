package Game;

import Game.Common.*;

import java.awt.Graphics;

public class Menu{
	private boolean menuReleased = false;
	public void update(){
		if(!KeyStatus.menu) menuReleased = true;
		if(menuReleased && KeyStatus.menu){
			Data.gameStatus = Data.PLAYING;
			menuReleased = false;
			return;
		}
	}
	public void draw(Graphics g){
		g.drawImage(Data.image.menuImage,0,0,null);
	}
}