import java.awt.Graphics;


public class MonsterPartsZone {
	public enum Parts{
		HEAD,
		WING,
		BODY,
		TAIL,
	};
	public int[] parts = new int[4];
	
	public MonsterPartsZone(){
		init();
	}
	public void init(){
		for(int i = 0; i < parts.length; i++){
			parts[i] = -1;
		}
	}
	
	public int drump(){
		int ret = 0;
		int[] count = new int[4];
		for(int i = 0; i < parts.length; i++){
			if(parts[i]<0) return 0;
			count[parts[i]-10]++;
		}
		for(int i = 0; i < count.length; i++){
			// TODO implement yaku
		}
		return ret;
	}
	
	public boolean set(Card card){
		switch(card.getMark()){
		case SPADES:
			parts[0] = card.getNumber();
			break;
		case CLUBS:
			parts[1] = card.getNumber();
			break;
		case HEARTS:
			parts[2] = card.getNumber();
			break;
		case DIAMONDS:
			parts[3] = card.getNumber();
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
				g.drawString(""+parts[i*2+j],x+j*Card.WIDTH,y+i*Card.HEIGHT+Card.HEIGHT);
			}
		}
	}
	
}
