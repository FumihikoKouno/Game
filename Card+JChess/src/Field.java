import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import Card.*;
import Common.Data;
import Common.MouseEnterExitListener;

public class Field extends JPanel implements MouseListener{
	Box[][] boxes = new Box[Data.FIELD_Y][Data.FIELD_X];
	private Point selectingPoint = new Point();
	
	private COMMAND selecting = COMMAND.NONE;
	private ArrayList<Card> enemies = new ArrayList<Card>();
	private ArrayList<Card> friends = new ArrayList<Card>();
	private ArrayList<Box> fields = new ArrayList<Box>();
	private boolean selectForASkill;
	
	private StatusPanel statusPanel;
	
	private PrivateArea[] areas;
	private static final int Y_OFFSET = 1;
	
	enum COMMAND{
		NONE,
		ATTACK,
		ACTIVE_SKILL,
		EVOLVE,
		SUMMON_SOUTH,
		SUMMON_NORTH,
		MOVE,
	}
	
	public Field(){
		setPreferredSize(new Dimension(Data.BOX_SIZE*Data.FIELD_X, Data.BOX_SIZE*Data.FIELD_Y+(Data.BOX_SIZE<<1)));
		setLayout(new GridLayout(Data.FIELD_Y,Data.FIELD_X));
		addMouseListener(this);
		for(int i = 0; i < Data.FIELD_Y; i++){
			for(int j = 0; j < Data.FIELD_X; j++){
				boxes[i][j] = new Box();
				if(i<Data.AREA_ROW) boxes[i][j].setElement(Box.ELEMENT.NORTH_FIELD);
				else if(i>=Data.FIELD_Y-Data.AREA_ROW) boxes[i][j].setElement(Box.ELEMENT.SOUTH_FIELD);
				else boxes[i][j].setElement(Box.ELEMENT.CENTER_FIELD);
				if(i==Data.FIELD_Y-1 && (j==0 || j==5)){
					boxes[i][j].setCard(new Player(Card.ELEMENT.SOUTH));
				}
			}
		}
	}
	
	public void setStatusPanel(StatusPanel status){
		statusPanel = status;
	}
	
	public void setAreas(PrivateArea[] area){
		areas = area;
	}

	public void resetSelect(){
		selecting = COMMAND.NONE;
		resetSelectableRange();		
	}
	
	public void rightClick(MouseEvent e){
		if(selecting != COMMAND.NONE){
			resetSelect();
		}else{
		}
	}
	
	public void clickSelect(MouseEvent e, int x, int y){
		Box box = boxes[y][x];
		Card card = box.getCard();
		Card selectingCard = boxes[selectingPoint.y][selectingPoint.x].getCard();
		switch(selecting){
		case ACTIVE_SKILL:
			if(box.getSelectable()){
				if(card == null){
					if(fields.contains(box))
						fields.remove(box);
					else
						fields.add(box);
				}else{
					if(card.getUser()==selectingCard.getUser()){
						if(friends.contains(card))
							friends.remove(card);
						else
							friends.add(card);
					}else{
						if(enemies.contains(card))
							enemies.remove(card);
						else
							enemies.add(card);
					}
				}
				if(fields.size()==selectingCard.getFieldsForActiveSkill() &&
				   friends.size()==selectingCard.getFriendsForActiveSkill() &&
				   enemies.size()==selectingCard.getEnemiesForActiveSkill()
				){
					selectingCard.doActiveSkill(fields,friends,enemies);
				}
			}
			if(!selectForASkill){
				for(int i = 0; i < Data.FIELD_Y; i++){
					for(int j = 0; j < Data.FIELD_X; j++){
						if(boxes[i][j].getSelectable()){
							fields.add(boxes[i][j]);
						}
					}
				}
			}
			card.doActiveSkill(fields, friends, enemies);
			break;
		case ATTACK:
			if(!box.getSelectable()) break;
			if(card == null) break;
			if(card.getUser() != selectingCard.getUser()){
				selectingCard.doAttack(card);
				if(card.getPower()<=0){
					box.setCard(null);
				}
			}
			break;
		case EVOLVE:
			break;
		case MOVE:
			if(!box.getSelectable()) break;
			if(card != null) break;
			box.setCard(selectingCard);
			boxes[selectingPoint.y][selectingPoint.x].setCard(null);
			break;
		case SUMMON_SOUTH:
			if(!box.getSelectable()) break;
			if(card != null) break;
			for(int i = 0; i < areas.length; i++){
				if(areas[i].getElement() == PrivateArea.ELEMENT.SOUTH){
					box.setCard(areas[i].getHand().summonCard());
					break;
				}
			}
			break;
		case SUMMON_NORTH:
			if(!box.getSelectable()) break;
			if(card != null);
			for(int i = 0; i < areas.length; i++){
				if(areas[i].getElement() == PrivateArea.ELEMENT.NORTH){
					box.setCard(areas[i].getHand().summonCard());
					break;
				}
			}
			break;
		default:
			break;
		}
		resetSelect();
	}
	
	public void setSelecting(int x, int y ){
		selectingPoint.x = x;
		selectingPoint.y = y;
	}
	
	public void resetSelectableRange(){
		for(int i = 0; i < Data.FIELD_Y; i++){
			for(int j = 0; j < Data.FIELD_X; j++){
				boxes[i][j].setSelectable(false);
			}
		}
	}
	
	public void setSelectableRange(boolean[][] range){
		for(int i = 0; i < Data.FIELD_Y; i++){
			for(int j = 0; j < Data.FIELD_X; j++){
				if(range[i][j]) boxes[i][j].setSelectable(true);
			}
		}
	}
	
