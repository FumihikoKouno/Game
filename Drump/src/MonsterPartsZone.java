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
			if(parts[i].getNumber()<0) return 0;
			count[parts[i].getNumber()-10]++;
		}
		for(int i = 0; i < count.length; i++){
			// TODO implement yaku
		}
		return ret;
	}
	
	public boolean set(Card card){
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
				parts[i*2+j].draw(g,x+j*Card.WIDTH,y+i*Card.HEIGHT,true);
			}
		}
	}
	
}
