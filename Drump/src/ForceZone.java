import java.awt.Graphics;


public class ForceZone {
	private int[] forces = new int[4];
	public ForceZone(){
		init();
	}
	public void init(){}
	public boolean set(int position, int power){
		if(position < 0 || position >= forces.length) return false;
		forces[position] += power;
		if(forces[position]>13){
			forces[position] = 0;
		}
		return true;
	}
	public void draw(Graphics g, int x, int y){
		for(int i = 0; i < forces.length; i++){
			g.drawRect(x+i*Card.WIDTH,y,Card.WIDTH,Card.HEIGHT);
			g.drawString(""+forces[i],x,y+Card.HEIGHT);
		}
	}
}
