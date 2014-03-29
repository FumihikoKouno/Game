import java.awt.Graphics;
import java.util.ArrayList;


public class ForceZone {
	private class CardArray extends ArrayList<Card>{}
	private CardArray[] forces = new CardArray[4];
	public ForceZone(){
		init();
	}
	public void init(){
		for(int i = 0; i < 4; i++){
			forces[i] = new CardArray();
		}
	}
	public boolean set(int position, Card card){
		if(position < 0 || position >= forces.length) return false;
		forces[position].add(card);
		int sum = 0;
		for(int i = 0; i < forces[position].size(); i++){
			sum += forces[position].get(i).getNumber();
		}
		if(sum > 13) forces[position].clear();
		return true;
	}
	public void draw(Graphics g, int x, int y){
		for(int i = 0; i < forces.length; i++){
			g.drawRect(x+i*Card.WIDTH,y,Card.WIDTH,Card.HEIGHT);
			for(int j = 0; j < forces[i].size(); j++){
				forces[i].get(j).draw(g,x+i*Card.WIDTH,y+j*Card.HEIGHT/2,true);
			}
		}
	}
}
