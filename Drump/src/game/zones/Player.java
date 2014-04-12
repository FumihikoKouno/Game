package game.zones;
import game.common.Card;

import java.awt.Graphics;


public class Player {
	private Card[] hand = new Card[6];
	private int id;
	private Field field;
	public Player(int id, Field field){
		init(id, field);
	}
	public void init(int id, Field field){
		this.id = id;
		this.field = field;
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
	public Card getCard(int i){
		return hand[i];
	}
	public void removeCard(int i){
		hand[i] = null;
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
	public void draw(Graphics g, int x, int y){
		for(int i = 0; i < hand.length; i++){
			if(hand[i]!=null){
				hand[i].draw(g,x+Card.WIDTH*i,y);
			}
		}
	}
}
