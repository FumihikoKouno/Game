import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Key implements KeyListener, Common{
	
	private MainPanel panel;
	
	public Key(MainPanel panel) {
		this.panel = panel;
		panel.setFocusable(true);
		panel.addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_UP:
			panel.keyPressed(KEY_UP);
			break;
		case KeyEvent.VK_DOWN:
			panel.keyPressed(KEY_DOWN);
			break;
		case KeyEvent.VK_LEFT:
			panel.keyPressed(KEY_LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			panel.keyPressed(KEY_RIGHT);
			break;
		case KeyEvent.VK_A:
			panel.keyPressed(KEY_A);
			break;
		case KeyEvent.VK_S:
			panel.keyPressed(KEY_S);
			break;
		case KeyEvent.VK_SPACE:
			panel.keyPressed(KEY_SPACE);
			break;
		case KeyEvent.VK_SHIFT:
			panel.keyPressed(KEY_SHIFT);
			break;
		}
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
	}
}
