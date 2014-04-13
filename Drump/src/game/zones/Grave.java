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
	
	public void drawRecycleCards(Graphics g){
		ArrayList<Card> parts = getPartsCards();
		int row = 0;
		for(int i = 0, idx = 0; i < parts.size(); i++,idx++){
			if((idx+1)*Card.WIDTH > Field.WIDTH){
				idx = 0;
				row++;
			}
			parts.get(i).draw(g,idx*Card.WIDTH,row*Card.HEIGHT);
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
			String[] cardInfo = cardsStr[i].split(":");
			Card tmp = new Card(Card.Mark.CLUBS,0);
			if(cardInfo[0].equals("CLUBS")){
				tmp.setMark(Card.Mark.CLUBS);
			}
			if(cardInfo[0].equals("DIAMONDS")){
				tmp.setMark(Card.Mark.DIAMONDS);
			}
			if(cardInfo[0].equals("HEARTS")){
				tmp.setMark(Card.Mark.HEARTS);
			}
			if(cardInfo[0].equals("SPADES")){
				tmp.setMark(Card.Mark.SPADES);
			}
			tmp.setNumber(Integer.parseInt(cardInfo[1]));
			tmp.setOpen(true);
			cards.add(tmp);
		}
	}
	
	public String toString(){
		String ret = "";
		for(int i = 0; i < cards.size(); i++){
			switch(cards.get(i).getMark()){
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
			default:
				break;
			}
			ret += cards.get(i).getNumber();
			if(i < cards.size()-1) ret += ",";
		}
		return ret;
	}
	
}
