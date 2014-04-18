package game.zones;
import game.common.Card;
import game.common.Data;
import game.common.Card.Mark;

import java.awt.Graphics;
import java.util.Random;

public class Deck {
	private Card[] cards = new Card[52];
	private int idx = 0;
	public Deck(){
		init();
	}
	public void init(){
		for(int i = 0; i < 13; i++){
			cards[i] = new Card(Card.Mark.SPADES,i);
			cards[i+13] = new Card(Card.Mark.CLUBS,i);
			cards[i+13*2] = new Card(Card.Mark.HEARTS,i);
			cards[i+13*3] = new Card(Card.Mark.DIAMONDS,i);
		}
		shuffle();
	}
	public Card getTopCard(){
		if(idx<cards.length){
			idx++;
			return cards[idx-1];
		}else{
			return null;
		}
	}
	public void shuffle(){
		Random rand = new Random(System.currentTimeMillis());
		boolean[] shuffled = new boolean[52];
		Card[] tmp = new Card[52];
		int tmpIdx;
		for(int i = 0; i < cards.length; i++){
			do{
				tmpIdx = rand.nextInt(cards.length);
			}while(shuffled[tmpIdx]);
			shuffled[tmpIdx] = true;
			tmp[i] = cards[tmpIdx];
		}
		cards = tmp;
	}
	public void draw(Graphics g, int x, int y){
		for(int i = 0; i < cards.length-idx; i++){
			g.drawImage(Data.images.reverseImage,
					x,y+52-i,Card.WIDTH,Card.HEIGHT,
					null);
		}
	}
	
	public void setByString(String str){
		System.out.println("Deck.setByString");
		String[] cardsStr = str.split(",");
		idx = cards.length-cardsStr.length;
		for(int i = 0; i < cardsStr.length; i++){
			cards[idx+i] = new Card(cardsStr[i]);
		}
	}
	
	public String toString(){
		String ret = "";
		for(int i = idx; i < cards.length; i++){
			ret += cards[i];
			if(i < cards.length-1) ret += ",";
		}
		return ret;
	}
}
