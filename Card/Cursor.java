import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Cursor implements Common{
	
	private Image cursorImage;
	private Title title;
	private Config config;
	private Field field;
	
	private Point cursor;
	private int menuCursor;
	private int endConfirm;
	private int dropCard = MAX_HAND_NUMBER;
	
	private Point endCursor;
	
	public Cursor(Title title, Config config, Field field){
		this.title = title;
		this.config = config;
		this.field = field;
		cursor = new Point();
		endCursor = new Point();
		loadImage();
	}
	
	public void setCursor(Point p){
		cursor = p;
	}

	public void move(int dir){
		if(Flag.game){
			moveGame(dir);
		}
		if(Flag.title){
			moveTitle(dir);
		}
		if(Flag.config){
			moveConfig(dir);
		}
	}
	public void moveConfig(int dir){
		switch(dir){
		case UP:
			config.moveCursor(-1);
			break;
		case DOWN:
			config.moveCursor(1);
			break;
		case LEFT:
			config.select(-1);
			break;
		case RIGHT:
			config.select(1);
			break;
		case SHIFT:
			config.endConfig();
			break;
		}
	}
	public void moveTitle(int dir){
		switch(dir){
		case UP:
			title.moveCursor(-1);
			break;
		case DOWN:
			title.moveCursor(1);
			break;
		case SPACE:
			title.select();
			break;
		}
	}
	
	public void moveGame(int dir){
		if(field.getDropCard()){
			switch(dir){
			case LEFT:
				if(field.getDecideDropCard()){
					field.setDecideSelect(0);
				}else{
					dropCard = Math.max(0,dropCard-1);
					field.selectDropCard(dropCard);
				}
				break;
			case RIGHT:
				if(field.getDecideDropCard()){
					field.setDecideSelect(1);
				}else{
					dropCard = Math.min(MAX_HAND_NUMBER,dropCard+1);
					field.selectDropCard(dropCard);
				}
				break;
			case SPACE:
				if(field.getDecideDropCard()){
					if(field.handExchange()){
						if(dropCard < MAX_HAND_NUMBER){
							if(field.getTurn() > 0){
								cursor.x = dropCard;
								cursor.y = ROW;
							}else{
								cursor.x = dropCard+2;
								cursor.y = -1;
							}
						}else{
							if(field.getTurn() > 0){
								cursor.x = MAX_HAND_NUMBER-1;
								cursor.y = ROW;
							}else{
								cursor.x = MAX_HAND_NUMBER+1;
								cursor.y = -1;
							}
						}
						dropCard = MAX_HAND_NUMBER;
						field.selectDropCard(dropCard);
						field.select(cursor);
					}
				}else{
					field.decideDropCard();
				}
				return;
			}
		}else if(field.getEndConfirm()){
			switch(dir){
			case LEFT:
				endConfirm = 0;
				field.selectEndConfirm(endConfirm);
				break;
			case RIGHT:
				endConfirm = 1;
				field.selectEndConfirm(endConfirm);
				break;
			case SPACE:
				int turn = field.getTurn();
				field.decideEndConfirm();
				if(turn != field.getTurn()){
					Point temp = cursor;
					cursor = endCursor;
					field.select(cursor);
					endCursor = temp;
				}
				return;
			case SHIFT:
				field.deleteEndConfirm();
				return;
			}
		}else if(field.getMenu()){
			switch(dir){
			case UP:
				menuCursor = Math.max(0,menuCursor-1);
				break;
			case DOWN:
				menuCursor = Math.min(MENU_NUMBER-1, menuCursor+1);
				break;
			case SPACE:
				field.select(menuCursor);
				field.decideMenu();
				field.deleteMenu();
				menuCursor = 0;
				return;
			case SHIFT:
				field.deleteMenu();
				menuCursor = 0;
				return;
			}
			field.select(menuCursor);
		}else{
			switch(dir){
			case UP:
				if(cursor.y >= 0){
					cursor.y--;
				}
				if(cursor.y == -1){
					cursor.x = Math.min(MAX_HAND_NUMBER+1, cursor.x);
				}
				break;
			case DOWN:
				if(cursor.y < ROW){
					cursor.y++;
				}
				if(cursor.y == ROW){
					cursor.x = Math.min(MAX_HAND_NUMBER+1, cursor.x);
				}
				break;
			case LEFT:
				if(cursor.x > 0){
					cursor.x--;
				}
				break;
			case RIGHT:
				if(cursor.y == -1 || cursor.y == ROW){
					if(cursor.x < MAX_HAND_NUMBER+1){
						cursor.x++;
					}
				}else if(cursor.x < COL-1){
					cursor.x++;
				}
				break;
			case SPACE:
				if(field.getSelectedMenu() >= 0){
					field.selectTarget(cursor);
				}else{
					field.setMenu(cursor);
				}
				return;
			case SHIFT:
				if(field.getSelectedMenu() >= 0){
					field.cancelSelectTarget();
				}else{
					field.setEndConfirm();
				}
				return;
			}
			field.select(cursor);
		}
	}
	
	public void draw(Graphics g){
		if(cursor.y == -1){
			if(cursor.x == 0){
				g.drawImage(cursorImage, DECK2_X, DECK2_Y, null);
				return;
			}
			if(cursor.x == 1){
				g.drawImage(cursorImage, GRAVE2_X, GRAVE2_Y, null);
				return;
			}
			if(cursor.x >= 2){
				g.drawImage(cursorImage, HAND2_X + (cursor.x-2) * CS, HAND2_Y, null);
				return;
			}
		}
		if(cursor.y == ROW){
			if(cursor.x < MAX_HAND_NUMBER){
				g.drawImage(cursorImage, HAND1_X + cursor.x * CS, HAND1_Y, null);
				return;
			}
			if(cursor.x == MAX_HAND_NUMBER){
				g.drawImage(cursorImage, GRAVE1_X, GRAVE1_Y, null);
				return;
			}
			if(cursor.x == MAX_HAND_NUMBER+1){
				g.drawImage(cursorImage, DECK1_X, DECK1_Y, null);
				return;
			}
		}
		g.drawImage(cursorImage, FIELD_X + cursor.x * CS, FIELD_Y + cursor.y * CS, null);
		return;
	}
	
	private void loadImage(){
		ImageIcon icon = new ImageIcon(getClass().getResource("image/cursor.gif"));
		cursorImage = icon.getImage();
	}
}
