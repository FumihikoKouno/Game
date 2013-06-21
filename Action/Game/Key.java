package Game;

import Game.Common.KeyStatus;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Key implements KeyListener{
	public Key() {
	}
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_UP:
			KeyStatus.up = true;
			break;
		case KeyEvent.VK_DOWN:
			KeyStatus.down = true;
			break;
		case KeyEvent.VK_LEFT:
			KeyStatus.left = true;
			break;
		case KeyEvent.VK_RIGHT:
			KeyStatus.right = true;
			break;
		case KeyEvent.VK_ENTER:
			KeyStatus.attack = true;
			break;
		case KeyEvent.VK_SPACE:
			KeyStatus.jump = true;
			break;
		case KeyEvent.VK_SHIFT:
			KeyStatus.pause = true;
			break;
		case KeyEvent.VK_B:
			KeyStatus.dash = true;
			break;
		case KeyEvent.VK_M:
			KeyStatus.menu = true;
			break;
		}
	}
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_UP:
			KeyStatus.up = false;
			break;
		case KeyEvent.VK_DOWN:
			KeyStatus.down = false;
			break;
		case KeyEvent.VK_LEFT:
			KeyStatus.left = false;
			break;
		case KeyEvent.VK_RIGHT:
			KeyStatus.right = false;
			break;
		case KeyEvent.VK_ENTER:
			KeyStatus.attack = false;
			break;
		case KeyEvent.VK_SPACE:
			KeyStatus.jump = false;
			break;
		case KeyEvent.VK_SHIFT:
			KeyStatus.pause = false;
			break;
		case KeyEvent.VK_B:
			KeyStatus.dash = false;
			break;
		case KeyEvent.VK_M:
			KeyStatus.menu = false;
			break;
		}
	}
	public void keyTyped(KeyEvent e){
	}
}
