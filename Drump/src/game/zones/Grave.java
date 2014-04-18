package game.zones;
import game.common.Card;

import java.awt.Graphics;
import java.util.ArrayList;

import com.sun.jndi.url.dns.dnsURLContext;


public class Grave {
	private ArrayList<Card> cards = new ArrayList<Card>();
	public Grave(){}
	
	public void addCard(Card card){
		card.setOpen(true);
		cards.add(card);
	}
	
	public ArrayList<Card> getPartsCards(){
		ArrayList<Card> ret = new ArrayList<Card>();
		for(int i = 0; i < cards.size(); i++){
			if(cards.get(i).getNumber() >= 10){
				ret.add(cards.get(i));
			}
		}
		return ret;
	}
	
	public void drawRecycleCards(Graphics g, int x, int y){
		ArrayList<Card> parts = getPartsCards();
		int row = 0;
		for(int i = 0, idx = 0; i < parts.size(); i++,idx++){
			if(x+(idx+1)*Card.WIDTH > Field.WIDTH){
				idx = 0;
				row++;
			}
			parts.get(i).draw(g,x+idx*Card.WIDTH,y+row*Card.HEIGHT);
		}
	}
	
	public void draw(Graphics g, int x, int y){
		for(int i = 0; i < cards.size(); i++){
			cards.get(i).draw(g,x,y+52-i);
		}
	}
	
	public void setByString(String str){
		cards.clear();
		String[] cardsStr = str.split(",");
		for(int i = 0; i < cardsStr.length; i++){
			Card tmp = new Card(cardsStr[i]);
			tmp.setOpen(true);
			cards.add(tmp);
		}
	}
	
	public String toString(){
		String ret = "";
		for(int i = 0; i < cards.size(); i++){
			ret += cards.get(i);
			if(i < cards.size()-1) ret += ",";
		}
		return ret;
	}
	
}
