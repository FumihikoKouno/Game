package game.zones;
import game.common.*;
import game.zones.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

import communication.SocketHandler;

public class Field extends JPanel implements MouseListener{
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	
	public static final int PLAYER_X0 = 50;
	public static final int PLAYER_Y0 = 350;
	public static final int PLAYER_X1 = 50;
	public static final int PLAYER_Y1 = 10;
	public static final int FORCE_ZONE_X0 = 40;
	public static final int FORCE_ZONE_Y0 = 250;
	public static final int FORCE_ZONE_X1 = 80;
	public static final int FORCE_ZONE_Y1 = 60;
	public static final int MONSTER_PARTS_ZONE_X0 = 200;
	public static final int MONSTER_PARTS_ZONE_Y0 = 200;
	public static final int MONSTER_PARTS_ZONE_X1 = 10;
	public static final int MONSTER_PARTS_ZONE_Y1 = 60;
	public static final int DECK_X = 150;
	public static final int DECK_Y = 110;
	public static final int GRAVE_X = 90;
	public static final int GRAVE_Y = 110;
	public static final int GRAVE_PARTS_X = WIDTH-(Card.WIDTH<<2);
	public static final int GRAVE_PARTS_Y = 0;
	
	public int selected;
	
	public boolean attacked;
	public boolean summoned;
	public boolean extraJacked;
	public int recycleCount;
	public int attackNumber;
	
	public enum Mode{
		DEFAULT,
		ATTACK,
		FORCE_CHARGE,
		BLOCK,
		SUMMON,
		RECYCLE,
		EXTRA_JACK,
		DROP_BY_TURN_END,
	};
	
	private JTextArea textArea;
	private SocketHandler sh;
	private Mode mode = Mode.DEFAULT;
	private Deck deck;
	private Player[] players = new Player[2];
	private ForceZone[] forceZones = new ForceZone[2];
	private MonsterPartsZone[] monsterPartsZones = new MonsterPartsZone[2];
	private Grave grave;
	
	private boolean myTurn;
	
	public Field(SocketHandler sh){
		init(sh);
	}
	public void init(SocketHandler sh){
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.sh = sh;
		grave = new Grave();
		addMouseListener(this);
		deck = new Deck();
		for(int i = 0; i < 2; i++){
			players[i] = new Player(this);
			forceZones[i] = new ForceZone(this);
			monsterPartsZones[i] = new MonsterPartsZone(this);
		}
		if(sh.getMode() == SocketHandler.Mode.SERVER){
			serverInit();
		}else{
			cliantInit();
		}
	}
	
	public void setTextArea(JTextArea t){
		textArea = t;
	}
	
	public void serverInit(){
		myTurn = true;
		players[0].init();
		sh.send(deck.toString());
		sh.send(players[0].toString());
		deck.setByString(sh.receive());
		players[1].setByString(sh.receive());
		players[0].setOpen(true);
	}
	
	public void cliantInit(){
		myTurn = false;
		deck.setByString(sh.receive());
		players[1].setByString(sh.receive());
		players[0].init();
		sh.send(deck.toString());
		sh.send(players[0].toString());
		players[0].setOpen(true);
	}
	
