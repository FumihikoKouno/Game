package game.zones;


import game.common.Card;

import java.awt.Graphics;


public class MonsterPartsZone {
	public enum Parts{
		HEAD,
		WING,
		BODY,
		TAIL,
	};
	public Card[] parts = new Card[4];
	private Field field;
	
	public MonsterPartsZone(Field f){
		init(f);
	}
	public void init(Field f){
		field = f;
	}
	
	public int drump(){
		int ret = 0;
		int[] count = new int[4];
		for(int i = 0; i < parts.length; i++){
			if(parts[i] == null) return 0;
			count[parts[i].getNumber()-10]++;
		}
		for(int i = 0; i < count.length; i++){
			// TODO implement yaku
		}
		return ret;
	}
	
	public boolean set(Card card){
		card.setOpen(true);
		switch(card.getMark()){
		case SPADES:
			parts[0] = card;
			break;
		case CLUBS:
			parts[1] = card;
			break;
		case HEARTS:
			parts[2] = card;
			break;
		case DIAMONDS:
			parts[3] = card;
			break;
		default:
			break;
		}
		return true;
	}
	
	public void draw(Graphics g, int x, int y){
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 2; j++){
				g.drawRect(x+j*Card.WIDTH,y+i*Card.HEIGHT,Card.WIDTH,Card.HEIGHT);
				if(parts[i*2+j] == null) continue;
				parts[i*2+j].draw(g,x+j*Card.WIDTH,y+i*Card.HEIGHT);
			}
		}
	}
	
	public void setByString(String str){
		String[] cardsStr = str.split(",");
		for(int i = 0; i < parts.length; i++) parts[i] = null;
		for(int i = 0; i < cardsStr.length; i++){
			String[] cardInfo = cardsStr[i].split(":");
			int idx = Integer.parseInt(cardInfo[0]);
			parts[idx] = new Card(Card.Mark.CLUBS,0);
			if(cardInfo[1].equals("CLUBS")){
				parts[idx].setMark(Card.Mark.CLUBS);
			}
			if(cardInfo[1].equals("DIAMONDS")){
				parts[idx].setMark(Card.Mark.DIAMONDS);
			}
			if(cardInfo[1].equals("HEARTS")){
				parts[idx].setMark(Card.Mark.HEARTS);
			}
			if(cardInfo[1].equals("SPADES")){
				parts[idx].setMark(Card.Mark.SPADES);
			}
			parts[idx].setNumber(Integer.parseInt(cardInfo[2]));
			parts[idx].setOpen(true);
		}
	}
	
	public String toString(){
		String ret = "";
		for(int i = 0; i < 4; i++){
			if(parts[i]!=null){
				if(!ret.equals("")) ret += ",";
				ret += i+":";
				switch(parts[i].getMark()){
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
				ret += parts[i].getNumber();
			}
		}
		return ret;
	}
	
}
