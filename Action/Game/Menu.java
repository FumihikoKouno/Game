package Game;

import Game.Common.*;

import java.awt.Graphics;
import java.awt.Color;

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
	
	private static final int SELECT_ALL = 0;
	private static final int SELECT_WEAPON = 1;
	private static final int SELECT_ELEMENT = 2;
	private static final int SELECT_BODY = 3;
	
	private static final int LIFE = 0;
	private static final int WEAPON = 1;
	private static final int EXIT = 2;
	
	
	private int cursorX = 0;
	private int cursorY = 0;
	
	private int mode = SELECT_ALL;
	
	private boolean menuReleased = false;
	private boolean upReleased = false;
	private boolean downReleased = false;
	private boolean leftReleased = false;
	private boolean rightReleased = false;
	private boolean attackReleased = false;
	
	private void cursorMove(){
		switch(mode){
		case SELECT_ALL:
			if(KeyStatus.up){
				if(upReleased){
					cursorY = (cursorY+2)%3;
					upReleased = false;
				}
			}else{
				upReleased = true;
			}
			if(KeyStatus.down){
				if(downReleased){
					cursorY = (cursorY+1)%3;
					downReleased = false;
				}
			}else{
				downReleased = true;
			}
			break;
		case SELECT_WEAPON:
		case SELECT_ELEMENT:
		case SELECT_BODY:
			if(KeyStatus.left){
				if(leftReleased){
					cursorX = (cursorX+3)%4;
					leftReleased = false;
				}
			}else{
				leftReleased = true;
			}
			if(KeyStatus.right){
				if(rightReleased){
					cursorX = (cursorX+1)%4;
					rightReleased = false;
				}
			}else{
				rightReleased = true;
			}
			break;
		}
	}
	
	private void enter(){
		if(KeyStatus.attack){
			if(attackReleased){
				attackReleased = false;
				switch(mode){
				case SELECT_ALL:
					switch(cursorY){
					case WEAPON:
						mode = SELECT_WEAPON;
						cursorX = StateData.player.weaponID;
						break;
					case EXIT:
						exit();
						break;
					}
					break;
				case SELECT_WEAPON:
					mode = SELECT_ELEMENT;
					StateData.player.weaponID = cursorX%2;
					cursorX = StateData.player.element;
					break;
				case SELECT_ELEMENT:
					mode = SELECT_BODY;
					StateData.player.element = cursorX;
					cursorX = StateData.player.bodyID;
					break;
				case SELECT_BODY:
					mode = SELECT_ALL;
					StateData.player.equipBody(cursorX%2);
					cursorX = 0;
					break;
				}
			}
		}else{
			attackReleased = true;
		}
	}
	
	private void exit(){
		Data.gameStatus = Data.PLAYING;
		menuReleased = false;
		cursorX = 0;
		cursorY = 0;
		mode = SELECT_ALL;
		KeyStatus.setAll(false);
		return;
	}
	
	public void update(){
		if(!KeyStatus.menu) menuReleased = true;
		if(menuReleased && KeyStatus.menu){
			exit();
			return;
		}
		cursorMove();
		enter();
	}
	
	private void drawCursor(Graphics g){
		int cx = 0;
		int cy = 0;
		int cw = 0;
		int ch = 0;
		g.setColor(Color.WHITE);
		switch(mode){
		case SELECT_ALL:
			switch(cursorY){
			case 0:
				cx = 25;
				cy = 31;
				cw = 83;
				ch = 43;
				break;
			case 1:
				cx = 25;
				cy = 86;
				cw = 171;
				ch = 49;
				break;
			case 2:
				cx = 28;
				cy = 357;
				cw = 83;
				ch = 46;
				break;
			}
			break;
		case SELECT_WEAPON:
			cy = 156;
			cw = 42;
			ch = 42;
			switch(cursorX){
			case 0:
				cx = 236;
				break;
			case 1:
				cx = 295;
				break;
			case 2:
				cx = 354;
				break;
			case 3:
				cx = 413;
				break;
			}
			break;
		case SELECT_ELEMENT:
			cy = 205;
			cw = 42;
			ch = 42;
			switch(cursorX){
			case 0:
				cx = 236;
				break;
			case 1:
				cx = 295;
				break;
			case 2:
				cx = 354;
				break;
			case 3:
				cx = 413;
				break;
			}
			break;
		case SELECT_BODY:
			cy = 255;
			cw = 42;
			ch = 42;
			switch(cursorX){
			case 0:
				cx = 236;
				break;
			case 1:
				cx = 295;
				break;
			case 2:
				cx = 354;
				break;
			case 3:
				cx = 413;
				break;
			}
			break;
		}
		for(int i = 0; i < 3; i++){
			g.drawRect(cx-i,cy-i,cw+(i<<1),ch+(i<<1));
		}
	}
	
	private void drawEquipment(Graphics g){
		int cx = 0;
		int cy = 0;
		int cw = 0;
		int ch = 0;
		g.setColor(new Color(255,255,255,128));
		// weapon
		cy = 156;
		cw = 42;
		ch = 42;
		switch(StateData.player.weaponID){
		case 0:
			cx = 236;
			break;
		case 1:
			cx = 295;
			break;
		case 2:
			cx = 354;
			break;
		case 3:
			cx = 413;
			break;
		}
		g.fillRect(cx,cy,cw,ch);
		// element
		cy = 205;
		cw = 42;
		ch = 42;
		switch(StateData.player.element){
		case 0:
			cx = 236;
			break;
		case 1:
			cx = 295;
			break;
		case 2:
			cx = 354;
			break;
		case 3:
			cx = 413;
			break;
		}
		g.fillRect(cx,cy,cw,ch);
		// body
		cy = 255;
		cw = 42;
		ch = 42;
		switch(StateData.player.bodyID){
		case 0:
			cx = 236;
			break;
		case 1:
			cx = 295;
			break;
		case 2:
			cx = 354;
			break;
		case 3:
			cx = 413;
			break;
		}
		g.fillRect(cx,cy,cw,ch);
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
		for(int i = 0; i < Data.BODY_NUM; i++){
			g.drawImage(Data.image.bodyIconImage,
				BODY_X+i*BODY_X_DIFF, BODY_Y,
				BODY_X+i*BODY_X_DIFF+Data.CHIP_SIZE, BODY_Y+Data.CHIP_SIZE,
				i*Data.CHIP_SIZE,0,(i+1)*Data.CHIP_SIZE,Data.CHIP_SIZE,null);
		}
		drawCursor(g);
		drawEquipment(g);
	}
}