package game.zones;
import game.common.Card;

import java.awt.Color;
import java.awt.Graphics;


public class Player {
	private Card[] hand = new Card[6];
	private Field field;
	public Player(Field field){
		this.field = field;
	}
	public void init(){
		for(int i = 0; i < 5; i++){
			hand[i] = field.drawCard();
			hand[i].setOpen(true);
		}
	}
	public void drawCard(Card card){
		for(int i = 0; i < hand.length; i++){
			if(hand[i]==null){
				hand[i] = card;
				hand[i].setOpen(true);
				return;
			}
		}
	}
	
	public int getHandNumber(){
		for(int i = 0; i < 6; i++){
			if(hand[i]==null) return i;
		}
		return 6;
	}
	public Card getCard(int i){
		return hand[i];
	}
	
	public void pack(){
		for(int j = 0; j < hand.length; j++){
			if(hand[j]==null){
				for(int k = j+1; k < hand.length; k++){
					if(hand[k]!=null){
						hand[j] = hand[k];
						hand[k] = null;
						break;
					}
				}
			}
		}
	}
	public void removeCard(int i){
		hand[i] = null;
	}
	
	public void lightUpJack(Graphics g, int x, int y){
		g.setColor(new Color(255,0,0,64));
		for(int i = 0; i < hand.length; i++){
			if(hand[i] != null){
				if(hand[i].getNumber() == 11) g.fillRect(x+Card.WIDTH*i,y,Card.WIDTH,Card.HEIGHT);
			}
		}
	}
	
	public void lightUpBlockableCards(Graphics g, int x, int y, int num){
		g.setColor(new Color(255,0,0,64));
		for(int i = 0; i < hand.length; i++){
			if(hand[i] != null){
				if(hand[i].getNumber()>num || hand[i].getNumber() == 1) g.fillRect(x+Card.WIDTH*i,y,Card.WIDTH,Card.HEIGHT);
			}
		}
	}
	
	public void draw(Graphics g, int x, int y){
		for(int i = 0; i < hand.length; i++){
			if(hand[i]!=null){
				hand[i].draw(g,x+Card.WIDTH*i,y);
			}
		}
	}
	
	public void setByString(String str){
		System.out.println("Player.setByString");
		String[] cardsStr = str.split(",");
		for(int i = 0; i < hand.length; i++){
			hand[i] = null;
		}
		if(str.equals("")) return;
		for(int i = 0; i < cardsStr.length; i++){
			hand[i] = new Card(cardsStr[i]);
		}
	}
	
	public String toString(){
		String ret = "";
		for(int i = 0; i < hand.length; i++){
			if(hand[i] == null) break;
			if(i > 0) ret += ",";
			ret += hand[i];
		}
		return ret;
	}
	public void setOpen(boolean b) {
		for(int i = 0; i < hand.length; i++){
			if(hand[i] == null) break;
			hand[i].setOpen(b);
		}
	}
}
