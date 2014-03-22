import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyStatus implements KeyListener{
	public static boolean up,down,left,right;
	public static boolean enter;
	
	public KeyStatus(){
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_UP:
			up = true;
			break;
		case KeyEvent.VK_DOWN:
			down = true;
			break;
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		case KeyEvent.VK_ENTER:
			enter = true;
			break;
		default:
			break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		case KeyEvent.VK_ENTER:
			enter = false;
			break;
		default:
			break;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
