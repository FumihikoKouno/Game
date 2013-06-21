import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Title implements Common{
	
	private static final int TITLE_X = 365;
	
	private static final int TITLE_START_Y = 351;
	private static final int TITLE_CONFIG_Y = 425;
	private static final int TITLE_EXIT_Y = 499;
	
	private static final int TITLE_SELECT_START = 0;
	private static final int TITLE_SELECT_CONFIG = 1;
	private static final int TITLE_SELECT_EXIT = 2;
	
	private Image titleImage;
	private Image cursorImage;
	
	private int cursor;
	
	public Title() {
		loadImage();
	}
	public void moveCursor(int dir){
		cursor += dir;
		if(cursor < 0) cursor = 2;
		if(cursor > 2) cursor = 0;
	}
	
	public void init(){
		cursor = 0;
	}
	
	public void select(){
		Flag.title = false;
		Flag.initialize = false;
		switch(cursor){
		case TITLE_SELECT_START:
			Flag.game = true;
			break;
		case TITLE_SELECT_CONFIG:
			Flag.config = true;
			break;
		case TITLE_SELECT_EXIT:
			System.exit(0);
			break;
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(titleImage,0,0,null);
		int cursorX = TITLE_X;
		int cursorY = 0;
		switch(cursor){
		case TITLE_SELECT_START:
			cursorY = TITLE_START_Y;
			break;
		case TITLE_SELECT_CONFIG:
			cursorY = TITLE_CONFIG_Y;
			break;
		case TITLE_SELECT_EXIT:
			cursorY = TITLE_EXIT_Y;
			break;
		}
		g.drawImage(cursorImage,cursorX,cursorY,null);
	}

	private void loadImage() {
		ImageIcon icon = new ImageIcon(getClass().getResource("image/title.gif"));
		titleImage = icon.getImage();
		icon = new ImageIcon(getClass().getResource("image/cursorTriangle.gif"));
		cursorImage = icon.getImage();
	}
}
