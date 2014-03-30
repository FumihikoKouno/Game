import java.awt.Graphics;
import java.util.ArrayList;


public class Grave {
	private ArrayList<Card> cards = new ArrayList<Card>();
	public Grave(){}
	
	public void addCard(Card card){
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
	
	public void draw(Graphics g, int x, int y){
		for(int i = 0; i < cards.size(); i++){
			cards.get(i).draw(g,x,y+52-i,true);
		}
	}
	
}
