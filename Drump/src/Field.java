import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


public class Field extends JPanel implements MouseListener{
	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	
	public static final int PLAYER_X0 = 200;
	public static final int PLAYER_Y0 = 200;
	public static final int PLAYER_X1 = 200;
	public static final int PLAYER_Y1 = 200;
	public static final int FORCE_ZONE_X0 = 100;
	public static final int FORCE_ZONE_Y0 = 150;
	public static final int FORCE_ZONE_X1 = 100;
	public static final int FORCE_ZONE_Y1 = 150;
	public static final int MONSTER_PARTS_ZONE_X0 = 10;
	public static final int MONSTER_PARTS_ZONE_Y0 = 10;
	public static final int MONSTER_PARTS_ZONE_X1 = 10;
	public static final int MONSTER_PARTS_ZONE_Y1 = 10;
	public static final int DECK_X = 300;
	public static final int DECK_Y = 20;
	public static final int GRAVE_X = 150;
	public static final int GRAVE_Y = 20;
	
	
	public enum Mode{
		ATTACK,
		BLOCK,
		SUMMON,
		RECYCLE,
	};
	
	private Mode mode;
	private Deck deck;
	private Player[] players = new Player[2];
	private ForceZone[] forceZones = new ForceZone[2];
	private MonsterPartsZone[] monsterPartsZones = new MonsterPartsZone[2];
	private Grave grave;
	
	public Field(){
		init();
	}
	public void init(){
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		grave = new Grave();
		addMouseListener(this);
		deck = new Deck();
		for(int i = 0; i < 2; i++){
			players[i] = new Player(i,this);
			forceZones[i] = new ForceZone(this);
			monsterPartsZones[i] = new MonsterPartsZone(this);
		}
	}
	public void removeCard(Card card){
		grave.addCard(card);
	}
	public Card drawCard(){
		return deck.getTopCard();
	}

	public void drump(){
		
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0,0,WIDTH,HEIGHT);
		g.setColor(Color.BLACK);
		deck.draw(g,DECK_X,DECK_Y);
		monsterPartsZones[0].draw(g,MONSTER_PARTS_ZONE_X0,MONSTER_PARTS_ZONE_Y0);
		forceZones[0].draw(g,FORCE_ZONE_X0,FORCE_ZONE_Y0);
		players[0].draw(g,PLAYER_X0,PLAYER_Y0);
		grave.draw(g,GRAVE_X,GRAVE_Y);
	}
	
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		// player0
		Card selected;
		if(y>=PLAYER_Y0 && y<PLAYER_Y0+Card.HEIGHT){
			for(int i = 0; i < 6; i++){
				if(x>=PLAYER_X0+i*Card.WIDTH && x<PLAYER_X0+(i+1)*Card.WIDTH){
					selected = players[0].getCard(i);
					if(selected == null) return;
					forceZones[0].set(0,selected);
					players[0].removeCard(i);
					players[0].drawCard(deck.getTopCard());
				}
			}
		}
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
}
