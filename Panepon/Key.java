import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Key implements KeyListener{
	public Key() {
	}
	public void keyPressed(KeyEvent e){
		if(Data.keyCansel) return;
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
		case KeyEvent.VK_SPACE:
			KeyStatus.change = true;
			break;
		case KeyEvent.VK_SHIFT:
		    KeyStatus.scroll = true;
		    break;
		case KeyEvent.VK_ENTER:
		    KeyStatus.enter = true;
		    break;
		}
	}
	public void keyReleased(KeyEvent e){
		if(Data.keyCansel) return;
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
		case KeyEvent.VK_SPACE:
			KeyStatus.change = false;
			break;
		case KeyEvent.VK_SHIFT:
		    KeyStatus.scroll = false;
		    break;
		case KeyEvent.VK_ENTER:
		    KeyStatus.enter = false;
		    break;
		}
	}
	public void keyTyped(KeyEvent e){
	}
}
