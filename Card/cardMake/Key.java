import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Key implements KeyListener, Common{
	private MainPanel panel;
	private Field field;
	
	public Key(Field field, MainPanel panel) {
		this.panel = panel;
		this.field = field;
		panel.setFocusable(true);
		panel.addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		switch (keyCode) {
		case KeyEvent.VK_SPACE:
			field.output();
			break;
		}
		panel.update();
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}
