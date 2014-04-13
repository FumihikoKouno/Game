package game.zones;
import game.common.Card;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class ForceZone {
	private class CardArray extends ArrayList<Card>{}
	private CardArray[] forces = new CardArray[4];
	private Field field;
	
	private boolean[] charged = new boolean[4];
	
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
		return forces[i].size();
	}
	public boolean set(int position, Card card){
		if(position < 0 || position >= forces.length || charged[position]) return false;
		charged[position] = true;
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
	
	public int getOnesNumber(int num){
		int ret = 0;
		for(int j = 0; j < forces[num].size(); j++){
			if(forces[num].get(j).getNumber()==1) ret++;
		}
		return ret;
	}
	
	public void turnEnd(){
		for(int i = 0; i < 4; i++) charged[i] = false;
	}
	
	public boolean canCharge(){
		boolean ret = true;
		for(int i = 0; i < 4; i++) ret = ret && (charged[i] || getSum(i)==13);
		return !ret;
	}
	
	public boolean canSummon(int idx, int num){
		int tmp = getSum(idx);
		if(tmp == num) return true;
		for(int i = 0; i < getOnesNumber(idx); i++){
			if(tmp+10>13) break;
			tmp += 10;
			if(tmp == num) return true;
		}
		return false;
	}
	
	public void lightUpForce(Graphics g, int x, int y, int num){
		g.setColor(new Color(255,0,0,64));
		for(int i = 0; i < forces.length; i++){
			int tmp = getSum(i);
			if(tmp==num) g.fillRect(x+i*Card.WIDTH,y,Card.WIDTH,(forces[i].size()+1)*Card.HEIGHT/2);
			for(int j = 0; j < getOnesNumber(i); j++){
				if(tmp+10 > 13) break;
				if(tmp+10 == num) g.fillRect(x+i*Card.WIDTH,y,Card.WIDTH,(forces[i].size()+1)*Card.HEIGHT/2);
			}
		}
	}
	
	public void lightUp(Graphics g, int x, int y, Card card){
		int num = card.getNumber();
		g.setColor(new Color(255,0,0,64));
		for(int i = 0; i < forces.length; i++){
			if(charged[i]) continue;
			int tmp = getSum(i);
			tmp += num;
			if(tmp <= 13) g.fillRect(x+i*Card.WIDTH,y+forces[i].size()*Card.HEIGHT/2,Card.WIDTH,Card.HEIGHT);
		}
	}
	
	public void clear(int i){
		forces[i].clear();
	}
	
	public ArrayList<Card> getCards(int i){
		return forces[i];
	}
	
	public void drawSum(Graphics g, int x, int y){
		g.setColor(Color.BLACK);
		for(int i = 0; i < forces.length; i++){
			int tmp = getSum(i);
			g.drawString(""+tmp,x+Card.WIDTH/3+i*Card.WIDTH,y-5);
			for(int j = 0; j < getOnesNumber(i); j++){
				if(tmp+10>13) break;
				tmp = tmp+10;
				g.drawString(""+tmp,x+Card.WIDTH/3+i*Card.WIDTH,y-10*(j+2));
			}
		}
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
		if(cardsStr.length==0){
			for(int i = 0; i < 4; i++) clear(i);
		}
		for(int i = 0; i < cardsStr.length; i++){
			System.out.println("in loop");
			String[] cardInfo = cardsStr[i].split(":");
			forces[i].clear();
			if(cardsStr[i].equals("")) continue;
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
