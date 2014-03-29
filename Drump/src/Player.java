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
		}
	}
	public void draw(Graphics g, int x, int y){
		for(int i = 0; i < hand.length; i++){
			if(hand[i]!=null){
				hand[i].draw(g,x,y,true);
			}
		}
	}
}
