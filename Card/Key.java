import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Key implements KeyListener, Common{
	private MainPanel panel;
	private Cursor cursor;
	
	private boolean upPress;
	private boolean downPress;
	private boolean leftPress;
	private boolean rightPress;
	private boolean spacePress;
	private boolean shiftPress;
	
	public Key(Cursor cursor, MainPanel panel) {
		this.cursor = cursor;
		this.panel = panel;
		panel.setFocusable(true);
		panel.addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(Flag.keyInputable){
			switch (keyCode) {
			case KeyEvent.VK_UP:
				upPress = true;
				cursor.move(UP);
				break;
			case KeyEvent.VK_DOWN:
				downPress = true;
				cursor.move(DOWN);
				break;
			case KeyEvent.VK_LEFT:
				leftPress = true;
				cursor.move(LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				rightPress = true;
				cursor.move(RIGHT);
				break;
			case KeyEvent.VK_SPACE:
				if(!spacePress){
					spacePress = true;
					cursor.move(SPACE);
				}
				break;
			case KeyEvent.VK_SHIFT:
				if(!shiftPress){
					shiftPress = true;
					cursor.move(SHIFT);
				}
				break;
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(Flag.keyInputable){
			switch (keyCode) {
			case KeyEvent.VK_UP:
				upPress = false;
				break;
			case KeyEvent.VK_DOWN:
				downPress = false;
				break;
			case KeyEvent.VK_LEFT:
				leftPress = false;
				break;
			case KeyEvent.VK_RIGHT:
				rightPress = false;
				break;
			case KeyEvent.VK_SPACE:
				spacePress = false;
				break;
			case KeyEvent.VK_SHIFT:
				shiftPress = false;
				break;
			}
		}
	}

	public void keyTyped(KeyEvent e) {
	}
}
