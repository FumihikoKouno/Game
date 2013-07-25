import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Key implements KeyListener{
	public Key() {
	}
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_UP:
			if(Data.keyCansel) return;
			KeyStatus.up = true;
			break;
		case KeyEvent.VK_DOWN:
			if(Data.keyCansel) return;
			KeyStatus.down = true;
			break;
		case KeyEvent.VK_LEFT:
			if(Data.keyCansel) return;
			KeyStatus.left = true;
			break;
		case KeyEvent.VK_RIGHT:
			if(Data.keyCansel) return;
			KeyStatus.right = true;
			break;
		case KeyEvent.VK_SPACE:
			if(Data.keyCansel) return;
			KeyStatus.change = true;
			break;
		case KeyEvent.VK_SHIFT:
			if(Data.keyCansel) return;
			KeyStatus.scroll = true;
			break;
		case KeyEvent.VK_ENTER:
			if(Data.keyCansel) return;
			KeyStatus.enter = true;
			break;
		case KeyEvent.VK_T:
			KeyStatus.toTitle = true;
			break;
		case KeyEvent.VK_R:
			KeyStatus.retry = true;
			break;
		case KeyEvent.VK_H:
			KeyStatus.hard = true;
			break;
		}
	}
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_UP:
			if(Data.keyCansel) return;
			KeyStatus.up = false;
			break;
		case KeyEvent.VK_DOWN:
			if(Data.keyCansel) return;
			KeyStatus.down = false;
			break;
		case KeyEvent.VK_LEFT:
			if(Data.keyCansel) return;
			KeyStatus.left = false;
			break;
		case KeyEvent.VK_RIGHT:
			if(Data.keyCansel) return;
			KeyStatus.right = false;
			break;
		case KeyEvent.VK_SPACE:
			if(Data.keyCansel) return;
			KeyStatus.change = false;
			break;
		case KeyEvent.VK_SHIFT:
			if(Data.keyCansel) return;
			KeyStatus.scroll = false;
			break;
		case KeyEvent.VK_ENTER:
			if(Data.keyCansel) return;
			KeyStatus.enter = false;
			break;
		case KeyEvent.VK_T:
			KeyStatus.toTitle = false;
			break;
		case KeyEvent.VK_R:
			KeyStatus.retry = false;
			break;
		case KeyEvent.VK_H:
			KeyStatus.hard = false;
			break;
		}
	}
	public void keyTyped(KeyEvent e){
	}
}
