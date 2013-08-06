package Game;

import Game.Common.*;

import java.awt.Graphics;

public class Menu{
	private static final int WEAPON_X = 241;
	private static final int WEAPON_Y = 161;
	private static final int WEAPON_X_DIFF = 59;
	private static final int ELEMENT_X = 242;
	private static final int ELEMENT_Y = 211;
	private static final int ELEMENT_X_DIFF = 59;
	private static final int BODY_X = 242;
	private static final int BODY_Y = 260;
	private static final int BODY_X_DIFF = 59;
	
	private int cursorX = 0;
	private int cursorY = 0;
	
	private boolean menuReleased = false;
	
	private void cursorMove(){
		if(KeyStatus.up){
			cursorY = (cursorY+3)%4;
		}
		if(KeyStatus.down){
			cursorY = (cursorY+1)%4;
		}
		if(KeyStatus.left){
			cursorX = (cursorX+3)%4;
		}
		if(KeyStatus.right){
			cursorX = (cursorX+1)%4;
		}
	}
	
	public void update(){
		if(!KeyStatus.menu) menuReleased = true;
		if(menuReleased && KeyStatus.menu){
			Data.gameStatus = Data.PLAYING;
			menuReleased = false;
			return;
		}
		cursorMove();
	}
	public void draw(Graphics g){
		g.drawImage(Data.image.menuImage,0,0,null);
		for(int i = 0; i < Data.ELEMENT_NUM; i++){
			g.drawImage(Data.image.elementIconImage,
				ELEMENT_X+i*ELEMENT_X_DIFF, ELEMENT_Y,
				ELEMENT_X+i*ELEMENT_X_DIFF+Data.CHIP_SIZE, ELEMENT_Y+Data.CHIP_SIZE,
				i*Data.CHIP_SIZE,0,(i+1)*Data.CHIP_SIZE,Data.CHIP_SIZE,null);
		}
		for(int i = 0; i < Data.WEAPON_NUM; i++){
			g.drawImage(Data.image.weaponIconImage,
				WEAPON_X+i*WEAPON_X_DIFF, WEAPON_Y,
				WEAPON_X+i*WEAPON_X_DIFF+Data.CHIP_SIZE, WEAPON_Y+Data.CHIP_SIZE,
				i*Data.CHIP_SIZE,0,(i+1)*Data.CHIP_SIZE,Data.CHIP_SIZE,null);
		}
		/*
		for(int i = 0; i < Data.BODY_NUM; i++){
			g.drawImage(Data.image.bodyIconImage,
				BODY_X+i*BODY_X_DIFF, BODY_Y,
				BODY_X+i*BODY_X_DIFF+Data.CHIP_SIZE, BODY_Y+Data.CHIP_SIZE,
				i*Data.CHIP_SIZE,0,(i+1)*Data.CHIP_SIZE,Data.CHIP_SIZE,null);
		}
		*/
	}
}