	public void setSelectableRange(COMMAND com){
		Card card = boxes[selectingPoint.y][selectingPoint.x].getCard();
		boolean[][] range;
		switch(com){
		case ATTACK:
			range = card.getAttackableRange(selectingPoint);
			break;
		case ACTIVE_SKILL:
			range = card.getActiveSkillRange(selectingPoint);
			break;
		case MOVE:
			range = card.getMoveRange(selectingPoint);
			break;
		default:
			return;
		}
		setSelectableRange(range);
	}
	
	public void selectCommand(COMMAND com){
		selecting = com;
		Card card = boxes[selectingPoint.y][selectingPoint.x].getCard();
		switch(com){
		case ACTIVE_SKILL:
			selectForASkill = card.getSelectForActiveSkill();
			break;
		case EVOLVE:
			card.evolve();
			break;
		default:
			return;
		}
	}
	
	public void createCommandPopup(Box selectingBox, Card selectingCard, MouseEvent e){
		JPopupMenu popup = new JPopupMenu();
		int attackCost = selectingCard.getAttackCost();
		int activeSkillCost = selectingCard.getActiveSkillCost();
		int evolveCost = selectingCard.getEvolveCost();
		int moveCost = selectingCard.getMoveCost();

		if(moveCost!=0){
			JMenuItem move = new JMenuItem("Move : Cost"+moveCost);
			move.addMouseListener(new MouseEnterExitListener(){
				public void mouseEntered(MouseEvent e) {
					setSelectableRange(COMMAND.MOVE);
				}
				public void mouseExited(MouseEvent e) {
					resetSelectableRange();
				}
			});
			move.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					selectCommand(COMMAND.MOVE);
				}
			});
			popup.add(move);
		}
		if(attackCost!=0){
			JMenuItem attack = new JMenuItem("Attack : Cost"+attackCost);
			attack.addMouseListener(new MouseEnterExitListener(){
				public void mouseEntered(MouseEvent e) {
					setSelectableRange(COMMAND.ATTACK);
				}
				public void mouseExited(MouseEvent e) {
					resetSelectableRange();
				}
			});
			attack.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					selectCommand(COMMAND.ATTACK);
				}
			});
			popup.add(attack);
		}
		if(activeSkillCost!=0){
			JMenuItem activeSkill = new JMenuItem("ActiveSkill : Cost"+activeSkillCost);
			activeSkill.addMouseListener(new MouseEnterExitListener(){
				public void mouseEntered(MouseEvent e) {
					setSelectableRange(COMMAND.ACTIVE_SKILL);
				}
				public void mouseExited(MouseEvent e) {
					resetSelectableRange();
				}
			});
			activeSkill.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					selectCommand(COMMAND.ACTIVE_SKILL);
				}
			});
			popup.add(activeSkill);
		}
		if(evolveCost!=0 && 
		   !selectingCard.isEvolved() && 
		   ((selectingCard.getUser()==Card.ELEMENT.SOUTH && selectingBox.getElement() == Box.ELEMENT.NORTH_FIELD) ||
		   (selectingCard.getUser()==Card.ELEMENT.NORTH && selectingBox.getElement() == Box.ELEMENT.SOUTH_FIELD))
		){
			JMenuItem evolution = new JMenuItem("Evolution : Cost"+evolveCost);
			evolution.addMouseListener(new MouseEnterExitListener(){
				public void mouseEntered(MouseEvent e) {
					setSelectableRange(COMMAND.EVOLVE);
				}
				public void mouseExited(MouseEvent e) {
					resetSelectableRange();
				}
			});
			evolution.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					selectCommand(COMMAND.EVOLVE);
				}
			});
			popup.add(evolution);
		}
		popup.show(e.getComponent(), e.getX(),e.getY());
	}
	
	public void clickFirstCard(MouseEvent e){
		Box selectingBox = boxes[selectingPoint.y][selectingPoint.x];
		Card selectingCard = selectingBox.getCard();
		if(selectingCard==null) return;
		statusPanel.setCard(selectingCard);
		createCommandPopup(selectingBox,selectingCard,e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x=0;
		int y=0;
		for(int i = 0; i < Data.FIELD_Y; i++){
			if(e.getY()<(i+Y_OFFSET)*Data.BOX_SIZE || e.getY()>=(i+1+Y_OFFSET)*Data.BOX_SIZE) continue;
			for(int j = 0; j < Data.FIELD_X; j++){
				if(e.getX()<j*Data.BOX_SIZE || e.getX()>=(j+1)*Data.BOX_SIZE) continue;
				y = i;
				x = j;
				i = Data.FIELD_Y;
			}
		}
		Box box = boxes[selectingPoint.y][selectingPoint.x];

		switch(e.getButton()){
		// Left Click
		case MouseEvent.BUTTON1:
			switch(box.getElement()){
			case SOUTH_FIELD:
			case NORTH_FIELD:
			case CENTER_FIELD:
				if(selecting != COMMAND.NONE) clickSelect(e,x,y);
				else{
					setSelecting(x,y);
					clickFirstCard(e);
				}
				break;
			default:
				return;
			}
			break;
		// Right Click
		case MouseEvent.BUTTON3:
			rightClick(e);
			break;
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	public void paintComponent(Graphics g){
		for(int i = 0; i < Data.FIELD_Y; i++){
			for(int j = 0; j < Data.FIELD_X; j++){
				boxes[i][j].draw(g,j,i+Y_OFFSET);
			}
		}
	}
}
