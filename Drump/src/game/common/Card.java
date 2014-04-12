package game.common;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Card {
	public static final int WIDTH = 30;
	public static final int HEIGHT = 40;
	public enum Mark{
		SPADES,
		CLUBS,
		HEARTS,
		DIAMONDS,
	};
	
	Mark mark;
	int number;
	public boolean open;
	
	public Card(Mark mark, int number){
		this.mark = mark;
		this.number = number;
	}
	
	public void setOpen(boolean o){
		open = o;
	}
	
	public Mark getMark(){
		return mark;
	}
	
	public int getNumber(){
		return number+1;
	}
	
	public void draw(Graphics g, int x, int y){
		if(open){
			int dy = 0;
			switch(mark){
			case SPADES:
				dy = 0;
				break;
			case CLUBS:
				dy = HEIGHT;
				break;
			case HEARTS:
				dy = 2*HEIGHT;
				break;
			case DIAMONDS:
				dy = 3*HEIGHT;
				break;
			default:
				break;
			}
			g.drawImage(Data.images.cardsImage,
					x, y, x+WIDTH, y+HEIGHT,
					number*WIDTH,       dy,
					number*WIDTH+WIDTH, dy+HEIGHT,
					null);
		}else{
			g.drawImage(Data.images.reverseImage, x, y, null);
		}
	}
}
