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
	
	public Card(){
		mark = Mark.SPADES;
		number = 0;
	}
	
	public Card(String str){
		String[] cardInfo = str.split(":");
		if(cardInfo[0].equals("SPADES")){
			setMark(Mark.SPADES);
		}
		if(cardInfo[0].equals("DIAMONDS")){
			setMark(Mark.DIAMONDS);
		}
		if(cardInfo[0].equals("HEARTS")){
			setMark(Mark.HEARTS);
		}
		if(cardInfo[0].equals("CLUBS")){
			setMark(Mark.CLUBS);
		}
		setNumber(Integer.parseInt(cardInfo[1]));
	}
	
	public Card(Mark mark, int number){
		this.mark = mark;
		this.number = number;
	}
	
	public void setMark(Mark m){
		this.mark = m;
	}
	
	public void setNumber(int num){
		this.number = num-1;
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
	
	public boolean getOpen(){
		return open;
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
	
	public String toString(){
		String ret = "";
		switch(mark){
		case CLUBS:
			ret += "CLUBS:";
			break;
		case DIAMONDS:
			ret += "DIAMONDS:";
			break;
		case HEARTS:
			ret += "HEARTS:";
			break;
		case SPADES:
			ret += "SPADES:";
			break;
		}
		ret += getNumber();
		return ret;
	}
	
}
