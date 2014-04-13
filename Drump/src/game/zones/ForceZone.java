package game.zones;
import game.common.Card;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class ForceZone {
	private class CardArray extends ArrayList<Card>{}
	private CardArray[] forces = new CardArray[4];
	private Field field;
	
	public ForceZone(Field f){
		init(f);
	}
	public void init(Field f){
		field = f;
		for(int i = 0; i < 4; i++){
			forces[i] = new CardArray();
		}
	}
	public int getCardNumber(int i){
		if(i<0 || i >= forces.length) return -1;
		return forces[i].size();
	}
	public boolean set(int position, Card card){
		if(position < 0 || position >= forces.length) return false;
		int sum = 0;
		for(int i = 0; i < forces[position].size(); i++){
			sum += forces[position].get(i).getNumber();
		}
		sum += card.getNumber();
		if(sum > 13){
			return false;
		}
		if(forces[position].size()==0) card.setOpen(true);
		else card.setOpen(false);
		forces[position].add(card);
		return true;
	}
	
	public boolean attacked(int num, Card card){
		return true;
	}
	
	public int getSum(int num){
		int ret = 0;
		for(int i = 0; i < forces[num].size(); i++){
			ret += forces[num].get(i).getNumber();
		}
		return ret;
	}
	
	public void lightUp(Graphics g, int x, int y, Card card){
		int num = card.getNumber();
		g.setColor(new Color(255,0,0,64));
		for(int i = 0; i < forces.length; i++){
			int tmp = getSum(i);
			tmp += num;
			if(tmp <= 13) g.fillRect(x+i*Card.WIDTH,y+forces[i].size()*Card.HEIGHT/2,Card.WIDTH,Card.HEIGHT);
		}
	}
	
	public void drawSum(Graphics g, int x, int y){
		g.setColor(Color.BLACK);
		for(int i = 0; i < forces.length; i++) g.drawString(""+getSum(i),x+Card.WIDTH/3+i*Card.WIDTH,y-5);
	}
	
	public void draw(Graphics g, int x, int y){
		for(int i = 0; i < forces.length; i++){
			g.setColor(Color.BLACK);
			g.drawRect(x+i*Card.WIDTH,y,Card.WIDTH,Card.HEIGHT);
			for(int j = 0; j < forces[i].size(); j++){
				forces[i].get(j).draw(g,x+i*Card.WIDTH,y+j*Card.HEIGHT/2);
			}
		}
	}
	
	public void setByString(String str){
		System.out.println("ForceZone.setByString");
		String[] cardsStr = str.split(",");
		for(int i = 0; i < cardsStr.length; i++){
			String[] cardInfo = cardsStr[i].split(":");
			forces[i].clear();
			if(cardInfo[0].equals("")) continue;
			for(int j = 0; j < cardInfo.length; j++){
				String[] cardDetail = cardInfo[j].split("-");
				Card tmp = new Card(Card.Mark.CLUBS,0);
				if(cardDetail[0].equals("CLUBS")){
					tmp.setMark(Card.Mark.CLUBS);
				}
				if(cardDetail[0].equals("DIAMONDS")){
					tmp.setMark(Card.Mark.DIAMONDS);
				}
				if(cardDetail[0].equals("HEARTS")){
					tmp.setMark(Card.Mark.HEARTS);
				}
				if(cardDetail[0].equals("SPADES")){
					tmp.setMark(Card.Mark.SPADES);
				}
				tmp.setNumber(Integer.parseInt(cardDetail[1]));
				if(cardDetail[2].equals("Open")){
					tmp.setOpen(true);
				}else{
					tmp.setOpen(false);
				}
				forces[i].add(tmp);
			}
		}
	}
	
	public String toString(){
		String ret = "";
		for(int i = 0; i < forces.length; i++){
			for(int j = 0; j < forces[i].size(); j++){
				switch(forces[i].get(j).getMark()){
				case CLUBS:
					ret += "CLUBS-";
					break;
				case DIAMONDS:
					ret += "DIAMONDS-";
					break;
				case HEARTS:
					ret += "HEARTS-";
					break;
				case SPADES:
					ret += "SPADES-";
					break;
				default:
					break;
				}
				ret += forces[i].get(j).getNumber() + "-";
				ret += (forces[i].get(j).getOpen() ? "Open" : "Close");
				if(j < forces[i].size()-1) ret += ":";
			}
			if(i < forces.length-1) ret += ",";
		}
		return ret;
	}
	
}