	public void update(){
		if(myTurn){
		}else{
			waiting();
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
		forceZones[0].drawSum(g,FORCE_ZONE_X0,FORCE_ZONE_Y0);
		players[0].draw(g,PLAYER_X0,PLAYER_Y0);
		grave.draw(g,GRAVE_X,GRAVE_Y);
		monsterPartsZones[1].draw(g,MONSTER_PARTS_ZONE_X1,MONSTER_PARTS_ZONE_Y1);
		forceZones[1].draw(g,FORCE_ZONE_X1,FORCE_ZONE_Y1);
		players[1].draw(g,PLAYER_X1,PLAYER_Y1);	
		grave.drawRecycleCards(g,GRAVE_PARTS_X, GRAVE_PARTS_Y);
		switch(mode){
		case DEFAULT:
			if(myTurn) textArea.setText("Your Turn");
			else textArea.setText("Enemy Turn");
			break;
		case ATTACK:
			textArea.setText("Select attack target");
			forceZones[1].lightUpTarget(g,FORCE_ZONE_X1,FORCE_ZONE_Y1);
			break;
		case BLOCK:
			textArea.setText("Select card using for block");
			players[0].lightUpBlockableCards(g,PLAYER_X0,PLAYER_Y0,attackNumber);
			break;
		case EXTRA_JACK:
			textArea.setText("Select Jack");
			players[0].lightUpJack(g,PLAYER_X0,PLAYER_Y0);
			break;
		case FORCE_CHARGE:
			textArea.setText("Select force zone");
			forceZones[0].lightUp(g,FORCE_ZONE_X0,FORCE_ZONE_Y0,players[0].getCard(selected));
			break;
		case RECYCLE:
			break;
		case SUMMON:
			textArea.setText("Select force");
			forceZones[0].lightUpForce(g,FORCE_ZONE_X0,FORCE_ZONE_Y0,players[0].getCard(selected).getNumber());
			break;
		default:
			break;
		}
	}
		
	public void clickPlayerCard(MouseEvent e, int num){
		if(players[0].getCard(num) == null) return;
		selected = num;
		JPopupMenu popup = new JPopupMenu();
		if(players[0].getCard(selected).getNumber() < 10){
			if(!attacked){
				JMenuItem attack = new JMenuItem("Attack");
				attack.addActionListener(new ActionListener() {			
					public void actionPerformed(ActionEvent arg0) {
						mode = Mode.ATTACK;
						stateChange();
					}
				});		
				popup.add(attack);
			}
			if(forceZones[0].canCharge()){
				JMenuItem charge = new JMenuItem("Force Charge");
				charge.addActionListener(new ActionListener() {			
					public void actionPerformed(ActionEvent arg0) {
						mode = Mode.FORCE_CHARGE;
						stateChange();
					}
				});
				popup.add(charge);
			}
		}
		
		if(players[0].getCard(num).getNumber() == 1){
			if(!extraJacked){
				JMenuItem extraJack = new JMenuItem("Extra Jack");
				extraJack.addActionListener(new ActionListener() {			
					public void actionPerformed(ActionEvent arg0) {
						mode = Mode.EXTRA_JACK;
						stateChange();
					}
				});
				popup.add(extraJack);
			}
		}
		
		if(players[0].getCard(num).getNumber() >= 10){
			if(recycleCount < 2){
				JMenu recycle = new JMenu("Recycle rest:" + (2-recycleCount));
				JMenuItem getPartsByRecycle = new JMenuItem("Get Parts from Grave");
				JMenuItem drawCardByRecycle = new JMenuItem("Draw Card");
				recycle.add(getPartsByRecycle);
				recycle.add(drawCardByRecycle);
				getPartsByRecycle.addActionListener(new ActionListener() {			
					public void actionPerformed(ActionEvent arg0) {
						mode = Mode.RECYCLE;
						stateChange();
					}
				});
				drawCardByRecycle.addActionListener(new ActionListener() {			
					public void actionPerformed(ActionEvent arg0) {
						grave.addCard(players[0].getCard(selected));
						players[0].removeCard(selected);
						players[0].pack();
						players[0].drawCard(drawCard());
						players[0].pack();
						recycleCount++;
						sh.send("Grave");
						sh.send(grave.toString());
						sh.send("Deck");
						sh.send(deck.toString());
						stateChange();
					}
				});
				popup.add(recycle);
			}
			if(!summoned){
				JMenuItem summon = new JMenuItem("Summon");
				summon.addActionListener(new ActionListener() {			
					public void actionPerformed(ActionEvent arg0) {
						mode = Mode.SUMMON;
						stateChange();
					}
				});
				popup.add(summon);
			}
		}
		

		JMenuItem goGrave = new JMenuItem("Go Grave(Debug)");
		JMenuItem drawCard = new JMenuItem("Draw Card(Debug)");

		goGrave.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				grave.addCard(players[0].getCard(selected));
				players[0].removeCard(selected);
				players[0].pack();
			}
		});
		drawCard.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				players[0].drawCard(deck.getTopCard());
			}
		});
		popup.add(goGrave);
		popup.add(drawCard);
		popup.show(e.getComponent(),e.getX(),e.getY());
		/*
		forceZones[id].set(0,selected);
		players[id].removeCard(num);
		players[id].drawCard(deck.getTopCard());		
		*/
	}
	
	public void waiting(){
		while(true){
			String receive = sh.receive();
			if(receive.equals("Player")){
				players[1].setByString(sh.receive());
			}
			if(receive.equals("Deck")){
				deck.setByString(sh.receive());
			}
			if(receive.equals("Grave")){
				grave.setByString(sh.receive());
			}
			if(receive.equals("ForceZone")){
				forceZones[1].setByString(sh.receive());
			}
			if(receive.equals("MonsterPartsZone")){
				monsterPartsZones[1].setByString(sh.receive());
			}
			if(receive.equals("Attacked")){
				String[] str = sh.receive().split(",");
				Card attackCard = new Card(str[1]);
				attackNumber = attackCard.getNumber();
				forceZones[0].attacked(Integer.parseInt(str[0]),attackCard);
				sh.send("ForceZone");
				sh.send(forceZones[0].toString());
				mode = Mode.BLOCK;
			}
			if(receive.equals("Blocked")){
			}
			if(receive.equals("TurnEnd")){
				myTurn = true;
				mode = Mode.DEFAULT;
				players[0].drawCard(drawCard());
				sh.send("Player");
				sh.send(players[0].toString());
				sh.send("Deck");
				sh.send(deck.toString());
				stateChange();
				return;
			}
			stateChange();
		}
	}
	
	public void turnEnd(){
		if(players[0].getHandNumber()>5){
			mode = Mode.DROP_BY_TURN_END;
		}else{
			mode = Mode.DEFAULT;
			attacked = false;
			summoned = false;
			extraJacked = false;
			recycleCount = 0;
			myTurn = false;
			forceZones[0].turnEnd();
			sh.send("TurnEnd");
		}
	}
	
	public void stateChange(){
		repaint();
	}
	
	public void mouseClicked(MouseEvent e) {
		if(!myTurn) return;
		if(e.getButton() == MouseEvent.BUTTON3) turnEnd();
		int x = e.getX();
		int y = e.getY();
		switch(mode){
		case DEFAULT:
			if(y>=PLAYER_Y0 && y<PLAYER_Y0+Card.HEIGHT && x>=PLAYER_X0 && x<PLAYER_X0+6*Card.WIDTH){
				for(int i = 0; i < 6; i++){
					if(x>=PLAYER_X0+i*Card.WIDTH && x<PLAYER_X0+(i+1)*Card.WIDTH){
						clickPlayerCard(e,i);
						stateChange();
						return;
					}
				}
			}
			break;
		case BLOCK:
			if(y>=PLAYER_Y0 && y<PLAYER_Y0+Card.HEIGHT && x>=PLAYER_X0 && x<PLAYER_X0+6*Card.WIDTH){
				for(int i = 0; i < 6; i++){
					if(x>=PLAYER_X0+i*Card.WIDTH && x<PLAYER_X0+(i+1)*Card.WIDTH){
						clickPlayerCard(e,i);
						stateChange();
						return;
					}
				}
			}
			break;
		case ATTACK:
			if(y>FORCE_ZONE_Y1 && y<FORCE_ZONE_Y1+Card.HEIGHT && x>=FORCE_ZONE_X1 && x<FORCE_ZONE_X1+4*Card.WIDTH){
				for(int i = 0; i < 6; i++){
					if(x>=FORCE_ZONE_X1+i*Card.WIDTH && x<FORCE_ZONE_X1+(i+1)*Card.WIDTH){
						sh.send("Attacked");
						sh.send(i+","+players[0].getCard(selected));
						players[0].removeCard(selected);
						players[0].pack();
						myTurn = false;
						mode = Mode.DEFAULT;
						stateChange();
						return;
					}
				}
			}
			break;
		case EXTRA_JACK:
			if(y>=PLAYER_Y0 && y<PLAYER_Y0+Card.HEIGHT && x>=PLAYER_X0 && x<PLAYER_X0+6*Card.WIDTH){
				for(int i = 0; i < 6; i++){
					if(x>=PLAYER_X0+i*Card.WIDTH && x<PLAYER_X0+(i+1)*Card.WIDTH){
						if(players[0].getCard(i) != null && players[0].getCard(i).getNumber()==11){
							grave.addCard(players[0].getCard(selected));
							monsterPartsZones[0].set(players[0].getCard(i));
							players[0].removeCard(selected);
							players[0].removeCard(i);
							players[0].pack();
							sh.send("Player");
							sh.send(players[0].toString());
							sh.send("Grave");
							sh.send(grave.toString());
							sh.send("MonsterPartsZone");
							sh.send(monsterPartsZones[0].toString());
							extraJacked = true;
						}
						stateChange();
						mode = Mode.DEFAULT;
						return;
					}
				}
			}
			break;
		case FORCE_CHARGE:
			if(y>FORCE_ZONE_Y0 && x>=FORCE_ZONE_X0 && x<FORCE_ZONE_X0+4*Card.WIDTH){
				for(int i = 0; i < 6; i++){
					if(x>=FORCE_ZONE_X0+i*Card.WIDTH && x<FORCE_ZONE_X0+(i+1)*Card.WIDTH && y<(int)(FORCE_ZONE_Y0+Card.HEIGHT*(1+0.5*forceZones[0].getCardNumber(i)))){
						if(forceZones[0].set(i,players[0].getCard(selected))){
							players[0].removeCard(selected);
							players[0].pack();
							sh.send("Player");
							sh.send(players[0].toString());
							sh.send("ForceZone");
							sh.send(forceZones[0].toString());
						}
						mode = Mode.DEFAULT;
						stateChange();
						mode = Mode.DEFAULT;
						return;
					}
				}
			}
			break;
		case RECYCLE:
			break;
		case SUMMON:
			if(y>FORCE_ZONE_Y0 && x>=FORCE_ZONE_X0 && x<FORCE_ZONE_X0+4*Card.WIDTH){
				for(int i = 0; i < 6; i++){
					if(x>=FORCE_ZONE_X0+i*Card.WIDTH && x<FORCE_ZONE_X0+(i+1)*Card.WIDTH && y<(int)(FORCE_ZONE_Y0+Card.HEIGHT*(1+0.5*forceZones[0].getCardNumber(i)))){
						if(forceZones[0].canSummon(i,players[0].getCard(selected).getNumber())){
							monsterPartsZones[0].set(players[0].getCard(selected));
							players[0].removeCard(selected);
							players[0].pack();
							ArrayList<Card> forces = forceZones[0].getCards(i);
							for(int j = 0; j < forces.size(); j++){
								grave.addCard(forces.get(j));
							}
							forceZones[0].clear(i);
							summoned = true;
							sh.send("Player");
							sh.send(players[0].toString());
							sh.send("ForceZone");
							sh.send(forceZones[0].toString());
							sh.send("MonsterPartsZone");
							sh.send(monsterPartsZones[0].toString());
							sh.send("Grave");
							sh.send(grave.toString());
							stateChange();
							mode = Mode.DEFAULT;
						}
					}
				}
			}
			break;
		case DROP_BY_TURN_END:
			if(y>=PLAYER_Y0 && y<PLAYER_Y0+Card.HEIGHT && x>=PLAYER_X0 && x<PLAYER_X0+6*Card.WIDTH){
				for(int i = 0; i < 6; i++){
					if(x>=PLAYER_X0+i*Card.WIDTH && x<PLAYER_X0+(i+1)*Card.WIDTH){
						if(players[0].getCard(i) != null){
							grave.addCard(players[0].getCard(i));
							players[0].removeCard(i);
							players[0].pack();
							sh.send("Player");
							sh.send(players[0].toString());
							sh.send("Grave");
							sh.send(grave.toString());
							turnEnd();
							return;
						}
						stateChange();
						mode = Mode.DEFAULT;
						return;
					}
				}
			}
			return;
		}
		mode = Mode.DEFAULT;
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
