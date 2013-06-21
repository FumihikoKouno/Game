import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Config implements Common{
	
	private static final int CONFIG_X = 82;
	private static final int CONFIG_DATA_X = 350;
	
	private static final int CONFIG_PLAYER_1_Y = 276;
	private static final int CONFIG_PLAYER_2_Y = 351;
	private static final int CONFIG_COST_SET_Y = 424;
	
	private static final int CONFIG_SELECT_PLAYER_1 = 0;
	private static final int CONFIG_SELECT_PLAYER_2 = 1;
	private static final int CONFIG_SELECT_COST = 2;
	
	private Image configImage;
	private Image cursorImage;
	private Image playerImage;
	private Image numberImage;
	
	private int cursor;
	
	public Config() {
		loadImage();
	}
	public void init(){
		cursor = 0;
	}
	public void moveCursor(int dir){
		cursor += dir;
		if(cursor < 0) cursor = 2;
		if(cursor > 2) cursor = 0;
	}
	public void select(int dir){
		switch(cursor){
		case CONFIG_SELECT_PLAYER_1:
			Flag.player1 ^= 1;
			break;
		case CONFIG_SELECT_PLAYER_2:
			Flag.player2 ^= 1;
			break;
		case CONFIG_SELECT_COST:
			Flag.initCost += dir;
			Flag.initCost = Math.min(99,Flag.initCost);
			Flag.initCost = Math.max(1,Flag.initCost);
			break;
		}
	}
	public void endConfig(){
		Flag.initialize = false;
		Flag.config = false;
		Flag.title = true;
	}
	
	public void draw(Graphics g) {
		g.drawImage(configImage,0,0,null);
		int cursorX = CONFIG_X;
		int cursorY = 0;
		switch(cursor){
		case CONFIG_SELECT_PLAYER_1:
			cursorY = CONFIG_PLAYER_1_Y;
			break;
		case CONFIG_SELECT_PLAYER_2:
			cursorY = CONFIG_PLAYER_2_Y;
			break;
		case CONFIG_SELECT_COST:
			cursorY = CONFIG_COST_SET_Y;
			break;
		}
		g.drawImage(cursorImage,cursorX,cursorY,null);
		g.drawImage(playerImage,
			CONFIG_DATA_X,CONFIG_PLAYER_1_Y,
			CONFIG_DATA_X + 75,CONFIG_PLAYER_1_Y + 27,
			Flag.player1 * 75, 0,
			Flag.player1 * 75 + 75, 27,
			null
		);
		g.drawImage(playerImage,
			CONFIG_DATA_X,CONFIG_PLAYER_2_Y,
			CONFIG_DATA_X + 75,CONFIG_PLAYER_2_Y + 27,
			Flag.player2 * 75, 0,
			Flag.player2 * 75 + 75, 27,
			null
		);
		g.drawImage(numberImage,
			CONFIG_DATA_X,CONFIG_COST_SET_Y,
			CONFIG_DATA_X + 30,CONFIG_COST_SET_Y + 30,
			(Flag.initCost/10) * 30, 0,
			(Flag.initCost/10) * 30 + 30, 30,
			null
		);
		g.drawImage(numberImage,
			CONFIG_DATA_X + 30,CONFIG_COST_SET_Y,
			CONFIG_DATA_X + 60,CONFIG_COST_SET_Y + 30,
			(Flag.initCost%10) * 30, 0,
			(Flag.initCost%10) * 30 + 30, 30,
			null
		);
	}

	private void loadImage() {
		ImageIcon icon = new ImageIcon(getClass().getResource("image/config.gif"));
		configImage = icon.getImage();
		icon = new ImageIcon(getClass().getResource("image/cursorTriangle.gif"));
		cursorImage = icon.getImage();
		icon = new ImageIcon(getClass().getResource("image/playerSelect.gif"));
		playerImage = icon.getImage();
		icon = new ImageIcon(getClass().getResource("image/costNumber.gif"));
		numberImage = icon.getImage();
	}
}
